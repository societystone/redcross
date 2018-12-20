package com.app.common;

public class Constants {
	public static final String ENABLE_VALUE = "1";//启用
	public static final String DISABLE_VALUE = "0";//禁用
	
	/**
	 * 码值类型
	 * @author wangtw
	 * A-系统角色
	 * Y-是否标识
	 * Z-启用标识
	 */
	public static enum SYS_REF_TYPE{
		A("系统角色"), Y("是否标识"), Z("启用标识");

		private String desc;
		private SYS_REF_TYPE(String desc) {
			this.desc=desc;
		}
		public String getDesc() {
			return desc;
		}
	}
	
	/**
	 * 关联类型
	 * @author wangtw
	 * RM-角色菜单关联
	 * RP-角色权限关联
	 */
	public static enum SYS_RELATION_TYPE{
		RM("角色菜单关联"), RP("角色权限关联");

		private String desc;
		private SYS_RELATION_TYPE(String desc) {
			this.desc=desc;
		}
		public String getDesc() {
			return desc;
		}
	}
}
