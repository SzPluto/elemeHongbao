package com.eleme.service.impl;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		return altDao.findMaxId();
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

	//根据id查询实体获得UseNum
	@Override
	public Integer getUseNum(Integer id) {
		return altDao.findById(id).getUseNum();
	}
	
	//将URL格式的Cookie转换为Avatar和ElemeKey
	@Override
	public List<String> formatConversion(String urlCookie) throws UnsupportedEncodingException {
		System.out.println(urlCookie);
		if(urlCookie == null){
			return null;
		}
		List<String> list = new ArrayList<String>();
		String cookie = "";
		String avatar = "";
		String elemeKey = "";
		cookie = URLDecoder.decode(urlCookie,"UTF-8");
		
		avatar = cookie;		//匹配Avatar
        String regAvatar = "\"avatar\":\"http://thirdqq.qlogo.cn/[a-z]+/[0-9]+/[0-9,A-Z]+";
        Pattern pAvatar = Pattern.compile(regAvatar);
        Matcher mAvatar = pAvatar.matcher(avatar);
        if(mAvatar.find()){
        	avatar = (mAvatar.group());
        }
        avatar = avatar.substring(50, avatar.length());
        
        elemeKey = cookie;		//匹配elemeKey
        String regElemeKey = "\"eleme_key\":\"[0-9,a-z]+";
        Pattern pElemeKey = Pattern.compile(regElemeKey);
        Matcher mElemeKey = pElemeKey.matcher(elemeKey);
        if(mElemeKey.find()){
        	elemeKey = (mElemeKey.group());
        }
        elemeKey = elemeKey.substring(13, elemeKey.length());
        list.add(avatar);
        list.add(elemeKey);
		System.out.println(avatar);
		System.out.println(elemeKey);
		return list;
	}

	//向数据库提交Cookie
	@Override
	public String insertCookie(List<String> cookie) {
		if(cookie == null){
			return "失败";
		}
		if(cookie.get(0)==null || cookie.get(1)==null){
			return "失败";
		}
		altDao.insertCookie(cookie.get(0),cookie.get(1),"133"+(int)((Math.random()*90000000+9999999)));
		return "成功";
	}
	
}