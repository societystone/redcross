/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.app.util;

import com.app.exception.CheckException;
/**
 * 关于异常的工具类.
 * 
 */
public class ExceptionUtil {

	/**
	 * 异常抛出
	 * @param message
	 */
	public static void throwCheckException(String message) {
		throw new CheckException(message);
    }
	public static void throwEmptyCheckException(Object value, String message) {
		if(Emptys.isEmpty(value)) {
			throwCheckException(message);
		}
    }
}
