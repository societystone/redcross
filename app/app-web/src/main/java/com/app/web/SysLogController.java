package com.app.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.bean.PageResultBean;
import com.app.bean.ResultBean;
import com.app.dto.SysLogDTO;
import com.app.entity.SysLogEntity;
import com.app.service.SysLogService;

/**
 * 系统日志controller . <br>
 * 
 * @author wangtw <br>
 */
@RestController
public class SysLogController {

    /**
     * 注入系统日志接口
     */
    @Autowired
    private SysLogService sysLogService;

    @GetMapping("/sys/log")
    public ResultBean<PageResultBean<SysLogEntity>> selectSysLogByPage(SysLogDTO sysLogDTO) {
        return new ResultBean<PageResultBean<SysLogEntity>>(sysLogService.selectSysLogByPage(sysLogDTO));
    }

}
