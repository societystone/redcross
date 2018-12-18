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
  common.initSelect(searchFormObj.find("select[name='departmentId']"),common.getSysRefDef(common.Constants.Department,'1'));
  common.initSelect(searchFormObj.find("select[name='status']"),common.getSysRefDef(common.Constants.Status,'1'));
  form.render("select"); //更新
  form.val("layui-form-search",{status:"1"});

  var searchData = {
		  "realName":searchFormObj.find("input[name='realName']").val(),
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
  
  //用户管理
  table.render({
    elem: '#table-data',
    title: '用户数据表',
    height:cardBodyHeight,
    method: "POST",
    contentType: 'application/json',
    url: config.appBase+'/sys/user/list',
    where: searchData,
    cols: [[
    	{type: 'checkbox', fixed: 'left'},
    	{type:'id', title: '用户id', hide:true},
    	{field: 'username', title: '用户名', minWidth: 100},
    	{field: 'realName', title: '姓名', width: 150},
    	{field: 'sex', title: '性别', width: 60, templet: function(d) {
    		return common.getRefDesc(common.Constants.Sex,d.sex);
    	}},
    	{field: 'phone', title: '手机', width: 150},
    	{field: 'departmentId', title: '部门', minWidth: 100, templet: function(d) {
    		return common.getRefDesc(common.Constants.Department,d.departmentId);
    	}},
    	{field: 'roles', title: '角色', minWidth: 150, templet: function(d) {
    		var str = "";
    		var roles = d.roles;
    		if(roles && roles.length>0){
    			for(var i in roles){
    				str += str ? "," : str;
    				str += common.getRefDesc(common.Constants.Role,roles[i]);
    			}
    		}
    		return str;
    	}},
    	{field: 'status', title: '状态', width: 60, templet: function(d) {
    		return common.getRefDesc(common.Constants.Status,d.status);
    	}},
    	{title: '操作', width: 150, align: 'center', fixed: 'right', toolbar: '#table-operate'}
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
		          title: '添加用户',
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
		              var roleIdArr = new Array();
		              for(var i in field){
		            	  if(i.indexOf("roleId[")!=-1){
		            		  roleIdArr.push(field[i]);
		            	  }
		              }
		              field["roles"] = roleIdArr;
		              $.ajax({
		              	  type: "POST",
		              	  contentType: 'application/json',
		              	  url: config.appBase+'/sys/user', //实际使用请改成服务端真实接口
		              	  data: JSON.stringify(field),
		              	  dataType : 'json',
		              	  success: function(res){
		              		  if(res.code==0){
		              			  layer.msg("添加用户成功！");
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
		          title: '编辑用户',
		          content: 'form.html',
		          maxmin: true,
		          area: ['500px', '450px'],
		          success: function(layero, index){
		        	  var iframeContent = layero.find('iframe').contents();
		        	  iframeContent.find("input[name='id']").val(id);
		        	  iframeContent.find("input[name='username']").attr("disabled",true);
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
		              var roleIdArr = new Array();
		              for(var i in field){
		            	  if(i.indexOf("roleId[")!=-1){
		            		  roleIdArr.push(field[i]);
		            	  }
		              }
		              field["roles"] = roleIdArr;
		              $.ajax({
		              	  type: "PUT",
		              	  contentType: 'application/json',
		              	  url: config.appBase+'/sys/user', 
		              	  data: JSON.stringify(field),
		              	  dataType:'json',
		              	  success: function(res){
		              		  if(res.code==0){
		              			  layer.msg("编辑用户成功！");
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
			  layer.confirm('确定删除此用户？', function(index){
				  $.ajax({
	              	  type: "DELETE",
	              	  contentType: 'application/json',
	              	  url: config.appBase+'/sys/user/'+id, 
	              	  dataType:'json',
	              	  success: function(res){
	              		  if(res.code==0){
	              			  layer.msg("删除用户成功！");
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
		          table.reload('table-data');
		          layer.msg('已删除');
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

  exports('user/list', {});
});