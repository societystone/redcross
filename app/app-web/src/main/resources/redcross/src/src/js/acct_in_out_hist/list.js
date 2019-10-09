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

  var searchFormObj = $(".layui-form");
  common.initSelect(searchFormObj.find("select[name='acctNo']"),common.getSysRefDef(common.Constants.AcctNo,'1'),null);
  form.render(); //更新
  
  var nowDate = new Date();

  var searchData = {
		  "type":searchFormObj.find("select[name='type']").val(),
		  "acctNo":searchFormObj.find("input[name='acctNo']").val(),
		  "beginDate":searchFormObj.find("input[name='beginMonth']").val(),
		  "endDate":searchFormObj.find("input[name='endMonth']").val()
  };
  //监听搜索
  form.on('submit(LAY-btn-search)', function(data){
    var field = data.field;
    var type = searchFormObj.find("select[name='type']").val();
    var beginDate,endDate;
    if(type == "1"){
    	beginDate = searchFormObj.find("input[name='beginDate']").val();
    	endDate = searchFormObj.find("input[name='endDate']").val();
    }else if(type == "2"){
    	beginDate = searchFormObj.find("input[name='beginMonth']").val();
    	endDate = searchFormObj.find("input[name='endMonth']").val();
    }else{
    	return;
    }
    if(!beginDate){
    	layer.msg("开始时间为空！");
    	return;
    }
    if(!endDate){
    	layer.msg("结束时间为空！");
    	return;
    }
    if(beginDate>endDate){
    	layer.msg("开始时间大于结束时间！");
    	return;
    }
    searchData = {
      	"type":type,
      	"acctNo":searchFormObj.find("input[name='acctNo']").val(),
      	"beginDate":beginDate,
      	"endDate":endDate
    };
    //执行重载
    table.reload('table-data', {
      where: searchData
    });
  });
  
  //账户流水
  table.render({
    elem: '#table-data',
    title: '账户收支列表',
    height:cardBodyHeight,
    method: "POST",
    contentType: 'application/json',
    url: config.appBase+'/acct_in_out_hist/list',
    where: searchData,
    cols: [[
    	{field: 'bankName', title: '银行名称', width: 120},
    	{field: 'acctNo', title: '账号', width: 200},
    	{field: 'acctName', title: '账户名', width: 200},
    	{field: 'date', title: '年月', width: 100, totalRowText: '合计'},
    	{field: 'inAmount', title: '收入(元)', width: 120, totalRow: true},
    	{field: 'outAmount', title: '支出(元)', width: 120, totalRow: true},
    	{field: 'balance', title: '余额(元)', width: 120, totalRow: true}
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
    totalRow: true,
    page: true, //是否显示分页
    limits:config.tableLimits,
    limit: config.tableDefaultLimit,
    toolbar: '#table-toolbar',
    defaultToolbar: ["filter","print"]
  });

  //事件
  var active = {
		  exportFunc: function(){
			  var url = config.appBase+'/export/acct_in_out_hist?';
			  if(searchData){
				  for(var i in searchData){
					  url += (i + "=" + (searchData[i]?searchData[i]:"") + "&");
				  }
			  }
			  window.location.href = url;
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
  
  form.on('select(report-type)', function(data){
	  var type = data.value;
	  if(type=="1"){
		  $("#date-range").show();
		  $("#month-range").hide();
	  }else if(type=="2"){
		  $("#date-range").hide();
		  $("#month-range").show();
	  }
  });
  
  exports('acct_in_out_hist/list', {});
});