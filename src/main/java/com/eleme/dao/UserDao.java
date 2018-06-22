package com.eleme.dao;

import org.apache.ibatis.annotations.Param;

public interface UserDao {
	
	/**
	 * 检测数据库中是否有此用户
	 * @return
	 */
	public String checkUser(@Param("phone") String phone);
	
	/**
	 * 获取余额
	 * @return
	 */
	public int getBalance(@Param("phone") String phone);
	
	/**
	 * 增加余额
	 * @return
	 */
	public void addBalance(@Param("phone") String phone,@Param("price") String price);
	
	/**
	 * 减少余额
	 * @return
	 */
	public void reduceBalance(@Param("phone") String phone,@Param("time") Integer time);
	
	/**
	 * 新建一条用户记录
	 */
	public void insertUser(@Param("phone") String phone);
	
	
	/*
	 * 增加使用次数以及金额
	 */
	public void addUserNum(@Param("phone") String phone,@Param("money") String money);
	
}
