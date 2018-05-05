package com.eleme.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

public interface RecordDao {
	
	/**
	 * 插入领取记录数据
	 * @param id
	 * @param phoneNum
	 */
	public void insertRecord(@Param("money")String money, @Param("phone")String phone ,@Param("succeed")int succeed,@Param("time")Date time,@Param("remakes")String remakes);
}
