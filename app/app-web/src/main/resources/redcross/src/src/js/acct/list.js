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
		  "acctName":searchFormObj.find("input[name='acctName']").val()
  };
  //监听搜索
  form.on('submit(LAY-btn-search)', function(data){
    var field = data.field;
    //执行重载
    table.reload('table-data', {
      where: field
    });
  });
  
  //用户管理
  table.render({
    elem: '#table-data',
    title: '用户数据表',
    height:cardBodyHeight,
    method: "POST",
    contentType: 'application/json',
    url: config.appBase+'/acct/selectlist',
    where: searchData,
    cols: [[
    	{type:'id', title: '账户id', hide:true},
    	{field: 'acctNo', title: '账户号', width: 300},
    	{field: 'acctName', title: '账户名', width: 250},
    	{field: 'createDate', title: '账户创建时间', width: 150},
    	{field: 'status', title: '账户状态', width: 150},
    	{field: 'modifyDate', title: '账户最后修改时间', width: 150},
    	{field: 'modifyDate', title: '账户最后修改时间', width: 150},
    	{field: 'userName', title: '账户创建人', width: 150},
    	{field: 'userNameLastModify', title: '账户最后修改人', width: 150},
    	{title: '操作', width: 200, align: 'center', fixed: 'right', templet: function(d){
    		var str = '<div>';
			if(d.status!='9'){//未删除状态
				if(d.status=='1'){
					str += '<a class="layui-btn layui-btn-warm layui-btn-xs" lay-event="stop"><i class="layui-icon layui-icon-pause"></i>停用</a>';
				}else{
	    			str += '<a class="layui-btn layui-btn-start layui-btn-xs" lay-event="start"><i class="layui-icon layui-icon-play"></i>启用</a>';
				}
				str += '<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit"><i class="layui-icon layui-icon-edit"></i>编辑</a>';
	    		str += '<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i class="layui-icon layui-icon-delete"></i>删除</a>';
				
			}
		
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
    defaultToolbar: ['filter']
  });

  //事件

  //事件
  var active = {
		  add: function(){
		      layer.open({
		          type: 2,
		          title: '添加账户',
		          content: 'form.html',
		          maxmin: true,
		          area: ['500px', '450px'],
		          success: function(layero, index){
		        	  layer.full(index);
		          },
		          btn: ['确定', '取消'],
		          yes: function(index, layero){
		            var iframeWindow = window['layui-layer-iframe'+ index];
		            var submitID = 'layui-submit-btn';
		            var iframeContent = layero.find('iframe').contents();
		            var submit = iframeContent.find('#'+ submitID);
		
		            //监听提交
		            iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
		            	common.disabledButton(submit,true);
		              var field = data.field; //获取提交的字段
		             
		              $.ajax({
		              	  type: "POST",
		              	  contentType: 'application/json',
		              	  url: config.appBase+'/acct/add', 
		              	  data: JSON.stringify(field),
		              	  dataType : 'json',
		              	  success: function(res){
		              		  if(res.code==0){
		              			  layer.msg("添加账户成功！");
		    		              table.reload('table-data'); //数据刷新
		    		              layer.close(index); //关闭弹层
		              		  }else{
		              			layer.msg("添加账户失败！");
		              			  common.disabledButton(submit,false);
		              		  }
		                  }
		                });
		            });
		            submit.trigger('click');
		          }
		       }); 
		  },
		  edit:function(id){
		      layer.open({
		          type: 2,
		          title: '编辑账户',
		          content: 'form.html',
		          maxmin: true,
		          area: ['500px', '450px'],
		          success: function(layero, index){
		        	  layer.full(index);
		        	  var iframeContent = layero.find('iframe').contents();
		        	  iframeContent.find("input[name='id']").val(id);
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
			             
		              $.ajax({
		              	  type: "PUT",
		              	  contentType: 'application/json',
		              	  url: config.appBase+'/acct/updateAcct', 
		              	  data: JSON.stringify(field),
		              	  dataType:'json',
		              	  success: function(res){
		              		  if(res.code==0){
		              			  layer.msg("编辑账户成功！");
		    		              table.reload('table-data'); //数据刷新
		    		              layer.close(index); //关闭弹层
		              		  }else{
		              			  layer.msg("编辑账户失败！");
		              			  common.disabledButton(submit,false);
		              		  }
		                  }
		                });
		            });  
		            submit.trigger('click');
		          }
		       }); 
		  },
		  del: function(id){
			  layer.confirm('确定删除此账户？', function(index){
				  $.ajax({
	              	  type: "POST",
	              	  contentType: 'application/json',
	              	  url: config.appBase+'/acct/stopOrStartAcct/'+id+'/9', 
	              	  dataType:'json',
	              	  success: function(res){
	              		  if(res.code==0){
	              			  layer.msg("删除账户成功！");
	    		              table.reload('table-data'); //数据刷新
	              		  }else{
	              			 layer.msg("删除账户失败！");
	              		  }
	    				  layer.close(index);
	                  }
	              });
			  });
		  },
		  start: function(id){
			  layer.confirm('确定启用此账户？', function(index){
				  $.ajax({
	              	  type: "POST",
	              	  contentType: 'application/json',
	              	  url: config.appBase+'/acct/stopOrStartAcct/'+id+'/1', 
	              	  dataType:'json',
	              	  success: function(res){
	              		  if(res.code==0){
	              			  layer.msg("启用账户成功！");
	    		              table.reload('table-data'); //数据刷新
	              		  }else{
	              			layer.msg("启用账户失败！");
	              		  }
	    				  layer.close(index);
	                  }
	              });
			  });
		  },
		  stop: function(id){
			  layer.confirm('确定停用此账户？', function(index){
				  $.ajax({
	              	  type: "POST",
	              	  contentType: 'application/json',
	              	  url: config.appBase+'/acct/stopOrStartAcct/'+id+'/2', 
	              	  dataType:'json',
	              	  success: function(res){
	              		  if(res.code==0){
	              			  layer.msg("停用账户成功！");
	    		              table.reload('table-data'); //数据刷新
	              		  }else{
	              			 layer.msg("停用账户失败！");
	              		  }
	    				  layer.close(index);
	                  }
	              });
			  });
		  }
    };
  
  //监听行工具事件
  table.on('tool(table-data)', function(obj){
	  var type = obj.event;
	  active[type] ? active[type].call(this, obj.data.id) : '';
  });

  exports('acct/list', {});
});