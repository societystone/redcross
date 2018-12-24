package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.aspect.annotation.Log;
import com.app.bean.PageResultBean;
import com.app.bean.ResultBean;
import com.app.entity.DataMoveTemplate;
import com.app.service.DataMoveTemplateService;

/**
 * 数据迁移模板controller . <br>
 * 
 * @author wangtw <br>
 */
@RestController
public class DataMoveTemplateController {

	/**
	 * 注入接口
	 */
	@Autowired
	private DataMoveTemplateService dataMoveTemplateService;

	/**
	 * 通过id获得模板
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/dataMoveTemplate/{id}")
	public ResultBean<DataMoveTemplate> selectDataMoveTemplateById(@PathVariable("id") Long id) {
		return new ResultBean<DataMoveTemplate>(dataMoveTemplateService.selectTemplateDetail(id));
	}

	/**
	 * 分页查询模板
	 * 
	 * @param dataMoveTemplate
	 * @return
	 */
	@PostMapping("/dataMoveTemplate/list")
	public ResultBean<PageResultBean<DataMoveTemplate>> selectDataMoveTemplateByPage(
			@RequestBody DataMoveTemplate dataMoveTemplate) {
		return new ResultBean<PageResultBean<DataMoveTemplate>>(
				dataMoveTemplateService.selectTemplateListByPage(dataMoveTemplate));
	}

	/**
	 * 不分页查询模板
	 * 
	 * @param dataMoveTemplate
	 * @return
	 */
	@PostMapping("/dataMoveTemplate/noPageList")
	public ResultBean<List<DataMoveTemplate>> selectDataMoveTemplateList(
			@RequestBody DataMoveTemplate dataMoveTemplate) {
		return new ResultBean<List<DataMoveTemplate>>(dataMoveTemplateService.selectTemplateList(dataMoveTemplate));
	}

	/**
	 * 添加模板
	 * 
	 * @param dataMoveTemplate
	 * @return
	 */
	@Log("添加数据迁移模板")
	@PostMapping("/dataMoveTemplate")
	public ResultBean<Long> addDataMoveTemplate(@RequestBody DataMoveTemplate dataMoveTemplate) {
		return new ResultBean<Long>(dataMoveTemplateService.insertTemplateInfo(dataMoveTemplate));
	}

	/**
	 * 修改模板
	 * 
	 * @param dataMoveTemplate
	 */
	@Log("修改数据迁移模板")
	@PutMapping("/dataMoveTemplate")
	public ResultBean<Boolean> modifyDataMoveTemplate(@RequestBody DataMoveTemplate dataMoveTemplate) {
		dataMoveTemplateService.updateTemplateInfo(dataMoveTemplate);
		return new ResultBean<Boolean>(true);
	}

	/**
	 * 启用模板
	 * 
	 * @param id
	 */
	@Log("启用数据迁移模板")
	@PostMapping("/dataMoveTemplate/startUse/{id}")
	public ResultBean<Boolean> startUseDataMoveTemplate(@PathVariable("id") Long id) {
		dataMoveTemplateService.startUseTemplateInfo(id);
		return new ResultBean<Boolean>(true);
	}

	/**
	 * 停用模板
	 * 
	 * @param id
	 */
	@Log("停用数据迁移模板")
	@PostMapping("/dataMoveTemplate/stopUse/{id}")
	public ResultBean<Boolean> stopUseDataMoveTemplate(@PathVariable("id") Long id) {
		dataMoveTemplateService.stopUseTemplateInfo(id);
		return new ResultBean<Boolean>(true);
	}

	/**
	 * 删除模板
	 * 
	 * @param id
	 */
	@Log("删除数据迁移模板")
	@DeleteMapping("/dataMoveTemplate/{id}")
	public ResultBean<Boolean> deleteDataMoveTemplate(@PathVariable("id") Long id) {
		dataMoveTemplateService.delteTemplateInfo(id);
		return new ResultBean<Boolean>(true);
	}

}
