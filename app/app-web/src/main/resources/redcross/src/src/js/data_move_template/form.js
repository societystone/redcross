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
	  var dataType='';
	  var remoteTable='';
	  var remoteColumn='';
	  if(obj){
		  dataType=obj.dataType;
		  remoteTable=obj.remoteTable;
		  remoteColumn=obj.remoteColumn;
	  }
	  var html='<div class="layui-form-item form-div-group">';
		html+='	<div class="layui-inline">';
		html+='		<label class="layui-form-label">数据类型</label>';
		html+='		<div class="layui-input-block">';
		html+='			<select name="dataType['+index+']">';
		html+= common.getSelectOption(common.getSysRefDef(common.Constants.DataMoveType,'1'), dataType);
		html+='			</select>';
		html+='		</div>';
		html+='	</div>';
		html+='	<div class="layui-inline">';
		html+='		<label class="layui-form-label">远程表名</label>';
		html+='		<div class="layui-input-block">';
		html+='			<select name="remoteTable['+index+']">';
		html+= common.getSelectOption(common.getSysRefDef(common.Constants.RemoteTable,'1'), remoteTable);
		html+='			</select>';
		html+='		</div>';
		html+='	</div>';
		html+='	<div class="layui-inline">';
		html+='		<label class="layui-form-label">远程表字段</label>';
		html+='		<div class="layui-input-block">';
		html+='			<select name="remoteColumn['+index+']">';
		html+= common.getSelectOption(common.getSysRefDef(common.Constants.RemoteTableColumn+remoteTable,'1'), remoteColumn);
		html+='			</select>';
		html+='		</div>';
		html+='	</div>';
		html+='	<div class="layui-inline">';
		html+='		<button class="layui-btn layui-btn-xs" name="LAY-btn-del" lay-filter="LAY-btn-del">';
		html+='			<i class="layui-icon layui-icon-delete">删除</i>';
		html+='		</button>';
		html+='	</div>';
		html+='</div>';
	index++;
	var obj = $("#layui-add-btn").parent();
	obj.before(html);
	form.render();
	obj.prev().find("button[name='LAY-btn-del']").click(function(){
		console.log(123);
		  $(this).parent().parent().remove();
	  });
	obj.prev().find("select[name^='remoteTable']").change(function(){
		console.log(456);
		  var remoteTable = $(this).find("option:selected").val();
		  $(this).parent().parent().next().find("select[name^='remoteColumn']").html(common.getSelectOption(common.getSysRefDef(common.Constants.RemoteTableColumn+remoteTable,'1'), ''));
	   });
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
      	  url: config.appBase+'/dataMoveTemplate/'+id,
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
  
  exports('data_move_template/form', {});
});