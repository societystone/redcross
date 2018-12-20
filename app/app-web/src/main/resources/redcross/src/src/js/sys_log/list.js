/**
 * wangtw
 * @param exports
 * @returns
 */
layui.define(['common'], function(exports){
  var $ = layui.jquery;
  var config = layui.config;
  var table = layui.table;
  var form = layui.form;
  var common = layui.common;
  var laydate = layui.laydate;
  
  ////////////start
  //计算表格高度
  var bodyHeight = $(document).height();
  var cardHeaderHeight = $(".layui-card-header").height();
  if(!cardHeaderHeight){
	  cardHeaderHeight=0;
  }
  var cardBodyHeight = parseInt(bodyHeight)-parseInt(cardHeaderHeight)-config.heightVal;
  ////////////end

  var searchFormObj = $(".layui-form");
  //执行一个laydate实例
  laydate.render({
	  elem: '#beginDate', //指定元素
	  format: 'yyyy-MM-dd'
  });
  laydate.render({
	  elem: '#endDate', //指定元素
	  format: 'yyyy-MM-dd'
  });
//  common.initSelect(searchFormObj.find("select[name='departmentId']"),common.getSysRefDef(common.Constants.Department,'1'));
//  common.initSelect(searchFormObj.find("select[name='status']"),common.getSysRefDef(common.Constants.Status,'1'));
//  form.render("select"); //更新
//  form.val("layui-form-search",{status:"1"});

  var searchData = {
		  "username":searchFormObj.find("input[name='username']").val(),
		  "beginDate":searchFormObj.find("select[name='beginDate']").val(),
		  "endDate":searchFormObj.find("select[name='endDate']").val()
  };
  //监听搜索
  form.on('submit(LAY-btn-search)', function(data){
    var field = data.field;
    //执行重载
    table.reload('table-data', {
      where: field
    });
  });
  
  //日志管理
  table.render({
    elem: '#table-data',
    title: '日志数据表',
    height:cardBodyHeight,
    method: "POST",
    contentType: 'application/json',
    url: config.appBase+'/sys/log/list',
    where: searchData,
    cols: [[
    	{type:'id', title: '日志id', hide:true},
    	{field: 'username', title: '用户名', width: 100},
    	{field: 'operation', title: '操作', width: 250},
    	{field: 'ip', title: '客户端IP', width: 140},
    	{field: 'createDate', title: '操作时间', width: 200},
    	{title: '操作', width: 100, align: 'center', fixed: 'right', toolbar: '#table-operate'}
    ]],
    parseData: function(res){ //res 即为原始返回的数据
    	var dataList = res.data;
    	var total = 0;
    	var rows = null;
    	if(dataList!=null){
        	total = dataList.total;
        	rows = dataList.rows;
    	}
        return {
          "code": res.code, //解析接口状态
          "msg": res.msg, //解析提示文本
          "count": total, //解析数据长度
          "data": rows //解析数据列表
        };
    },
    page: true, //是否显示分页
    limits:config.tableLimits,
    limit: config.tableDefaultLimit,
    defaultToolbar: ['filter']
  });

  //事件
  var active = {
		  detail:function(id){
		      layer.open({
		          type: 2,
		          title: '日志详情',
		          content: 'detail.html',
		          maxmin: true,
		          area: ['500px', '450px'],
		          success: function(layero, index){
		        	  var iframeContent = layero.find('iframe').contents();
		        	  iframeContent.find("input[name='id']").val(id);
		          }
		       }); 
		  }
    };
  
  //监听行工具事件
  table.on('tool(table-data)', function(obj){
	  var type = obj.event;
	  active[type] ? active[type].call(this, obj.data.id) : '';
  });

  exports('sys_log/list', {});
});