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

  var searchData = {
		  "acctNo":searchFormObj.find("input[name='acctNo']").val(),
		  "beginDate":searchFormObj.find("input[name='beginDate']").val(),
		  "endDate":searchFormObj.find("input[name='endDate']").val()
  };
  //监听搜索
  form.on('submit(LAY-btn-search)', function(data){
    var field = data.field;
    var beginDate=field.beginDate;
    var endDate=field.endDate;
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
    url: config.appBase+'/acct_tran_hist/list',
    where: searchData,
    cols: [[
    	{type:'id', title: '账户流水id', hide:true},
    	{field: 'acctNo', title: '账户号', width: 200},
    	{field: 'acctName', title: '账户名', width: 200},
    	{field:'drcrf',title:'drcrf',width:100},
    	{field:'vouhNo',title:'vouhNo',width:100},
    	{field:'debitAmount',title:'debitAmount',width:100},
    	{field:'creditAmount',title:'creditAmount',width:100},
    	{field:'balance',title:'balance',width:100},
    	{field:'recipBkNo',title:'recipBkNo',width:100},
    	{field:'recipBkName',title:'recipBkName',width:100},
    	{field:'recipAccNo',title:'recipAccNo',width:100},
    	{field:'recipName',title:'recipName',width:100},
    	{field:'summary',title:'summary',width:100},
    	{field:'useCN',title:'useCN',width:100},
    	{field:'postScript',title:'postScript',width:100},
    	{field:'busCode',title:'busCode',width:100},
    	{field:'date',title:'date',width:100},
    	{field:'time',title:'time',width:100},
    	{field:'ref',title:'ref',width:100},
    	{field:'oref',title:'oref',width:100},
    	{field:'enSummary',title:'enSummary',width:100},
    	{field:'busType',title:'busType',width:100},
    	{field:'vouhType',title:'vouhType',width:100},
    	{field:'addInfo',title:'addInfo',width:100},
    	{field:'toutfo',title:'toutfo',width:100},
    	{field:'onlySequence',title:'onlySequence',width:100},
    	{field:'agentAcctName',title:'agentAcctName',width:100},
    	{field:'agentAcctNo',title:'agentAcctNo',width:100},
    	{field:'upDtranf',title:'upDtranf',width:100},
    	{field:'valueDate',title:'valueDate',width:100},
    	{field:'trxCode',title:'trxCode',width:100},
    	{field:'ref1',title:'ref1',width:100},
    	{field:'oref1',title:'oref1',width:100},
    	{field:'cASHF',title:'cASHF',width:100},
    	{field:'busiDate',title:'busiDate',width:100},
    	{field:'busiTime',title:'busiTime',width:100},
    	{field:'seqNo',title:'seqNo',width:100},
    	{field:'mgNo',title:'mgNo',width:100},
    	{field:'mgAccNo',title:'mgAccNo',width:100},
    	{field:'mgCurrType',title:'mgCurrType',width:100},
    	{field:'cashExf',title:'cashExf',width:100},
    	{field:'detailNo',title:'detailNo',width:100},
    	{field:'remark',title:'remark',width:100},
    	{field:'tradeTime',title:'tradeTime',width:100},
    	{field:'tradeFee',title:'tradeFee',width:100},
    	{field:'tradeLocation',title:'tradeLocation',width:100},
    	{field:'exRate',title:'exRate',width:100},
    	{field:'forCurrtype',title:'forCurrtype',width:100},
    	{field:'enAbstract',title:'enAbstract',width:100},
    	{field:'openBankNo',title:'openBankNo',width:100},
    	{field:'openBankBIC',title:'openBankBIC',width:100},
    	{field:'openBankName',title:'openBankName',width:100},
    	{field:'subAcctSeq',title:'subAcctSeq',width:100},
    	{field:'tHCurrency',title:'tHCurrency',width:100},
    	{field:'recipBkName1',title:'recipBkName1',width:100},
    	{field:'recipBkNo1',title:'recipBkNo1',width:100},
    	{field:'tInfoNew',title:'tInfoNew',width:100},
    	{field:'refundMsg',title:'refundMsg',width:100},
    	{field:'busTypNo',title:'busTypNo',width:100},
    	{field:'receiptInfo',title:'receiptInfo',width:100},
    	{field:'telNo',title:'telNo',width:100},
    	{field:'mDCARDNO',title:'mDCARDNO',width:100},
    	{field:'trxAmt',title:'trxAmt',width:100},
    	{field:'trxCurr',title:'trxCurr',width:100},
    	{field:'currType',title:'currType',width:100},
    	{field:'downType',title:'下载方式',width:100, templet: function(d){
    		return common.getRefDesc(common.Constants.DownType, d.downType);
    	}},
    	{title: '操作', width: 80, align: 'center', fixed: 'right', templet: function(d){
    		var str = '<div>';
			str += '<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="datial"><i class="layui-icon layui-icon-edit"></i>详情</a>';
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
    defaultToolbar: ["filter","print"]
  });

  //事件

  //事件
  var active = {
		  datial:function(id){
		      layer.open({
		          type: 2,
		          title: '交易详情',
		          content: 'detail.html',
		          maxmin: true,
		          area: ['500px', '450px'],
		          success: function(layero, index){
		        	  layer.full(index);
		        	  var iframeContent = layero.find('iframe').contents();
		        	  iframeContent.find("input[name='id']").val(id);
		          }
		       }); 
		  },
		  exportFunc: function(){
			  var url = config.appBase+'/export/acct_tran_hist?';
			  if(searchData){
				  for(var i in searchData){
					  url += (i + "=" + (searchData[i]?searchData[i]:"") + "&");
				  }
			  }
			  window.location.href = url;
		  }
    };
  
  //监听行工具事件
  table.on('tool(table-data)', function(obj){
	  var type = obj.event;
	  active[type] ? active[type].call(this, obj.data.id) : '';
  });
  
  //工具栏事件
  table.on('toolbar(table-data)', function(obj){
    switch(obj.event){
      case 'export':
    	  active.exportFunc();
    	  break;
    };
  });

  exports('acct_tran_hist/list', {});
});