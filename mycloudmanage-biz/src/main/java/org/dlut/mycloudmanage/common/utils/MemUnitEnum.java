package org.dlut.mycloudmanage.common.utils;

public enum MemUnitEnum {

	KB(1024, "KB"), MB(1024 * 1024, "MB"), GB(1024 * 1024 * 1024, "GB");

	private long size;
	private String desc;

	public static MemUnitEnum getMemUnitByDesc(String desc) {
		for (MemUnitEnum memUnitEnum : MemUnitEnum.values()) {
			if (memUnitEnum.getDesc().equals(desc)) {
				return memUnitEnum;
			}
		}
		return null;
	}

	private MemUnitEnum(long size, String desc) {
		this.size = size;
		this.desc = desc;
	}

	public long getSize() {
		return size;
	}

	public String getDesc() {
		return desc;
	}

}
