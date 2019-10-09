/**
 * wangtw
 * 
 * @param exports
 * @returns
 */
layui.define(['common'], function(exports){
  var $ = layui.jquery;
  var config = layui.config;
  var form = layui.form;
  var common = layui.common;
  var formObj = $(".layui-form");
  common.initSelect(formObj.find("select[name='acctNo']"),common.getSysRefDef(common.Constants.AcctNo,'1'),null);
  form.render(); //更新
  
  exports('acct_data_download/form', {});
});