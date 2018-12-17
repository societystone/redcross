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
  var id = formObj.find("input[name='id']").val();

  //自定义验证
  form.verify({
	  oldPassword: function(value){
		  if(!value){
			  return '当前密码不能为空';
	      }else{
	    	  var flag=false;
	    	  var msg = "";
	    	  $.ajax({
              	  type: "POST",
              	  contentType: 'application/json',
              	  url: config.appBase+'/sys/user/checkPwd',
			  	  async : false,
              	  data: JSON.stringify({
              		  "id":id,
              		  "password":value
              	  }),
              	  dataType:'json',
              	  success: function(res){
              		  if(res.code==0){
              			  flag = true;
              		  }else{
              			  msg = res.msg;
              		  }
                  }
              });
	    	  if(!flag){
	    		  return msg;
	    	  }
	      }
	  },
	  password: function(value){
		  if(!value){
			  return '新密码不能为空';
	      }else if(value == formObj.find("input[name='oldPassword']").val()){
			  return '新密码不能和当前密码相同';
		  }else if(!/^[\S]{6,12}$/.test(value)){
			  return '密码必须6到12位，且不能出现空格';
		  }
	  },
	  rePassword: function(value){
		  if(!value){
			  return '请再次输入新密码';
	      }else if(value != formObj.find("input[name='password']").val()){
			  return '两次密码输入不一致';
		  }
	  }
  });
  
  
  exports('user/modifyPwdForm', {});
});