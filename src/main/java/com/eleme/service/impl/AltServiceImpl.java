package com.eleme.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.eleme.dao.AltDao;
import com.eleme.entity.Alt;
import com.eleme.service.AltService;

@Service("altService")
public class AltServiceImpl implements AltService{
	
	@Resource
	private AltDao altDao;
	
	//根据id查找实体
	@Override
	public Alt findById(Integer id) {
		return altDao.findById(id);
	}
	
	//账号使用次数+1
	@Override
	public void addUseNumber(Integer id) {
		altDao.addUseNum(id);
	}
	
	//查找最大id
	@Override
	public Integer findMaxId() {
		return null;
	}
	
	//根据id查询实体获得Avatar
	@Override
	public String getAvatar(Integer id){
		return altDao.findById(id).getAvatar();
	}
	
	//根据实体查找ElemeKey
	@Override
	public String getElemeKey(Integer id){
		return altDao.findById(id).getElemeKey();
	}
}