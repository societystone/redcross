package com.app.bankbean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.app.bankbean.pub.BankBeanPubResponse;
import com.app.bankbean.qaccbal.BankBeanQaccbalOut;
import com.app.bankbean.qbill.BankBeanQbillOut;
import com.app.bankbean.qpd.BankBeanQpdOut;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "eb")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({ BankBeanQpdOut.class, BankBeanQaccbalOut.class, BankBeanQbillOut.class })
public class BankBeanResponse<T> {

	private BankBeanPubResponse pub;

	@XmlAnyElement(lax = true)
	private T out;
}
