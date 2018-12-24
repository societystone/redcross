package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.aspect.annotation.Log;
import com.app.bean.PageResultBean;
import com.app.bean.ResultBean;
import com.app.entity.VoucherTemplate;
import com.app.service.VoucherTemplateService;

/**
 * 凭证模板controller . <br>
 * 
 * @author wangtw <br>
 */
@RestController
public class VoucherTemplateController {

	/**
	 * 注入接口
	 */
	@Autowired
	private VoucherTemplateService voucherTemplateService;

	/**
	 * 通过id获得模板
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/voucherTemplate/{id}")
	public ResultBean<VoucherTemplate> selectVoucherTemplateById(@PathVariable("id") Long id) {
		return new ResultBean<VoucherTemplate>(voucherTemplateService.selectTemplateDetail(id));
	}

	/**
	 * 分页查询模板
	 * 
	 * @param voucherTemplate
	 * @return
	 */
	@PostMapping("/voucherTemplate/list")
	public ResultBean<PageResultBean<VoucherTemplate>> selectVoucherTemplateByPage(
			@RequestBody VoucherTemplate voucherTemplate) {
		return new ResultBean<PageResultBean<VoucherTemplate>>(
				voucherTemplateService.selectTemplateListByPage(voucherTemplate));
	}

	/**
	 * 不分页查询模板
	 * 
	 * @param voucherTemplate
	 * @return
	 */
	@PostMapping("/voucherTemplate/noPageList")
	public ResultBean<List<VoucherTemplate>> selectVoucherTemplateList(@RequestBody VoucherTemplate voucherTemplate) {
		return new ResultBean<List<VoucherTemplate>>(voucherTemplateService.selectTemplateList(voucherTemplate));
	}

	/**
	 * 添加模板
	 * 
	 * @param voucherTemplate
	 * @return
	 */
	@Log("添加凭证模板")
	@PostMapping("/voucherTemplate/add")
	public ResultBean<Long> addVoucherTemplate(@RequestBody VoucherTemplate voucherTemplate) {
		return new ResultBean<Long>(voucherTemplateService.insertTemplateInfo(voucherTemplate));
	}

	/**
	 * 修改模板
	 * 
	 * @param voucherTemplate
	 */
	@Log("修改凭证模板")
	@PostMapping("/voucherTemplate/modify")
	public ResultBean<Boolean> modifyVoucherTemplate(@RequestBody VoucherTemplate voucherTemplate) {
		voucherTemplateService.updateTemplateInfo(voucherTemplate);
		return new ResultBean<Boolean>(true);
	}

	/**
	 * 启用模板
	 * 
	 * @param id
	 */
	@Log("启用凭证模板")
	@PostMapping("/voucherTemplate/startUse/{id}")
	public ResultBean<Boolean> startUseVoucherTemplate(@PathVariable("id") Long id) {
		voucherTemplateService.startUseTemplateInfo(id);
		return new ResultBean<Boolean>(true);
	}

	/**
	 * 停用模板
	 * 
	 * @param id
	 */
	@Log("停用凭证模板")
	@PostMapping("/voucherTemplate/stopUse/{id}")
	public ResultBean<Boolean> stopUseVoucherTemplate(@PathVariable("id") Long id) {
		voucherTemplateService.stopUseTemplateInfo(id);
		return new ResultBean<Boolean>(true);
	}

	/**
	 * 删除模板
	 * 
	 * @param id
	 */
	@Log("删除凭证模板")
	@DeleteMapping("/voucherTemplate/{id}")
	public ResultBean<Boolean> deleteVoucherTemplate(@PathVariable("id") Long id) {
		voucherTemplateService.delteTemplateInfo(id);
		return new ResultBean<Boolean>(true);
	}

}
