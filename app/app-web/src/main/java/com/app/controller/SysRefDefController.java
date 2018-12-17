package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.bean.ResultBean;
import com.app.entity.SysRefDef;
import com.app.service.SysRefDefService;

/**
 * 系统码值controller . <br>
 * 
 * @author wangtw <br>
 */
@RestController
public class SysRefDefController {

    /**
     * 注入用户接口
     */
    @Autowired
    private SysRefDefService sysRefDefService;

    /**
     * 查询码值
     * @param sysRefDef
     * @return
     */
    @PostMapping("/sys/refDef/list")
    public ResultBean<List<SysRefDef>> selectList(@RequestBody SysRefDef sysRefDef) {
        return new ResultBean<List<SysRefDef>>(sysRefDefService.selectList(sysRefDef));
    }

}
