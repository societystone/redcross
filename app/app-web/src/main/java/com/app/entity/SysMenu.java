package com.app.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 系统菜单Entity . <br>
 * 提供类所有属性的getting和 setting方法 <br>
 * 此外还提供了equals、canEqual、hashCode、toString方法-@Data <br>
 * 
 * 自动生成无参数构造函数-@NoArgsConstructor <br>
 * 自动生成全参数构造函数-@AllArgsConstructor
 * 
 * @author wangtw
 *
 */
@Setter
@Getter
public class SysMenu extends BaseEntity implements Serializable {

    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = 1468053204928788623L;
	private String name;//菜单名
	private Long parentId;//父菜单id
	private String url;//链接地址
	private String icon;//图标
	private Integer rank;//排序

}
