package com.eleme.service;

public interface UserService {
	
	/**
	 *  查询手机号是否欠费
	 * 
	 * @return
	 */
	public boolean checkBalance(String phone);
	
	/**
	 *  增加使用次数以及金额
	 * 
	 * @return
	 */
	public void addUseNumber(String phone,String money);
	
}
