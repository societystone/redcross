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
  common.initSelect(searchFormObj.find("select[name='status']"),common.getSysRefDef(common.Constants.Status,'1'));
  form.render("select"); //更新
  form.val("layui-form-search",{status:"1"});

  var searchData = {
		  "name":searchFormObj.find("input[name='name']").val(),
		  "status":searchFormObj.find("select[name='status']").val()
  };
  //监听搜索
  form.on('submit(LAY-btn-search)', function(data){
    var field = data.field;
    //执行重载
    table.reload('table-data', {
      where: field
    });
  });
  
  //角色管理
  table.render({
    elem: '#table-data',
    title: '角色数据表',
    height:cardBodyHeight,
    method: "POST",
    contentType: 'application/json',
    url: config.appBase+'/sys/role/list',
    where: searchData,
    cols: [[
    	{type: 'checkbox', fixed: 'left'},
    	{type:'id', title: '角色id', hide:true},
    	{field: 'name', title: '角色名', minWidth: 100},
    	{field: 'rank', title: '排序', width: 60},
    	{field: 'status', title: '状态', width: 60, templet: function(d) {
    		return common.getRefDesc(common.Constants.Status,d.status);
    	}},
    	{field: 'remark', title: '备注', minWidth: 100},
    	{title: '操作', width: 220, align: 'center', fixed: 'right', toolbar: '#table-operate'}
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
		          title: '添加角色',
		          content: 'form.html',
		          maxmin: true,
		          area: ['500px', '450px'],
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
		              $.post({
		              	  type: "POST",
		              	  contentType: 'application/json',
		              	  url: config.appBase+'/sys/role', 
		              	  data: JSON.stringify(field),
		              	  dataType : 'json',
		              	  success: function(res){
		              		  if(res.code==0){
		              			  common.loadLoaclData(common.Constants.Role);
		              			  layer.msg("添加角色成功！");
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
		          title: '编辑角色',
		          content: 'form.html',
		          maxmin: true,
		          area: ['500px', '450px'],
		          success: function(layero, index){
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
		              	  url: config.appBase+'/sys/role', 
		              	  data: JSON.stringify(field),
		              	  dataType:'json',
		              	  success: function(res){
		              		  if(res.code==0){
		              			  common.loadLoaclData(common.Constants.Role);
		              			  layer.msg("编辑角色成功！");
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
			  layer.confirm('确定删除此角色？', function(index){
				  $.ajax({
	              	  type: "DELETE",
	              	  contentType: 'application/json',
	              	  url: config.appBase+'/sys/role/'+id, 
	              	  dataType:'json',
	              	  success: function(res){
	              		  if(res.code==0){
	              			  common.loadLoaclData(common.Constants.Role);
	              			  layer.msg("删除角色成功！");
	    		              table.reload('table-data'); //数据刷新
	              		  }else{
	              			layer.msg(ret.msg);
	              		  }
	    				  layer.close(index);
	                  }
	              });
			  });
		  },
		  batchdel: function(checkData){
		      if(checkData.length === 0){
		    	  return layer.msg('请选择数据');
		      }
		      layer.confirm('确定删除吗？', function(index) {
      			  common.loadLoaclData(common.Constants.Role);
		          table.reload('table-data');
		          layer.msg('已删除');
		      });
		  },
		  auz:function(id){
		      layer.open({
		          type: 2,
		          title: '设置权限',
		          content: 'permissionform.html',
		          maxmin: true,
		          area: ['500px', '450px'],
		          success: function(layero, index){
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
		            	var menuIdArr = new Array();
		            	for(var i in field){
		            		if(i.indexOf("menuId[")!=-1){
		            			menuIdArr.push(field[i]);
			            	}
			            }
		            	field["menus"] = menuIdArr;
		              $.ajax({
		              	  type: "POST",
		              	  contentType: 'application/json',
		              	  url: config.appBase+'/sys/role/menu', 
		              	  data: JSON.stringify(field),
		              	  dataType:'json',
		              	  success: function(res){
		              		  if(res.code==0){
		              			  layer.msg("设置权限成功！");
//		    		              table.reload('table-data'); //数据刷新
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
		  }
    };
  
  //工具栏事件
  table.on('toolbar(table-data)', function(obj){
    switch(obj.event){
      case 'add':
    	  active.add();
    	  break;
      case 'batchdel':
    	  var checkData = table.checkStatus(obj.config.id);
    	  active.batchdel(checkData);
    	  break;
    };
  });
  
  //监听行工具事件
  table.on('tool(table-data)', function(obj){
	  var type = obj.event;
	  active[type] ? active[type].call(this, obj.data.id) : '';
  });

  exports('role/list', {});
});