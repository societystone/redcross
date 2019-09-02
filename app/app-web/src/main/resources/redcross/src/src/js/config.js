/**
 * 全局配置
 * @param exports
 * @returns
 */
layui.define(function(exports){
  exports('config', {
	  tableName: 'layuiTable', //本地存储表名
	  appBase : '/app-web',//应用上下文
	  tableLimits : [30,50,100], //表格分页方式，一页显示数量
	  tableDefaultLimit : 30, //表格默认分页一页显示数量
	  heightVal : 50
  });
});
