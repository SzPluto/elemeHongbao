package com.eleme.service;

import java.io.IOException;

public interface HongbaoService {
	
	/**
	 * 领红包方法
	 * 
	 * @return
	 */
	public Object[] hongbao(String url, String avatar, String elemeKey, Integer id, String phoneNum) throws IOException;
	
	/**
	 * 领大红包方法
	 * 
	 * @return
	 */
	public String getHongbao(String phoneNum, String url) throws IOException;
	
	/**
	 * 重置所有手机号
	 * @param i
	 * @throws IOException
	 */
	public void retrunPhone() throws IOException;
	
	/**
	 * 修改手机号
	 * 
	 * @return
	 */
	public void changePhoneNum(Integer id,String phoneNum) throws IOException;
	
	/**
	 * 生成随机手机号
	 * @return
	 */
	public String randomPhoneNum();
	
	/**
	 * 插入领取记录
	 * @param i
	 * @param string
	 * @param j
	 */
	public void insertRecord(String money, String string, int succee,String remakes);
	
	/**
	 * 获取广告数据
	 * @return
	 */
	public String getAdvertising();
}
