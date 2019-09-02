/**
 * wangtw
 * 
 * @param exports
 * @returns
 */
layui.define([ 'common' ], function(exports) {
	var $ = layui.jquery;
	var config = layui.config;
	var form = layui.form;
	var common = layui.common;
	var formObj = $(".layui-form");
	var id = formObj.find("input[name='id']").val();
	if (id) {
		$.ajax({
			type : "GET",
			contentType : 'application/json',
			url : config.appBase + '/acct_tran_hist/' + id,
			dataType : 'json',
			success : function(res) {
				if (res.code == 0) {
					if (res.data != null) {
						var data = res.data;
//						form.val("layui-form", data);
						for(var k in data){
							formObj.find("span[name='"+k+"']").html(data[k]);
						}
					}
				} else {
					layer.msg(res.msg);
				}
			}
		});
	}

	exports('acct_tran_hist/detail', {});
});