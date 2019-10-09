package com.app.controller;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.aspect.annotation.Log;
import com.app.config.Config;
import com.app.dto.AcctBalanceDTO;
import com.app.dto.AcctInOutDTO;
import com.app.entity.AcctTranHist;
import com.app.service.AcctDataService;
import com.app.service.SysRefDefService;
import com.app.util.DateUtils;
import com.app.util.DigitUtils;
import com.app.util.Emptys;
import com.app.util.ExcelUtil;
import com.app.util.ExceptionUtil;

@RestController
public class ReportController {
	/**
	 * 日志对象
	 */
	private static final Logger log = LoggerFactory.getLogger(ReportController.class);

	@Autowired
	private AcctDataService acctDataService;
	@Autowired
	private SysRefDefService sysRefDefService;

	/**
	 * 导出账户余额信息
	 * 
	 * @return
	 */
	@Log("导出账户余额数据")
	@RequestMapping("export/acct_balance")
	public void exportAcctBalanceList(HttpServletResponse response) {
		log.info("导出账户余额信息开始");
		// excel文件名
		String fileName = "账户余额信息_" + DateUtils.formatYYYYMMDDHHmmss(new Date()) + ExcelUtil.excel2007U;
		// sheet名
		String sheetName = "账户余额信息表";
		// excel标题
		String[] title = Config.reportExportBalanceTitle.split(",");
		// excel字段
		List<String> fields = Arrays.asList(Config.reportExportBalanceField.split(","));
		// excel合计字段
		List<String> totalFields = Arrays.asList(Config.reportExportBalanceTotalField.split(","));
		// 合计字段统计结果存储变量
		Map<String, String> totalFieldValue = new HashMap<>();
		// 数据内容
		String[][] content;
		// 查询数据
		List<AcctBalanceDTO> abDtos = acctDataService.queryAcctBalanceList();
		if (Emptys.isNotEmpty(abDtos)) {
			content = new String[abDtos.size() + 2][];
			for (int i = 0; i < abDtos.size(); i++) {
				content[i] = new String[fields.size()];
				AcctBalanceDTO abDto = abDtos.get(i);
				for (int j = 0; j < fields.size(); j++) {
					String value = "";
					try {
						String field = fields.get(j);
						Field f = abDto.getClass().getDeclaredField(field);// 根据变量名获得字段
						f.setAccessible(true);// 设置字段可访问，即暴力反射
						value = (String) f.get(abDto);// 在那个对象上获取此字段的值
						if ("status".equals(field)) {
							String desc = sysRefDefService.getSysRefDefDesc("S", value);
							value = Emptys.isNotEmpty(desc) ? desc : value;
						}
						if (totalFields.contains(field)) {
							totalFieldValue.put(field,
									(totalFieldValue.containsKey(field)
											? DigitUtils.add(totalFieldValue.get(field), value)
											: value));
						}
					} catch (Exception e) {
						log.error("导出数据读取对象属性异常：", e);
					}
					content[i][j] = Emptys.isNotEmpty(value) ? value : "";
				}
			}
			if (Emptys.isNotEmpty(totalFields)) {
				int i = content.length - 1;
				content[i] = new String[fields.size()];
				content[i][0] = "合计：";
				for (String totalField : totalFields) {
					int index = fields.indexOf(totalField);
					String value = totalFieldValue.get(totalField);
					content[i][index] = Emptys.isNotEmpty(value) ? value : "";
				}
			}
		} else {
			content = new String[0][];
		}
		// 创建HSSFWorkbook
		HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);
		sendResponse(wb, response, fileName);
		log.info("导出账户余额信息结束");
	}

	/**
	 * 导出账户收支
	 * 
	 * @param acct
	 * @return
	 */
	@Log("导出账户收支数据")
	@RequestMapping("export/acct_in_out_hist")
	public void exportAcctInOutList(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> queryMap = new HashMap<>();
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String name = (String) parameterNames.nextElement();
			String value = request.getParameter(name);
			queryMap.put(name, value);
		}
		String beginDate = (String) queryMap.get("beginDate");
		ExceptionUtil.throwEmptyCheckException(beginDate, "开始时间不能为空");
		String endDate = (String) queryMap.get("endDate");
		ExceptionUtil.throwEmptyCheckException(endDate, "结束时间不能为空");
		log.info("导出账户收支信息开始：{}", queryMap);
		// excel文件名
		String fileName = "账户收支信息_" + DateUtils.formatYYYYMMDDHHmmss(new Date()) + ExcelUtil.excel2007U;
		// sheet名
		String sheetName = "账户收支信息表";
		// excel标题
		String[] title = Config.reportExportInOutTitle.split(",");
		// excel字段
		List<String> fields = Arrays.asList(Config.reportExportInOutField.split(","));
		// excel合计字段
		List<String> totalFields = Arrays.asList(Config.reportExportInOutTotalField.split(","));
		// 合计字段统计结果存储变量
		Map<String, String> totalFieldValue = new HashMap<>();
		// 数据内容
		String[][] content;
		// 查询数据
		List<AcctInOutDTO> aioDtos = acctDataService.queryAcctInOutList(queryMap);
		if (Emptys.isNotEmpty(aioDtos)) {
			content = new String[aioDtos.size() + 2][];
			for (int i = 0; i < aioDtos.size(); i++) {
				content[i] = new String[fields.size()];
				AcctInOutDTO aioDto = aioDtos.get(i);
				for (int j = 0; j < fields.size(); j++) {
					String value = "";
					try {
						String field = fields.get(j);
						Field f = aioDto.getClass().getDeclaredField(field);// 根据变量名获得字段
						f.setAccessible(true);// 设置字段可访问，即暴力反射
						value = (String) f.get(aioDto);// 在那个对象上获取此字段的值
						if (totalFields.contains(field)) {
							totalFieldValue.put(field,
									(totalFieldValue.containsKey(field)
											? DigitUtils.add(totalFieldValue.get(field), value)
											: value));
						}
					} catch (Exception e) {
						log.error("导出数据读取对象属性异常：", e);
					}
					content[i][j] = Emptys.isNotEmpty(value) ? value : "";
				}
			}
			if (Emptys.isNotEmpty(totalFields)) {
				int i = content.length - 1;
				content[i] = new String[fields.size()];
				content[i][0] = "合计：";
				for (String totalField : totalFields) {
					int index = fields.indexOf(totalField);
					String value = totalFieldValue.get(totalField);
					content[i][index] = Emptys.isNotEmpty(value) ? value : "";
				}
			}
		} else {
			content = new String[0][];
		}
		// 创建HSSFWorkbook
		HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);
		sendResponse(wb, response, fileName);
		log.info("导出账户收支信息结束");
	}

	/**
	 * 导出账户流水
	 * 
	 * @param acct
	 * @return
	 */
	@Log("导出账户交易流水数据")
	@RequestMapping("export/acct_tran_hist")
	public void exportAcctTranHistList(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> queryMap = new HashMap<>();
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String name = (String) parameterNames.nextElement();
			String value = request.getParameter(name);
			queryMap.put(name, value);
		}
		log.info("导出账户流水信息开始：{}", queryMap);
		// excel文件名
		String fileName = "账户流水信息_" + DateUtils.formatYYYYMMDDHHmmss(new Date()) + ExcelUtil.excel2007U;
		// sheet名
		String sheetName = "账户流水信息表";
		// excel标题
		String[] title = Config.reportExportTranTitle.split(",");
		// excel字段
		List<String> fields = Arrays.asList(Config.reportExportTranField.split(","));
		// excel合计字段
		List<String> totalFields = Arrays.asList(Config.reportExportTranTotalField.split(","));
		// 合计字段统计结果存储变量
		Map<String, String> totalFieldValue = new HashMap<>();
		// 数据内容
		String[][] content;
		// 查询数据
		List<AcctTranHist> aths = acctDataService.queryAcctTranHistList(queryMap);
		if (Emptys.isNotEmpty(aths)) {
			content = new String[aths.size() + 2][];
			for (int i = 0; i < aths.size(); i++) {
				content[i] = new String[fields.size()];
				AcctTranHist ath = aths.get(i);
				for (int j = 0; j < fields.size(); j++) {
					String value = "";
					try {
						String field = fields.get(j);
						Field f = ath.getClass().getDeclaredField(field);// 根据变量名获得字段
						f.setAccessible(true);// 设置字段可访问，即暴力反射
						value = (String) f.get(ath);// 在那个对象上获取此字段的值
						if (totalFields.contains(field)) {
							totalFieldValue.put(field,
									(totalFieldValue.containsKey(field)
											? DigitUtils.add(totalFieldValue.get(field), value)
											: value));
						}
					} catch (Exception e) {
						log.error("导出数据读取对象属性异常：", e);
					}
					content[i][j] = Emptys.isNotEmpty(value) ? value : "";
				}
			}
			if (Emptys.isNotEmpty(totalFields)) {
				int i = content.length - 1;
				content[i] = new String[fields.size()];
				content[i][0] = "合计：";
				for (String totalField : totalFields) {
					int index = fields.indexOf(totalField);
					String value = totalFieldValue.get(totalField);
					content[i][index] = Emptys.isNotEmpty(value) ? value : "";
				}
			}
		} else {
			content = new String[0][];
		}
		// 创建HSSFWorkbook
		HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);
		sendResponse(wb, response, fileName);
		log.info("导出账户流水信息结束");
	}

	// 发送响应流方法
	private void sendResponse(HSSFWorkbook wb, HttpServletResponse response, String fileName) {
		// 响应到客户端
		try {
			response.setContentType("application/octet-stream;charset=ISO8859-1");
			response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
			response.addHeader("Pargam", "no-cache");
			response.addHeader("Cache-Control", "no-cache");
			OutputStream os = response.getOutputStream();
			wb.write(os);
			os.flush();
			os.close();
		} catch (Exception e) {
			log.warn("导出数据异常：", e);
		}
	}
}
