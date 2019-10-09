/**
 * wangtw
 * 
 * @param exports
 * @returns
 */
layui.define(['common'], function(exports){
  var $ = layui.jquery;
  var config = layui.config;
  var form = layui.form;
  var common = layui.common;
  var formObj = $(".layui-form");
  var id = formObj.find("input[name='id']").val();
	  
  // 加载所有菜单
  $.ajax({
  	  type: "GET",
  	  contentType: 'application/json',
  	  url: config.appBase+'/user/acctNo/list/'+id,
  	  dataType : 'json',
  	  success: function(res){
  		  if(res.code==0){
  			  if(res.data!=null){
	  			  var data = res.data;
	  			  var checkArr = new Array();
	  			  var setData = {};
	  			  for(var i in data){
	  				  var acctId = data[i].id;
	  				  checkArr.push({
	  					  value:acctId,
	  					  title:data[i].acctNo+"-"+data[i].acctName
	  				  });
	  				  if(data[i].flag){
	  					  setData["acctId["+acctId+"]"] = acctId;
	  				  }
	  			  }
	  			  common.initCheckbox(formObj.find("div[name='permissionItem']"),checkArr,'acctId');
	  			  form.render(); // 更新
      			  form.val("layui-form",setData);
  			  }
  		  }else{
  			  layer.msg(res.msg);
  		  }
      }
    });
  
  
  
  exports('user/permissionform', {});
});