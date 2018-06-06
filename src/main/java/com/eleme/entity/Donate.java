package com.eleme.entity;

public class Donate {
	
    private int id; //编号
    private String name; //捐款大佬昵称
    private float Money; //捐款金额
    private String time; //捐款日期
    private String source; //捐款渠道
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getMoney() {
		return Money;
	}
	public void setMoney(float money) {
		Money = money;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
    
}
