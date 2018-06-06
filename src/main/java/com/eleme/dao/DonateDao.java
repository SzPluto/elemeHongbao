package com.eleme.dao;

import java.util.List;
import com.eleme.entity.Donate;
public interface DonateDao {

	/**
	 * 查找前五条捐献记录
	 * @return
	 */
	public List<Donate> getDonate();
}
