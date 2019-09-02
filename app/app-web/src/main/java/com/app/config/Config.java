package com.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = { "classpath:report.properties" }, ignoreResourceNotFound = true, encoding = "UTF-8")
public class Config {

	public static int batchTotalCount;// 批量提交笔数

	public static boolean isEnableSystemTranDate;// 系统交易日期开关

	public static String systemTranDate;// 系统交易日期

	public static String databaseName;

	public static String reportExportBalanceTitle;// 账户余额信息列标题

	public static String reportExportBalanceField;// 账户余额信息列字段

	public static String reportExportBalanceTotalField;// 账户余额信息合计列字段

	public static String reportExportInOutTitle;// 账户收支信息列标题

	public static String reportExportInOutField;// 账户收支信息列字段

	public static String reportExportInOutTotalField;// 账户收支信息合计列字段

	public static String reportExportTranTitle;// 账户流水信息列标题

	public static String reportExportTranField;// 账户流水信息列字段

	public static String reportExportTranTotalField;// 账户流水信息合计列字段

	@Value("${batch.total.count}")
	public void setBatchTotalCount(int batchTotalCount) {
		Config.batchTotalCount = batchTotalCount;
	}

	@Value("${is.enable.system.tran.date}")
	public void setEnableSystemTranDate(boolean isEnableSystemTranDate) {
		Config.isEnableSystemTranDate = isEnableSystemTranDate;
	}

	@Value("${system.tran.date}")
	public void setSystemTranDate(String systemTranDate) {
		Config.systemTranDate = systemTranDate;
	}

	@Value("${remote.databaseName}")
	public void setDatabaseName(String databaseName) {
		Config.databaseName = databaseName;
	}

	@Value("${report.export.balance.title}")
	public void setReportExportBalanceTitle(String reportExportBalanceTitle) {
		Config.reportExportBalanceTitle = reportExportBalanceTitle;
	}

	@Value("${report.export.balance.field}")
	public void setReportExportBalanceField(String reportExportBalanceField) {
		Config.reportExportBalanceField = reportExportBalanceField;
	}

	@Value("${report.export.balance.totalField}")
	public void setReportExportBalanceTotalField(String reportExportBalanceTotalField) {
		Config.reportExportBalanceTotalField = reportExportBalanceTotalField;
	}

	@Value("${report.export.inout.title}")
	public void setReportExportInOutTitle(String reportExportInOutTitle) {
		Config.reportExportInOutTitle = reportExportInOutTitle;
	}

	@Value("${report.export.inout.field}")
	public void setReportExportInOutField(String reportExportInOutField) {
		Config.reportExportInOutField = reportExportInOutField;
	}

	@Value("${report.export.inout.totalField}")
	public void setReportExportInOutTotalField(String reportExportInOutTotalField) {
		Config.reportExportInOutTotalField = reportExportInOutTotalField;
	}

	@Value("${report.export.tran.title}")
	public void setReportExportTranTitle(String reportExportTranTitle) {
		Config.reportExportTranTitle = reportExportTranTitle;
	}

	@Value("${report.export.tran.field}")
	public void setReportExportTranField(String reportExportTranField) {
		Config.reportExportTranField = reportExportTranField;
	}

	@Value("${report.export.tran.totalField}")
	public void setReportExportTranTotalField(String reportExportTranTotalField) {
		Config.reportExportTranTotalField = reportExportTranTotalField;
	}

}
