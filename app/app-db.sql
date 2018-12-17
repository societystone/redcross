

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
	id            bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id'  ,
username        varchar(30) NOT NULL COMMENT '用户名'  ,
password        varchar(64) NOT NULL COMMENT '密码'    ,
real_name       varchar(50) COMMENT '姓名'    ,
phone           varchar(30) COMMENT '手机号码',
create_date     datetime  NOT NULL  COMMENT '创建时间',
status          varchar(1) NOT NULL DEFAULT '1' COMMENT '状态'    ,
	PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
id           bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '菜单id'  ,
menu_name    varchar(30)  NOT NULL                COMMENT '菜单名'  ,
parent_id    bigint(20) NOT NULL                  COMMENT '父菜单id',
url          varchar(50)                          COMMENT '链接地址',
icon         varchar(50)                          COMMENT '图标'    ,
rank         int(11)    NOT NULL                  COMMENT '排序'    ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `sys_relation`;

CREATE TABLE `sys_relation` (
  id            bigint(20)   NOT NULL AUTO_INCREMENT COMMENT'主键id'      ,
relation_type   varchar(10)  NOT NULL                COMMENT'关联类型'    ,
main_primary_id bigint(20) NOT NULL                  COMMENT'主表主键id'  ,
rel_primary_id  bigint(20) NOT NULL                  COMMENT'关联表主键id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `sys_ref_def`;

CREATE TABLE `sys_ref_def` (
  id          bigint(20)    NOT NULL AUTO_INCREMENT            COMMENT '主键id'  , 
ref_type      varchar(10)   NOT NULL            COMMENT '码值类型',
ref_code      varchar(10)   NOT NULL            COMMENT '码值'    ,
ref_desc      varchar(50)   NOT NULL            COMMENT '码值描述',
status        varchar(1)    NOT NULL  DEFAULT   '1'       COMMENT '状态'    ,
remark        varchar(200)              COMMENT '备注'    ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

