package com.eleme.dao;

import org.apache.ibatis.annotations.Param;

public interface UserDao {
	
	/**
	 * 检测数据库中是否有此用户
	 * @return
	 */
	public String checkUser(@Param("phone") String phone);

	/**
	 * 查找今日领取次数
	 * @return
	 */
	public int getTodayUseNumber(@Param("phone") String phone);
	
	/**
	 * 查找设定领取次数
	 * @return
	 */
	public int getSetUseNumber(@Param("phone") String phone);
	
	
	/**
	 * 新建一条用户记录
	 */
	public void insertUser(@Param("phone") String phone);
	
	
	/*
	 * 增加使用次数以及金额
	 */
	public void addUserNum(@Param("phone") String phone,@Param("money") String money);
	
}
