package com.eleme.service;

import java.io.IOException;

import com.eleme.entity.Alt;

public interface AltService {
	
	/**
	 * 根据ID查找实体
	 * 
	 * @return
	 */
	public Alt findById(Integer id);
	
	/**
	 * 使用次数+1
	 * 
	 * @return
	 */
	public void addUseNumber(Integer id);
	
	/**
	 * 修改手机号
	 * 
	 * @return
	 */
	public void changePhoneNum(Integer id,String phoneNum) throws IOException;
	
	/**
	 * 领红包方法
	 * 
	 * @return
	 */
	int hongbao(String url, String avatar, String elemeKey, Integer id, String phoneNum) throws IOException;
	
	/**
	 * 领大红包方法
	 * 
	 * @return
	 */
	public  String getHongbao(String phoneNum, String url) throws IOException;
	
}
