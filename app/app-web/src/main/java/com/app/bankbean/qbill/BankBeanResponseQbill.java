package com.app.bankbean.qbill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.app.bankbean.BankBeanResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement(name = "eb")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({ BankBeanQbillOut.class })
public class BankBeanResponseQbill extends BankBeanResponse<BankBeanQbillOut> {

}
