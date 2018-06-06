package com.eleme.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.eleme.entity.Alt;

public interface StatService {
	
	/**
	 *  查找今日领取次数
	 * 
	 * @return
	 */
	public String todayGetRecordNumber();
	
	/**
	 * 查找今日领取金额
	 * 
	 * @return
	 */
	public String todayGetRecordMoney();
	
	/**
	 * 查找总领取次数
	 * 
	 * @return
	 */
	public String allGetRecordNumber();
	
	/**
	 * 查找总领取金额
	 * 
	 * @return
	 */
	public String allGetRecordMoney();

}
