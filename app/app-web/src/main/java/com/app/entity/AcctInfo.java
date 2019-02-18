package com.app.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 账户信息 . <br>
 * 
 * @author wangtw <br>
 */
@Setter
@Getter
public class AcctInfo extends BaseEntity implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -1705182797058431420L;
	private String acctNo;// 账号
	private String acctName;// 户名
	private String status;// 状态1-正常2-停用9-删除
	private String userCode;// 创建人账号
	private String userName;// 创建人姓名
	private String userCodeLastModify;// 最后修改人账号
	private String userNameLastModify;// 最后修改人姓名

}
