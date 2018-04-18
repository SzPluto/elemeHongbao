package com.eleme.dao;

import org.apache.ibatis.annotations.Param;

import com.eleme.entity.Alt;

public interface AltDao {
	/*
	 * 根据ID查找实体
	 */
	public Alt findById(@Param("id") Integer id);
	
	/*
	 * 使用次数+1
	 */
	public void addUseNum(Integer id);
	
	/*
	 * 修改手机号
	 */
	public void changePhoneNum(Integer id, String phoneNum);
	
	/**
	 * 查找最大id
	 */
	public Integer findMaxId();
}
