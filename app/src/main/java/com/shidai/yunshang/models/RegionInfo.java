package com.shidai.yunshang.models;

public class RegionInfo {
	
	public int areaId;
	public int parentId;
	public String areaName;

	public RegionInfo(int areaId, int parentId, String areaName) {
		this.areaId = areaId;
		this.areaName = areaName;
		this.parentId = parentId;
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}



	@Override
	public String toString()
	{
		return "RegionInfo [name=" + areaName + "]";
	}


	//这个用来显示在PickerView上面的字符串,PickerView会通过反射获取getPickerViewText方法显示出来。
	public String getPickerViewText() {
		//这里还可以判断文字超长截断再提供显示
		return areaName;
	}
}
