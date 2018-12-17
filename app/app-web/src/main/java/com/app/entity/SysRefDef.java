package com.app.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 系统码值Entity . <br>
 * 
 * @author wangtw
 *
 */
@Setter
@Getter
public class SysRefDef extends BaseEntity implements Serializable {

    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = -20719711873344161L;
	private String refType;//码值类型
    private String refCode;//码值
    private String refDesc;//码值描述
    private Integer rank;//排序
    private String status;//码值状态，0-禁用 1-启用
    private String remark;//备注

}
