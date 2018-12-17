package com.app.bankbean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name="eb")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(BankBean01In.class)
public class BankBeanRequest<T> {

	private BankBeanPubRequest pub;
	
	@XmlAnyElement(lax = true)
	private T in;
}
