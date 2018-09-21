/**
 * Created on 2018年3月23日 下午5:16:54
 */
package com.app.aspect;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.app.aspect.annotation.SysLog;
import com.app.entity.SysLogEntity;
import com.app.service.SysLogService;
import com.app.util.IpUtils;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 系统日志切面 . <br>
 * 使用@Aspect注解将一个java类定义为切面类 <br>
 * 使用@Pointcut定义一个切入点，可以是一个规则表达式，比如下例中某个package下的所有函数，也可以是一个注解等。 <br>
 * 根据需要在切入点不同位置的切入内容 <br>
 * 使用@Before在切入点开始处切入内容 <br>
 * 使用@After在切入点结尾处切入内容 <br>
 * 使用@AfterReturning在切入点return内容之后切入内容（可以用来对处理返回值做一些加工处理） <br>
 * 使用@Around在切入点前后切入内容，并自己控制何时执行切入点自身的内容 <br>
 * 使用@AfterThrowing用来处理当切入内容部分抛出异常之后的处理逻辑 <br>
 * 
 * @author wangtw <br>
 */
@Aspect
@Component
public class SysLogAspect {

    /**
     * 注入系统日志接口
     */
    @Autowired
    private SysLogService sysLogService;

    /**
     * 开始时间
     */
    private static final ThreadLocal<Long> START_TIME = new ThreadLocal<Long>();

    /**
     * 设置开始时间
     * 
     * @param start
     */
    public static void set(Long start) {
        START_TIME.set(start);
    }

    /**
     * 获取开始时间
     * 
     * @return
     */
    public static Long get() {
        return START_TIME.get();
    }

    /**
     * 删除开始时间
     */
    public static void remove() {
        START_TIME.remove();
    }

    /**
     * 切入点
     */
    @Pointcut("@annotation(com.app.aspect.annotation.SysLog)")
    public void logPointCut() {

    }

    /**
     * 日志处理
     * 
     * @param point
     * @return Object
     * @throws Throwable
     */
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        START_TIME.set(System.currentTimeMillis());
        // 执行方法
        Object result = point.proceed();
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - START_TIME.get();

        saveSysLog(point, time);
        return result;
    }

    /**
     * 保存日志
     * 
     * @param point
     * @param time
     * @throws JsonProcessingException
     */
    private void saveSysLog(ProceedingJoinPoint point, long time) throws JsonProcessingException {
        // 获取request
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        SysLogEntity sysLog = new SysLogEntity();
        SysLog syslog = method.getAnnotation(SysLog.class);
        if (syslog != null) {
            // 注解上的描述
            sysLog.setOperation(syslog.value());
        }

        // 请求的方法名
        String className = point.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");

        // 请求的参数
        String params = JSON.toJSONString(point.getArgs(), SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty);
        // String params = JSON.toJSONString(request.getParameterMap(),
        // SerializerFeature.DisableCircularReferenceDetect,
        // SerializerFeature.WriteMapNullValue);
        sysLog.setParams(params);
        // 设置IP地址
        sysLog.setIp(IpUtils.getCliectIp(request));

        // 用户名
        // String username = ((SysUserEntity)
        // SecurityUtils.getSubject().getPrincipal()).getUsername();
        // TODO 暂未实现shiro这里先写死
        String username = "admin";
        sysLog.setUsername(username);

        sysLog.setTime(time);
        // 保存系统日志
        sysLogService.insertSysLog(sysLog);
    }

}
