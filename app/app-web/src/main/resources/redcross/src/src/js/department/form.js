/**
 * wangtw
 * @param exports
 * @returns
 */
layui.define(['common'], function(exports){
  var $ = layui.jquery;
  var config = layui.config;
  var form = layui.form;
  var common = layui.common;

  var formObj = $(".layui-form");
  common.initCheckbox(formObj.find("div[name='roleItem']"),common.getSysRefDef(common.Constants.Role,'1'),'roleId');
  form.render(); //更新
  
  var id = formObj.find("input[name='id']").val();
  if(id){
	  $.ajax({
      	  type: "GET",
      	  contentType: 'application/json',
      	  url: config.appBase+'/sys/department/'+id,
      	  dataType : 'json',
      	  success: function(res){
      		  if(res.code==0){
      			  if( res.data!=null){
	      			  var data = res.data;
	      			  if(data.roles!=null && data.roles.length>0){
	      				  for(var i in data.roles){
	      					  var roleId = data.roles[i];
	      					  data["roleId["+roleId+"]"] = roleId;
	      				  }
	      			  }
	      			  form.val("layui-form",data);
	      		  }
      		  }else{
      			  layer.msg(res.msg);
      		  }
          }
        });
  }
  
  exports('department/form', {});
});