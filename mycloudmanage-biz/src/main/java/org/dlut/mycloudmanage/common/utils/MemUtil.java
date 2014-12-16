/*
 * Copyright 2014 etao.com All right reserved. This software is the
 * confidential and proprietary information of etao.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with etao.com .
 */
package org.dlut.mycloudmanage.common.utils;

/**
 * 类MemUtil.java的实现描述：TODO 类实现描述
 * 
 * @author xuyizhen Dec 12, 2014 11:00:32 PM
 */
public class MemUtil {
	public static String getMem(long memory, MemUnitEnum memUnitEnum) {
		return memory / memUnitEnum.getSize() + "";
	}

	public static long getMem(int memory, MemUnitEnum memUnitEnum) {
		return memory * memUnitEnum.getSize();
	}
}
