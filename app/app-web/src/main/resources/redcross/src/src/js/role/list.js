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
		  "name":searchFormObj.find("input[name='name']").val()
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
    	{type:'id', title: '角色id', hide:true},
    	{field: 'code', title: '角色代码', width: 100},
    	{field: 'name', title: '角色名', width: 150},
    	{field: 'permissions', title: '数据权限', width: 200, templet: function(d) {
    		var str = "";
    		var permissions = d.permissions;
    		if(permissions && permissions.length>0){
    			for(var i in permissions){
    				str += str ? "," : str;
    				str += common.getRefDesc(common.Constants.Permission,permissions[i].id);
    			}
    		}
    		return str;
    	}},
    	{title: '操作', width: 150, align: 'center', fixed: 'right', templet: function(){
    		var str = '<div>';
    		if(common.isHasPermission(common.Constants.PermissionType1)){
    			str += '<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="auz"><i class="layui-icon layui-icon-auz"></i>设置权限</a>';
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
    defaultToolbar: ['filter']
  });

  //事件
  var active = {
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
			            var permissionArr = new Array();
			            for(var i in field){
			            	if(i.indexOf("permissionId[")!=-1){
			            		permissionArr.push({
			            			"id":field[i]
			            		});
			            	}
			            }
			            field["permissions"] = permissionArr;
		            	var menuArr = new Array();
		            	for(var i in field){
		            		if(i.indexOf("menuId[")!=-1){
		            			menuArr.push({
			            			"id":field[i]
		            			});
			            	}
			            }
		            	field["menus"] = menuArr;
		              $.ajax({
		              	  type: "POST",
		              	  contentType: 'application/json',
		              	  url: config.appBase+'/sys/role/permission', 
		              	  data: JSON.stringify(field),
		              	  dataType:'json',
		              	  success: function(res){
		              		  if(res.code==0){
		              			  layer.msg("设置权限成功！");
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
		  }
    };
  
  //监听行工具事件
  table.on('tool(table-data)', function(obj){
	  var type = obj.event;
	  active[type] ? active[type].call(this, obj.data.id) : '';
  });

  exports('role/list', {});
});