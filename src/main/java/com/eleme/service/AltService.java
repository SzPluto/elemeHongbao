package com.eleme.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

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
	 * 查找最大id
	 * @return
	 */
	public Integer findMaxId();
	
	/**
	 * 根据id查询实体获得Avatar
	 * @param id
	 * @return
	 */
	public String getAvatar(Integer id);
	
	/**根据id查询实体获得ElemeKey
	 * 
	 * @param id
	 * @return
	 */
	public String getElemeKey(Integer id);
	
	/**
	 * 根据id查询实体获得UseNum
	 * @param id
	 * @return
	 */
	public Integer getUseNum(Integer id);
	
	/**
	 * 将URL格式的Cookie转换为Avatar和ElemeKey
	 * @param cookie
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public List<String> formatConversion(String urlCookie) throws UnsupportedEncodingException;
	
	/**
	 * 向数据库提交Cookie
	 * @param Avatar
	 * @param ElemeKey
	 * @return
	 */
	public String insertCookie(List<String> cookie);
	
}
