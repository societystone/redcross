package com.app.service;

import java.util.Date;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.app.dao.SysUserDAO;
import com.app.entity.SysUserEntity;

/**
 * 系统用户接口测试 . <br>
 * 
 * @author wangtw <br>
 */
public class SysUserServiceTest extends BaseTest {

    /**
     * 系统用户接口-被测类
     */
    @Autowired
    private SysUserService sysUserService;

    /**
     * 系统用户dao-模拟对象
     */
    @MockBean
    private SysUserDAO sysUserDAO;

    /**
     * 系统用户实体
     */
    private SysUserEntity sysUserEntity;

    /**
     * id
     */
    private Long id = -99L;

    /**
     * 用户名
     */
    private String username = "admin";

    /**
     * 所有测试方法执行之前执行该方法
     */
    @Before
    public void before() {
        sysUserEntity = new SysUserEntity(id, -1L, username, "123456", "admin", "123456", "admin@163.com", 0,
                new Date());
        // 设置模拟对象的返回预期值
        Mockito.when(sysUserDAO.insertSysUser(sysUserEntity)).thenReturn(id);
    }

    /**
     * 添加用户测试
     */
    @Test
    public void insertSysUser() {
        // 执行测试
        long userId = sysUserService.insertSysUser(sysUserEntity);
        // 验证
        Assert.assertThat(userId, Matchers.is(id));
        // 得到一个抓取器
        ArgumentCaptor<SysUserEntity> personCaptor = ArgumentCaptor.forClass(SysUserEntity.class);
        // 验证模拟对象的save()是否被调用一次,并抓取调用时传入的参数值
        Mockito.verify(sysUserDAO).insertSysUser(personCaptor.capture());
        // 获取抓取到的参数值
        SysUserEntity addSysUser = personCaptor.getValue();
        // 验证调用时的参数值
        Assert.assertThat(username, Matchers.is(addSysUser.getUsername()));
    }

}
