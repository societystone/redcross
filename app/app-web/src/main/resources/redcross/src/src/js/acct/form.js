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
  common.initSelect(formObj.find("select[name='acctType']"),common.getSysRefDef(common.Constants.AcctType,'1'),null);
  common.initSelect(formObj.find("select[name='capitalChannel']"),common.getSysRefDef(common.Constants.Channel,'1'),null);
  form.render(); //更新
  
  
  var id = formObj.find("input[name='id']").val();
  if(id){
	  $.ajax({
      	  type: "GET",
      	  contentType: 'application/json',
      	  url: config.appBase+'/acct/select/'+id,
      	  dataType : 'json',
      	  success: function(res){
      		  if(res.code==0){
      			  if( res.data!=null){
	      			  var data = res.data;
	      			  form.val("layui-form",data);
	      		  }
      		  }else{
      			  layer.msg(res.msg);
      		  }
          }
        });
  }
  
  exports('acct/form', {});
});