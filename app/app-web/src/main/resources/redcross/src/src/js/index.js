/**
 * 主页
 * @param exports
 * @returns
 */
 
layui.define(['common'], function(exports){
  var $ = layui.jquery
  ,config = layui.config
  ,common = layui.common
  ,layer = layui.layer
  ,laytpl = layui.laytpl
  ,element = layui.element
  ,form = layui.form;
  //打开页签
  var openTabsPage = function(id, text, url){
	  var tabPage = $("#index-top-tab").find(".layui-tab-title").find("li[lay-id='"+id+"']");
	  if(!tabPage || tabPage.length==0){
		  element.tabAdd('index-top-tab', {title:text, content:'<iframe src="'+url+'"></iframe>', id:id});
	  }
	  element.tabChange('index-top-tab', id);
  };
  var menuHtmlFunc = function(menus,listHtml,defaultOpen){
	  var html = "";
	  if(menus==null || menus==""){
		  return html;
	  }
	  if(menus!=null && menus.length>0){
		  for(var i=0;i<menus.length;i++){
			  var menu = menus[i];
			  var childs = menu.childs;
			  if(childs!=null && childs.length>0){ 
				  if(defaultOpen && i==0){
					  html += '<'+listHtml+' class="layui-nav-item layui-nav-itemed">';
				  }else{
					  html += '<'+listHtml+' class="layui-nav-item">';
					  defaultOpen = false;
				  }
				  html += '<a href="javascript:;"><i class="layui-icon '+menu.icon+'"></i>' + menu.name + '</a>';
				  html += '<dl class="layui-nav-child">';
				  html += menuHtmlFunc(childs,"dd",defaultOpen);
				  html += '</dl>';
			  }else{
				  if(defaultOpen && i==0){
					  html += '<'+listHtml+' class="layui-nav-item layui-this">';
					  openTabsPage(menu.id,menu.name,menu.url);
				  }else{
					  html += '<'+listHtml+' class="layui-nav-item">';
				  }
				  html += '<a href="javascript:;" onclick="layui.index.openTabsPage(\''+menu.id+'\',\''+menu.name+'\',\''+menu.url+'\');"><i class="layui-icon '+menu.icon+'"></i>' + menu.name + '</a>';
			  }
			  html += '</'+listHtml+'>';
		  }
	  }
	  return html;
  }
  
  //加载菜单
  var loadMenu = function(){
	  $.getJSON(config.appBase+'/sys/menu',
			  function(result){
	    		  if(result.code==0 && result.data!=null && result.data.length>0){
	    			  var menuHtml = menuHtmlFunc(result.data,"li",true);
	    			  $("#nav-menu").append(menuHtml);
	    			  element.render("nav");
	    		  }
	    	  }
	  );
  }
  loadMenu();
  
  //加载登录用户
  var loadLoginUser = function(){
	  var loginUser = layui.data(config.tableName)[config.loginUser];
	  if(loginUser){
		  var realName = loginUser.realName;
		  var sex = loginUser.sex;
		  $("#layui-userinfo").find("span").html(realName);
		  var imageSrc = "../src/images/avatar1.png";
		  if(sex=='F'){
			  imageSrc = "../src/images/avatar2.png"; 
		  }
		  $("#layui-userinfo").find("img").attr("src",imageSrc);
	  }
  }
  loadLoginUser();
  
  //事件
  var active = {
		  refresh:function(){
			  var obj = $("#index-top-tab").find(".layui-show").find("iframe");
			  obj.attr("src",obj.attr("src"));
		  },
		  modifyPwd : function(){
			  var loginUser = layui.data(config.tableName)[config.loginUser];
			  if(loginUser){
				  layer.open({
			          type: 2,
			          title: '修改密码',
			          content: 'user/modifyPwdForm.html',
			          maxmin: true,
			          area: ['500px', '450px'],
			          success: function(layero, index){
			        	  var iframeContent = layero.find('iframe').contents();
			        	  iframeContent.find("input[name='id']").val(loginUser.id);
			          },
			          btn: ['确定', '取消'],
			          yes: function(index, layero){
			            var iframeWindow = window['layui-layer-iframe'+ index];
			            var submitID = 'layui-submit-btn';
			            var submit = layero.find('iframe').contents().find('#'+ submitID);
			
			            //监听提交
			            iframeWindow.layui.form.on('submit('+ submitID +')', function(data){
			            	common.disabledButton(submit,true);
			            	var field = data.field; //获取提交的字段
			              $.ajax({
			              	  type: "PUT",
			              	  contentType: 'application/json',
			              	  url: config.appBase+'/sys/user/modifyPwd', 
			              	  data: JSON.stringify(field),
			              	  dataType:'json',
			              	  success: function(res){
			              		  if(res.code==0){
			              			  layer.msg("修改用户密码成功！");
			    		              layer.close(index); //关闭弹层
			              		  }else{
			              			  layer.msg(res.msg);
			              			  common.disabledButton(submit,false);
			              		  }
			                  }
			                });
			            });  
			            submit.trigger('click');
			          }
			       }); 
			  }
		  },
		  logoutUser : function(){
			  layer.confirm('确定退出登陆吗？', function(index) {
	    		  layui.data(config.tableName,null);
	    		  $.post(config.appBase+"/sys/logout");
	    		  location.href = 'login.html'; //返回登录页
	          });
		  }  
  };

  $("#index-layui-header").find("a").click(function(){
	  var event = $(this).attr("lay-event");
	  if(!event){
		  return;
	  }
	  active[event] ? active[event].call(this) : '';
  });
  

  //对外暴露的接口
  exports('index', {openTabsPage:openTabsPage});
});