package com.app.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.util.StringUtils;

/**
 * 首页controller . <br>
 * 
 * @author wangtw <br>
 */
@RestController
public class IndexController {

    @GetMapping("/test")
    public Integer getTest() {
        String string = "12a";
        return StringUtils.stringToInteger(string);
    }

}
