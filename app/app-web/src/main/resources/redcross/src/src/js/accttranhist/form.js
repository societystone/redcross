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
			url : config.appBase + '/accttranhist/select/' + id,
			dataType : 'json',
			success : function(res) {
				if (res.code == 0) {
					if (res.data != null) {
						var data = res.data;
						var account = data.account;
						data["acctNo"] = account.acctNo;
						data["acctName"] = account.acctName;
						form.val("layui-form", data);
					}
				} else {
					layer.msg(res.msg);
				}
			}
		});
	}

	exports('accttranhist/form', {});
});