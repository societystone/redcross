package com.app.bankbean.qaccbal;

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
@XmlSeeAlso({ BankBeanQaccbalOut.class })
public class BankBeanResponseQaccbal extends BankBeanResponse<BankBeanQaccbalOut> {

}
