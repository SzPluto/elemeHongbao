package com.eleme.service;

public interface UserService {
	
	/**
	 *  查找今日使用次数
	 * 
	 * @return
	 */
	public boolean checkTodayUseNumber(String phone);
	
	/**
	 *  增加使用次数以及金额
	 * 
	 * @return
	 */
	public void addUseNumber(String phone,String money);
}
