package com.app.bankbean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.app.bankbean.pub.BankBeanPubRequest;
import com.app.bankbean.qaccbal.BankBeanQaccbalIn;
import com.app.bankbean.qbill.BankBeanQbillIn;
import com.app.bankbean.qhisd.BankBeanQhisdIn;
import com.app.bankbean.qpd.BankBeanQpdIn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "eb")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({ BankBeanQpdIn.class, BankBeanQaccbalIn.class, BankBeanQbillIn.class, BankBeanQhisdIn.class })
public class BankBeanRequest<T> {

	private BankBeanPubRequest pub;

	@XmlAnyElement(lax = true)
	private T in;
}
