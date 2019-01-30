package com.app.bankbean.qaccbal;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "out")
public class BankBeanQaccbalOut {

	@XmlElement(name = "rd")
	private List<BankBeanQaccbalOutRd> rds;

}
