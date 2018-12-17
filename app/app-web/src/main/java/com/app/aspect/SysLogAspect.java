package com.app.aspect;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.app.aspect.annotation.Log;
import com.app.entity.SysLog;
import com.app.entity.SysUser;
import com.app.service.SysLogService;
import com.app.util.Emptys;

/**
 * 系统日志：切面处理类
 */
@Aspect
@Component
public class SysLogAspect {

	@Autowired
	private SysLogService sysLogService;

	// 定义切点 @Pointcut
	// 在注解的位置切入代码
	@Pointcut("@annotation( com.app.aspect.annotation.Log)")
	public void logPoinCut() {
	}

	// 切面 配置通知
	@AfterReturning("logPoinCut()")
	public void saveSysLog(JoinPoint joinPoint) {
		System.out.println("切面。。。。。");
		// 保存日志
		SysLog sysLog = new SysLog();

		// 从切面织入点处通过反射机制获取织入点处的方法
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		// 获取切入点所在的方法
		Method method = signature.getMethod();

		// 获取操作
		Log log = method.getAnnotation(Log.class);
		if (Emptys.isNotEmpty(log)) {
			String value = log.value();
			sysLog.setOperation(value);// 保存获取的操作
		}

		// 获取请求的类名
		String className = joinPoint.getTarget().getClass().getName();
		// 获取请求的方法名
		String methodName = method.getName();
		sysLog.setMethod(className + "." + methodName);

		if (!"login".equals(methodName)) {
			// 请求的参数
			Object[] args = joinPoint.getArgs();
			// 将参数所在的数组转换成json
			String params = JSON.toJSONString(args);
			sysLog.setParams(params);
		}
		// 获取用户名
		SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
		sysLog.setUsername(sysUser.getUsername());
		// 获取用户ip地址
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes sra = (ServletRequestAttributes) ra;
		HttpServletRequest request = sra.getRequest();
		sysLog.setIp(request.getRemoteHost());

		// 调用service保存SysLog实体类到数据库
		sysLogService.insertSysLog(sysLog);
	}

}
