package com.app.util;

import java.util.Collection;
import java.util.Map;

public class Emptys {

	/**
	 * 判断是否为空.
	 */
	public static boolean isEmpty(Object obj) {
		if (obj instanceof CharSequence) {
			return ((obj == null) || (((CharSequence) obj).length() == 0));
		}
		return obj == null;
	}

	/**
	 * 判断是否不为空.
	 */
	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}

	/**
	 * 判断是否为空.
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return (collection == null) || collection.isEmpty();
	}

	/**
	 * 判断是否不为空.
	 */
	public static boolean isNotEmpty(Collection<?> collection) {
		return !isEmpty(collection);
	}

	/**
	 * 判断是否为空.
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return (map == null) || map.isEmpty();
	}

	/**
	 * 判断是否不为空.
	 */
	public static boolean isNotEmpty(Map<?, ?> map) {
		return !isEmpty(map);
	}

	/**
	 * 判断是否为空.
	 */
	public static boolean isEmpty(Object[] array) {
		return (array == null) || array.length <= 0;
	}

	/**
	 * 判断是否不为空.
	 */
	public static boolean isNotEmpty(Object[] array) {
		return !isEmpty(array);
	}

}
