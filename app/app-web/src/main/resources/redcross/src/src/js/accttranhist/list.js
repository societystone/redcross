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

  var searchData = {
		  "acctNo":searchFormObj.find("input[name='acctNo']").val(),
		  "queryStartTime":searchFormObj.find("input[name='queryStartTime']").val(),
		  "queryEndTime":searchFormObj.find("input[name='queryEndTime']").val()
		  
  };
  //监听搜索
  form.on('submit(LAY-btn-search)', function(data){
    var field = data.field;
    //执行重载
    table.reload('table-data', {
      where: field
    });
  });
  
  //账户流水
  table.render({
    elem: '#table-data',
    title: '账户流水列表',
    height:cardBodyHeight,
    method: "POST",
    contentType: 'application/json',
    url: config.appBase+'/accttranhist/selectlist',
    where: searchData,
    cols: [[
    	{type:'id', title: '账户流水id', hide:true},
    	{field: 'acctId', title: '账户id', width: 300},
    	{field: 'account.acctNo', title: '账户号', width: 250},
    	{field: 'account.acctName', title: '账户名', width: 250},
    	{field: 'vouhNo', title: '凭证号', width: 150},
    	{field: 'tradeTime', title: '交易时间', width: 150},
    	{field: 'tradeLocation', title: '交易场所', width: 150},
    	{field: 'trxAmt', title: '交易金额', width: 150},
    	{field: 'trxCurr', title: '交易币种', width: 150},
    	{title: '操作', width: 200, align: 'center', fixed: 'right', templet: function(d){
    		var str = '<div>';
			str += '<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="datial"><i class="layui-icon layui-icon-edit"></i>明细详情</a>';
    		str += '</div>';
    		return str;
    	}
    	}
    	
    ]],
    done:function(res, curr, count){    //res 接口返回的信息

    	    $("[data-field = 'status']").children().each(function(){

    	        if($(this).text() == '1'){

    	            $(this).text("已启用");

    	        }else if($(this).text() == '2'){

    	             $(this).text("已禁用");

    	        }

    	    })
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
    page: true, //是否显示分页
    limits:config.tableLimits,
    limit: config.tableDefaultLimit,
    toolbar: '#table-toolbar',
    defaultToolbar: ['filter']
  });

  //事件

  //事件
  var active = {
		  datial:function(id){
		      layer.open({
		          type: 2,
		          title: '交易详情',
		          content: 'form.html',
		          maxmin: true,
		          area: ['500px', '450px'],
		          success: function(layero, index){
		        	  layer.full(index);
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
  

  exports('accttranhist/list', {});
});