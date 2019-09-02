package com.app.bankbean.qpd;

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
@XmlSeeAlso({ BankBeanQpdOut.class })
public class BankBeanResponseQpd extends BankBeanResponse<BankBeanQpdOut> {

}
