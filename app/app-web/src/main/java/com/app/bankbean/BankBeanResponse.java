package com.app.bankbean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.app.bankbean.pub.BankBeanPubResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlTransient
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class BankBeanResponse<T> {

	private BankBeanPubResponse pub;

	@XmlAnyElement(lax = true)
	private T out;
}
