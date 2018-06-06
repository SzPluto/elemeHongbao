package com.eleme.service;

import java.util.List;
import com.eleme.entity.Donate;

public interface DonateService {
	
	/**
	 *  查找今日领取次数
	 * 
	 * @return
	 */
	public List<Donate> getDonate();

}
