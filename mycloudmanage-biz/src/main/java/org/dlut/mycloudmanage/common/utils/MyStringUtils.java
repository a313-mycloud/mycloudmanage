/*
 * Copyright 2014 etao.com All right reserved. This software is the
 * confidential and proprietary information of etao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with etao.com .
 */
package org.dlut.mycloudmanage.common.utils;

/**
 * 类MyStringUtils.java的实现描述：TODO 类实现描述
 * 
 * @author xuyizhen Dec 16, 2014 8:29:09 PM
 */
public class MyStringUtils {
	/**
	 * 判断是否可以转换为integer
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 判断是否可以转化为Long
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isLong(String str) {
		try {
			Long.parseLong(str);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;

		}
	}

}
