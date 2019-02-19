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
  var index=1;
  
  function add_one_mapping(obj){

	var obj = $("#layui-add-btn").parent();
	form.render();
  }
  
  function init_mapping(data){
	  if(data!=null && data.length>0){
		  for(var i=0;i<data.length;i++){
			  var obj = data[i];
			  add_one_mapping(obj);
		  }
	  }
  }

  var formObj = $(".layui-form");
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
	      			  var templateDetails = data.templateDetails;
	      			  if(templateDetails!=null && templateDetails.length>0){
	      				init_mapping(templateDetails);
	      			  }
	      		  }
      		  }else{
      			  layer.msg(res.msg);
      		  }
          }
        });
  }else{
	  add_one_mapping(null);
  }
  
  $("#layui-add-btn").click(function(){
	  add_one_mapping(null);
  });
  
  exports('acct/form', {});
});