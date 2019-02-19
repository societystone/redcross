package com.app.common;

public class Constants {
	public static final String ENABLE_VALUE = "1";// 启用
	public static final String DISABLE_VALUE = "0";// 禁用
	
	public static final int RESULT_NUM = 0 ;// 用于数据库插入修改是否有返回值作对比用
	public static final int RESULT_NUM1 = 1 ;// 用于数据库插入修改是否有返回值作对比用

	/**
	 * 系统状态
	 * 
	 * @author wangtw START_USE-启用 STOP_USE-禁用 REMOVE-删除
	 */
	public static enum SYS_STATUS {
		START_USE("1"), STOP_USE("2"), REMOVE("3");

		private String value;

		private SYS_STATUS(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	/**
	 * 是否标识
	 * 
	 * @author wangtw YES-是 NO-否
	 */
	public static enum SYS_YES_NO {
		YES("Y"), NO("N");

		private String value;

		private SYS_YES_NO(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	/**
	 * 角色关联类型
	 * 
	 * @author wangtw ROLE_MENU-角色菜单关联 ROLE_PERMISSION-角色权限关联
	 */
	public static enum ROLE_RELATION_TYPE {
		ROLE_MENU("RM"), ROLE_PERMISSION("RP");

		private String value;

		private ROLE_RELATION_TYPE(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
}
