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
    title: '账户数据下载列表',
    height:cardBodyHeight,
    method: "POST",
    contentType: 'application/json',
    url: config.appBase+'/acct_query_hist/list',
    cols: [[
    	{field: 'bankName', title: '银行名称', width: 120},
    	{field: 'acctNo', title: '账号', width: 200},
    	{field: 'acctName', title: '账户名', width: 200},
    	{field: 'queryDate', title: '下载日期', width: 100},
    	{field: 'isNotDownload', title: '下载类型', width: 150, templet: function(d){
    		return common.getRefDesc(common.Constants.Download, d.isNotDownload);
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
    page: true, //是否显示分页
    limits:config.tableLimits,
    limit: config.tableDefaultLimit,
    toolbar: '#table-toolbar',
    defaultToolbar: ["filter"]
  });

  //事件
  var active = {
		  download:function(){
		      layer.open({
		          type: 2,
		          title: '下载银行数据',
		          content: 'form.html',
		          maxmin: false,
		          area: ['500px', '450px'],
		          success: function(layero, index){
//		        	  layer.full(index);
		          },
		          btn: ['确定', '取消'],
		          yes: function(index, layero){
		            var iframeWindow = window['layui-layer-iframe'+ index];
		            var submitID = 'layui-submit-btn';
		            var submit = layero.find('iframe').contents().find('#'+ submitID);
		
		            //监听提交
		            iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
		            	common.disabledButton(submit,true);
			              var field = data.field; //获取提交的字段
			              var beginDate = field.beginDate;
			              var endDate = field.endDate;
			              if(!beginDate){
			              	layer.msg("开始时间为空！");
			              	return;
			              }
			              if(!endDate){
			              	layer.msg("结束时间为空！");
			              	return;
			              }
			              if(!beginDate && !endDate && beginDate>endDate){
			              	layer.msg("开始时间大于结束时间！");
			              	return;
			              }
			             
		              $.ajax({
		              	  type: "PUT",
		              	  contentType: 'application/json',
		              	  url: config.appBase+'/acct_hist/download', 
		              	  data: JSON.stringify(field),
		              	  dataType:'json',
		              	  async:true
		              	  /*success: function(res){
		              		  if(res.code==0){
		              			  layer.msg("下载数据成功！");
		    		              table.reload('table-data'); //数据刷新
		    		              layer.close(index); //关闭弹层
		              		  }else{
		              			  layer.msg(res.msg);
		              			  common.disabledButton(submit,false);
		              		  }
		                  }*/
		                });
		              layer.msg("下载数据指令已发送，请稍后查询结果！");
		              table.reload('table-data'); //数据刷新
		              layer.close(index); //关闭弹层
		            });  
		            submit.trigger('click');
		          }
		       }); 
		  }
    };
  
  //工具栏事件
  table.on('toolbar(table-data)', function(obj){
    switch(obj.event){
      case 'download':
    	  if(!common.isHasPermission(common.Constants.PermissionType1)){
    		  layer.msg("无操作权限！");
    		  return;
    	  }
    	  active.download();
    	  break;
    };
  });
  exports('acct_data_download/list', {});
});