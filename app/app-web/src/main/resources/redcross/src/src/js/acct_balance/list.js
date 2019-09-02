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
  ////////////start
  //计算表格高度
  var bodyHeight = $(document).height();
  var cardHeaderHeight = $(".layui-card-header").height();
  if(!cardHeaderHeight){
	  cardHeaderHeight=0;
  }
  var cardBodyHeight = parseInt(bodyHeight)-parseInt(cardHeaderHeight)-config.heightVal;
  ////////////end
  
  //账户流水
  table.render({
    elem: '#table-data',
    title: '账户余额列表',
    height:cardBodyHeight,
    method: "POST",
    contentType: 'application/json',
    url: config.appBase+'/acct_banlance/list',
    cols: [[
    	{field: 'bankName', title: '银行名称', width: 120},
    	{field: 'acctNo', title: '账号', width: 200},
    	{field: 'acctName', title: '账户名', width: 200},
    	{field: 'status', title: '账户状态', width: 100, templet: function(d){
    		return common.getRefDesc(common.Constants.Status, d.acctStatus);
    	}, totalRowText: '合计'},
    	{field: 'inAmount', title: '收入(元)', width: 120, totalRow: true},
    	{field: 'outAmount', title: '支出(元)', width: 120, totalRow: true},
    	{field: 'balance', title: '余额(元)', width: 120, totalRow: true},
    	{field: 'queryTime', title: '查询时间', width: 150, templet: function(d){
    		return d.queryTime ? d.queryTime.substring(0,14) : "";
    	}}
    ]],
    done:function(res, curr, count){    //res 接口返回的信息

    },
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
    totalRow:true,
    page: true, //是否显示分页
    limits:config.tableLimits,
    limit: config.tableDefaultLimit,
    toolbar: '#table-toolbar',
    defaultToolbar: ["filter","print"]
  });

  //事件
  var active = {
		  exportFunc: function(){
			  window.location.href = config.appBase+'/export/acct_balance';
		  }
    };
  
  //工具栏事件
  table.on('toolbar(table-data)', function(obj){
    switch(obj.event){
      case 'export':
    	  active.exportFunc();
    	  break;
    };
  });
  exports('acct_balance/list', {});
});