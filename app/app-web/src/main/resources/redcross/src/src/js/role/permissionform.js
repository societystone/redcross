/**
 * wangtw
 * @param exports
 * @returns
 */
layui.define(['common','authtree'], function(exports){
  var $ = layui.jquery;
  var config = layui.config;
  var form = layui.form;
  var common = layui.common;
  var authtree = layui.authtree;
  var formObj = $(".layui-form");
  
  //加载所有菜单
  $.ajax({
  	  type: "GET",
  	  contentType: 'application/json',
  	  url: config.appBase+'/sys/menu/all',
  	  dataType : 'json',
  	  success: function(res){
  		  if(res.code==0){
  			  if(res.data!=null){
	  			  var data = res.data;
	  			  authtree.render('#layui-auth-tree-index', data, {
					inputname: 'menuId[]'
					,layfilter: 'lay-check-auth'
					// ,autoclose: false
					// ,autochecked: false
					// ,openchecked: true
					,openall: true
					,autowidth: true
	  			  });
	  			  var id = formObj.find("input[name='id']").val();
		  		  if(id){
		  			  $.ajax({
		  			  	  type: "GET",
		  			  	  contentType: 'application/json',
		  			  	  url: config.appBase+'/sys/role/menu/'+id,
		  			  	  dataType : 'json',
		  			  	  success: function(res){
		  			  		  if(res.code==0){
		  			  			  if(res.data!=null){
		  				  			  var data = res.data;
		  				  			  for(var i in data){
		  				  				  authtree.setChecked('#layui-auth-tree-index', data[i]);
		  				  			  }
		  			  			  }
		  			  		  }else{
		  			  			  layer.msg(res.msg);
		  			  		  }
		  			      }
		  			    });
		  		  }
  			  }
  		  }else{
  			  layer.msg(res.msg);
  		  }
      }
    });
  
  
  
  exports('role/permissionform', {});
});