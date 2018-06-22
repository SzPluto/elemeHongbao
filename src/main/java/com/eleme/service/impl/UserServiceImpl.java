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
	 * 检测该用户是否欠费，没欠费返回false，欠费返回true，如果数据库中没有这条数据则新增一条
	 */
	@Override
	public boolean checkBalance(String phone) {
		if(userDao.checkUser(phone) == null){
			userDao.insertUser(phone);
		}
		if(userDao.getBalance(phone)<=0){
			return true;
		}else{
			return false;
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
