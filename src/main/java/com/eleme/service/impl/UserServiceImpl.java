package com.eleme.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.eleme.dao.UserDao;
import com.eleme.service.UserService;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService{

	@Resource
	private UserDao userDao;
	
	/**
	 * 检测该用户是否超过领取上限，未超过返回false，超过返回true，如果数据库中没有这条数据则新增一条
	 */
	@Override
	public boolean checkTodayUseNumber(String phone) {
		if(userDao.checkUser(phone) == null){
			userDao.insertUser(phone);
		}
		if(userDao.getTodayUseNumber(phone)<userDao.getSetUseNumber(phone)){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 增加使用次数以及金额
	 */
	@Override
	public void addUseNumber(String phone, String money) {
		userDao.addUserNum(phone, money);	
	}

}
