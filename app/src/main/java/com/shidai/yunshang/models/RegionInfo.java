package com.shidai.yunshang.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class RegionInfo {
	public int areaId;//子的id
	public int parentId;//父类id
	private String full_name;
	private String name;
	private String region_name;
	private String first_letter;

	@Generated(hash = 2139989709)
	public RegionInfo(int areaId, int parentId, String full_name, String name,
			String region_name, String first_letter) {
		this.areaId = areaId;
		this.parentId = parentId;
		this.full_name = full_name;
		this.name = name;
		this.region_name = region_name;
		this.first_letter = first_letter;
	}


	@Generated(hash = 467433030)
	public RegionInfo() {
	}

	@Override
	public String toString()
	{
		return "RegionInfo [name=" + region_name + "]";
	}


	//这个用来显示在PickerView上面的字符串,PickerView会通过反射获取getPickerViewText方法显示出来。
	public String getPickerViewText() {
		//这里还可以判断文字超长截断再提供显示
		return name;
	}


	public String getFirst_letter() {
		return this.first_letter;
	}


	public void setFirst_letter(String first_letter) {
		this.first_letter = first_letter;
	}


	public String getRegion_name() {
		return this.region_name;
	}


	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}


	public String getName() {
		return this.name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getFull_name() {
		return this.full_name;
	}


	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}


	public int getParentId() {
		return this.parentId;
	}


	public void setParentId(int parentId) {
		this.parentId = parentId;
	}


	public int getAreaId() {
		return this.areaId;
	}


	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
}
