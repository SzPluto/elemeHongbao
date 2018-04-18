package com.eleme.service;

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
}
