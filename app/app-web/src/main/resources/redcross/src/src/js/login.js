/**
 * 用户登入
 * @param exports
 * @returns
 */
 
layui.define(['config','form'], function(exports){
  var $ = layui.jquery
  ,config = layui.config
  ,layer = layui.layer
  ,laytpl = layui.laytpl
  ,form = layui.form;
  form.on('submit(LAY-user-login-submit)', function(data){
	  $.post(
		  config.appBase+'/sys/login',
		  JSON.stringify(data.field),
		  function(result){
		        result = JSON.parse(result);
		        if(result.code==0){
			        layui.data(config.tableName, {
			            key: config.loginUser,
			            value: result.data
			        });
				    //登入成功的提示与跳转
			        layer.msg('登入成功', {
			          offset: '15px',
			          icon: 1,
			          time: 1000
			        }, function(){
			            location.href = 'index.html'; //后台主页
			        });
		        }else{
		        	layer.msg(result.msg);
		        }
		    }
	  );
	});
  //对外暴露的接口
  exports('login', {});
});