/**

 @Name： 公共业务
 @Author：wangtw
    
 */
 
layui.define(['laytpl', 'layer', 'element', 'util', 'form', 'table', 'config'],function(exports){
  var $ = layui.jquery
  ,config = layui.config;

  var func = {
		  /**
		   * 常量
		   */
		  Constants : {
			  Sex : 'S',//性别
			  Status : 'Z',//状态
			  YNFlag : 'Y',//是否标识
			  User : 'User',//部门
			  Department : 'Department',//部门
			  Role : 'Role'//角色
		  },
		  /**
		   * 加载本地码值存储
		   * refType 码值类型
		   */
		  loadLoaclData : function(refType){
			  var key = 'sys_ref_'+refType;
			  var sysRefData = null;
			  if(refType == this.Constants.User){
				  $.ajax({
				  	  type: "POST",
				  	  contentType: 'application/json',
				  	  url: config.appBase+'/sys/user/noPageList', 
				  	  async : false,
				  	  data: JSON.stringify({}),
				  	  dataType:"json",
				  	  success: function(res){
				  		  if(res.code==0 && res.data!=null && res.data.length>0){
				  			  sysRefData = new Array();
				  			  for(var i=0;i<res.data.length;i++){
				  				  var r = res.data[i];
				  				  sysRefData.push({
				  					  value : r.id,
				  					  title : r.realName,
				  					  status : r.status
				  				  });
				  			  }
					  		  layui.data(config.tableName,{
					  			  key:key, 
					  			  value:sysRefData
					  		  });
				  		  }
				      }
				  });
			  }else if(refType == this.Constants.Department){
				  $.ajax({
				  	  type: "POST",
				  	  contentType: 'application/json',
				  	  url: config.appBase+'/sys/department/noPageList', 
				  	  async : false,
				  	  data: JSON.stringify({}),
				  	  dataType:"json",
				  	  success: function(res){
				  		  if(res.code==0 && res.data!=null && res.data.length>0){
				  			  sysRefData = new Array();
				  			  for(var i=0;i<res.data.length;i++){
				  				  var r = res.data[i];
				  				  sysRefData.push({
				  					  value : r.id,
				  					  title : r.name,
				  					  status : r.status
				  				  });
				  			  }
					  		  layui.data(config.tableName,{
					  			  key:key, 
					  			  value:sysRefData
					  		  });
				  		  }
				      }
				  });
			  }else if(refType == this.Constants.Role){
				  $.ajax({
				  	  method: "POST",
				  	  contentType: 'application/json',
				  	  url: config.appBase+'/sys/role/noPageList', 
				  	  async : false,
				  	  data: JSON.stringify({}),
				  	  dataType:"json",
				  	  success: function(res){
				  		  if(res.code==0 && res.data!=null && res.data.length>0){
				  			  sysRefData = new Array();
				  			  for(var i=0;i<res.data.length;i++){
				  				  var r = res.data[i];
				  				  sysRefData.push({
				  					  value : r.id,
				  					  title : r.name,
				  					  status : r.status
				  				  });
				  			  }
					  		  layui.data(config.tableName,{
					  			  key:key, 
					  			  value:sysRefData
					  		  });
				  		  }
				      }
				  });
			  }else{
				  $.ajax({
				  	  method: "POST",
				  	  contentType: 'application/json',
				  	  url: config.appBase+'/sys/refDef/list', 
				  	  async : false,
				  	  data: JSON.stringify({"refType":refType}),
				  	  dataType:"json",
				  	  success: function(res){
				  		  if(res.code==0 && res.data!=null && res.data.length>0){
				  			  sysRefData = new Array();
				  			  for(var i=0;i<res.data.length;i++){
				  				  var r = res.data[i];
				  				  sysRefData.push({
				  					  value : r.refCode,
				  					  title : r.refDesc,
				  					  status : r.status
				  				  });
				  			  }
					  		  layui.data(config.tableName,{
					  			  key:key, 
					  			  value:sysRefData
					  		  });
				  		  }
				      }
				  });
			  }
			  return sysRefData;
		  },
		  /**
		   * 获取码值
		   * refType 码值类型
		   * status 状态，不传或者null代表所有状态；1启用；0禁用
		   */
		  getSysRefDef : function (refType, status){
			  var data = null;
			  var key = 'sys_ref_'+refType;
			  var sysRefData = layui.data(config.tableName)[key];
			  if(sysRefData == null){
				  sysRefData = this.loadLoaclData(refType);
			  }
			  if(sysRefData != null && sysRefData.length>0){
				  data = new Array();
				  if(!status){
					  data = sysRefData;
				  }else{
					  for(var i=0;i<sysRefData.length;i++){
						  var sysRef = sysRefData[i];
						  if(status == sysRef.status){
							  data.push(sysRef);
						  }
					  }
				  }
			  }
			  return data;
		  },
		  /**
		   * 通过码值类型及码值code获取码值描述
		   */
		  getRefDesc : function(refType, refCode){
			  if(!refType || !refCode){
				  return "";
			  }
			  var refDesc = "";
			  var key = 'sys_ref_'+refType;
			  var sysRefData = layui.data(config.tableName)[key];
			  if(sysRefData == null){
				  sysRefData = this.getSysRefDef(refType);
			  }
			  if(sysRefData != null && sysRefData.length>0){
				  for(var i=0;i<sysRefData.length;i++){
					  var sysRef = sysRefData[i];
					  if(refCode == sysRef.value){
						  refDesc = sysRef.title;
						  break;
					  }
				  }
			  }
			  return refDesc;
		  },
		  /**
		   * 初始化select元素
		   */
		  initSelect : function(obj, data){
			  var html = "<option value=''>不限</option>";
			  if(data!=null && data.length>0){
				  for(var i=0;i<data.length;i++){
					  html += "<option value='"+data[i].value+"'>"+data[i].title+"</option>";
				  }
			  }
			  obj.html(html);
		  },
		  /**
		   * 初始化radio元素
		   */
		  initRadio : function(obj, data, name, checkedValue){
			  var html = "";
			  if(data!=null && data.length>0){
				  for(var i=0;i<data.length;i++){
					  if(data[i].value == checkedValue){
						  html += "<input type='radio' name='"+name+"' value='"+data[i].value+"' title='"+data[i].title+"' checked>";
					  }else{
						  html += "<input type='radio' name='"+name+"' value='"+data[i].value+"' title='"+data[i].title+"'>";
					  }
				  }
			  }
			  obj.html(html);
		  },
		  /**
		   * 初始化checkbox元素
		   */
		  initCheckbox : function(obj, data, name){
			  var html = "";
			  if(data!=null && data.length>0){
				  for(var i=0;i<data.length;i++){
					  html += "<input type='checkbox' name='"+name+"["+data[i].value+"]' value='"+data[i].value+"' title='"+data[i].title+"' lay-skin='primary'>";
				  }
			  }
			  obj.html(html);
		  },
		  /**
		   * 禁用/启用按钮
		   */
		  disabledButton : function(obj,bool){
			  if(bool){
				  obj.attr("disabled",true);
				  obj.addClass("layui-btn-disabled");
			  }else{
				  obj.removeAttr("disabled");
				  obj.removeClass("layui-btn-disabled");
			  }
		  }
  };
  
  //对外暴露的接口
  exports('common', func);
});