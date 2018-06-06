package com.eleme.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eleme.dao.DonateDao;
import com.eleme.entity.Donate;
import com.eleme.service.DonateService;

@Service("donateService")
public class DonateServiceImpl implements DonateService{

	@Resource
	private DonateDao donateDao;
	
	@Override
	public List<Donate> getDonate() {
		return donateDao.getDonate();
	}

}
