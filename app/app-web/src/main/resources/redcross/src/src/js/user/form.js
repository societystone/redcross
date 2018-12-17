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
  common.initRadio(formObj.find("div[name='sexItem']"),common.getSysRefDef(common.Constants.Sex,'1'),'sex','M');
  common.initSelect(formObj.find("select[name='departmentId']"),common.getSysRefDef(common.Constants.Department,'1'));
  common.initCheckbox(formObj.find("div[name='roleItem']"),common.getSysRefDef(common.Constants.Role,'1'),'roleId');
  form.render(); //更新
  
  function selectDepartmentId(id){
	  if(id){
		  $.ajax({
	      	  type: "GET",
	      	  contentType: 'application/json',
	      	  url: config.appBase+'/sys/department/'+id, 
	      	  async : false,
	      	  dataType : 'json',
	      	  success: function(res){
	      		  if(res.code==0 && res.data!=null && res.data.roles!=null && res.data.roles.length>0){
	      			  var roles = res.data.roles;
	      			  var roleArr = new Array();
	      			  for(var i in roles){
    					  var roleId = roles[i];
    					  roleArr.push({
    						  value:roleId,
    						  title:common.getRefDesc(common.Constants.Role,roleId)
    					  });
    				  }
	      			  common.initCheckbox(formObj.find("div[name='roleItem']"),roleArr,'roleId');
	      		  }else{
	      			  common.initCheckbox(formObj.find("div[name='roleItem']"),null,'roleId');
	      		  }
	      		  form.render(); //更新
	          }
	        });
	  }else{
		  common.initCheckbox(formObj.find("div[name='roleItem']"),common.getSysRefDef(common.Constants.Role,'1'),'roleId');
		  form.render(); //更新
	  }
  }
  
  form.on('select(departmentId)', function(data){
	  selectDepartmentId(data.value);
  });
  
  var id = formObj.find("input[name='id']").val();
  if(id){
	  $.ajax({
      	  type: "GET",
      	  contentType: 'application/json',
      	  url: config.appBase+'/sys/user/'+id,
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
	      			  selectDepartmentId(data.departmentId);
	      			  form.val("layui-form",data);
	      		  }
      		  }else{
      			  layer.msg(res.msg);
      		  }
          }
        });
  }
  
  exports('user/form', {});
});