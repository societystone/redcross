package com.app.bankbean.qaccbal;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "in")
public class BankBeanQaccbalIn {

	@XmlElement(name = "TotalNum")
	private String totalNum = ""; // 总笔数
	@XmlElement(name = "BLFlag")
	private String bLFlag = "";// 是否网银互联账号
	@XmlElement(name = "SynFlag")
	private String synFlag = "";// 是否同步返回标志

	@XmlElement(name = "rd")
	private List<BankBeanQaccbalInRd> rds;
}
