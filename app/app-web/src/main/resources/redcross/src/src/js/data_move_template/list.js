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
  common.initSelect(searchFormObj.find("select[name='status']"),common.getSysRefDef(common.Constants.Status,'1'),"");
  common.initSelect(searchFormObj.find("select[name='isUse']"),common.getSysRefDef(common.Constants.YNFlag,'1'),"");
  form.render("select"); //更新

  var searchData = {
		  "name":searchFormObj.find("input[name='name']").val(),
		  "status":searchFormObj.find("select[name='status']").val(),
		  "isUse":searchFormObj.find("select[name='isUse']").val()
  };
  //监听搜索
  form.on('submit(LAY-btn-search)', function(data){
    var field = data.field;
    //执行重载
    table.reload('table-data', {
      where: field
    });
  });
  
  //表格
  table.render({
    elem: '#table-data',
    title: '数据迁移模板数据表',
    height:cardBodyHeight,
    method: "POST",
    contentType: 'application/json',
    url: config.appBase+'/dataMoveTemplate/list',
    where: searchData,
    cols: [[
    	{type:'id', title: '模板ID', hide:true},
    	{field: 'name', title: '模板名称', width: 150},
    	{field: 'status', title: '状态', width: 60, templet: function(d) {
    		return common.getRefDesc(common.Constants.Status,d.status);
    	}},
    	{field: 'isUse', title: '是否被使用', width: 100, templet: function(d) {
    		return common.getRefDesc(common.Constants.YNFlag,d.isUse);
    	}},
    	{field: 'createDate', title: '创建时间', width: 180},
    	{field: 'remark', title: '备注', width: 200},
    	{title: '操作', width: 200, align: 'center', fixed: 'right', templet: function(d){
    		var str = '<div>';
    		if(common.isHasPermission(common.Constants.PermissionType1)){
    			if(d.status!='3'){//未删除状态
    				if(d.status=='1'){
    					str += '<a class="layui-btn layui-btn-warm layui-btn-xs" lay-event="stop"><i class="layui-icon layui-icon-pause"></i>停用</a>';
    				}else{
    	    			str += '<a class="layui-btn layui-btn-start layui-btn-xs" lay-event="start"><i class="layui-icon layui-icon-play"></i>启用</a>';
    				}
    				if(d.isUse!='Y'){//未被使用
    					str += '<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit"><i class="layui-icon layui-icon-edit"></i>编辑</a>';
    	    			str += '<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i class="layui-icon layui-icon-delete"></i>删除</a>';
    				}
    			}
    		}
    		str += '</div>';
    		return str;
    	}}
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
    toolbar: '#table-toolbar',
    defaultToolbar: ['filter']
  });

  //事件
  var active = {
		  add: function(){
		      layer.open({
		          type: 2,
		          title: '添加模板',
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
		              var templateDetails = new Array();
			            for(var i in field){
			            	if(i.indexOf("dataType[")!=-1){
			            		var str = i.substring(i.indexOf("[")+1, i.length-1);
			            		templateDetails.push({
			            			"dataType":field[i],
			            			"remoteTable":field["remoteTable["+str+"]"],
			            			"remoteColumn":field["remoteColumn["+str+"]"]
			            		});
			            	}
			            }
			            field["templateDetails"] = templateDetails;
		              $.ajax({
		              	  type: "POST",
		              	  contentType: 'application/json',
		              	  url: config.appBase+'/dataMoveTemplate', 
		              	  data: JSON.stringify(field),
		              	  dataType : 'json',
		              	  success: function(res){
		              		  if(res.code==0){
//		              			  common.loadLoaclData(common.Constants.Role);
		              			  layer.msg("添加模板成功！");
		    		              table.reload('table-data'); //数据刷新
		    		              layer.close(index); //关闭弹层
		              		  }else{
		              			  layer.msg(res.msg);
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
		          title: '编辑模板',
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
			              var templateDetails = new Array();
				            for(var i in field){
				            	if(i.indexOf("dataType[")!=-1){
				            		var str = i.substring(i.indexOf("[")+1, i.length-1);
				            		templateDetails.push({
				            			"dataType":field[i],
				            			"remoteTable":field["remoteTable["+str+"]"],
				            			"remoteColumn":field["remoteColumn["+str+"]"]
				            		});
				            	}
				            }
				            field["templateDetails"] = templateDetails;
		              $.ajax({
		              	  type: "PUT",
		              	  contentType: 'application/json',
		              	  url: config.appBase+'/dataMoveTemplate', 
		              	  data: JSON.stringify(field),
		              	  dataType:'json',
		              	  success: function(res){
		              		  if(res.code==0){
//		              			  common.loadLoaclData(common.Constants.Role);
		              			  layer.msg("编辑模板成功！");
		    		              table.reload('table-data'); //数据刷新
		    		              layer.close(index); //关闭弹层
		              		  }else{
		              			  layer.msg(res.msg);
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
			  layer.confirm('确定删除此模板？', function(index){
				  $.ajax({
	              	  type: "DELETE",
	              	  contentType: 'application/json',
	              	  url: config.appBase+'/dataMoveTemplate/'+id, 
	              	  dataType:'json',
	              	  success: function(res){
	              		  if(res.code==0){
	              			  layer.msg("删除模板成功！");
	    		              table.reload('table-data'); //数据刷新
	              		  }else{
	              			layer.msg(ret.msg);
	              		  }
	    				  layer.close(index);
	                  }
	              });
			  });
		  },
		  start: function(id){
			  layer.confirm('确定启用此模板？', function(index){
				  $.ajax({
	              	  type: "POST",
	              	  contentType: 'application/json',
	              	  url: config.appBase+'/dataMoveTemplate/startUse/'+id, 
	              	  dataType:'json',
	              	  success: function(res){
	              		  if(res.code==0){
	              			  layer.msg("启用模板成功！");
	    		              table.reload('table-data'); //数据刷新
	              		  }else{
	              			layer.msg(ret.msg);
	              		  }
	    				  layer.close(index);
	                  }
	              });
			  });
		  },
		  stop: function(id){
			  layer.confirm('确定停用此模板？', function(index){
				  $.ajax({
	              	  type: "POST",
	              	  contentType: 'application/json',
	              	  url: config.appBase+'/dataMoveTemplate/stopUse/'+id, 
	              	  dataType:'json',
	              	  success: function(res){
	              		  if(res.code==0){
	              			  layer.msg("停用模板成功！");
	    		              table.reload('table-data'); //数据刷新
	              		  }else{
	              			layer.msg(ret.msg);
	              		  }
	    				  layer.close(index);
	                  }
	              });
			  });
		  }
    };
  
  //工具栏事件
  table.on('toolbar(table-data)', function(obj){
    switch(obj.event){
      case 'add':
    	  active.add();
    	  break;
    };
  });
  
  //监听行工具事件
  table.on('tool(table-data)', function(obj){
	  var type = obj.event;
	  active[type] ? active[type].call(this, obj.data.id) : '';
  });

  exports('data_move_template/list', {});
});