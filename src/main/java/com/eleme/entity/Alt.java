package com.eleme.entity;

public class Alt {
	  
    private int id; //编号
    private String avatar; //cookie_avatar
    private String elemeKey; //cookie_elemeKey
    private int useNum; //今日使用次数
    private String phoneNum; //当前电话号码
    
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getElemeKey() {
		return elemeKey;
	}
	public void setElemeKey(String elemeKey) {
		this.elemeKey = elemeKey;
	}
	public int getUseNumber() {
		return useNum;
	}
	public void setUseNumber(int useNumber) {
		this.useNum = useNumber;
	}
	public String getPhoneNumber() {
		return phoneNum;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNum = phoneNumber;
	}
}