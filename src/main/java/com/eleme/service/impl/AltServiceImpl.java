package com.eleme.service.impl;


import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
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
	public List<String> formatConversion(String urlCookie) throws IOException {
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
        }else{
        	return null;
        }
        avatar = avatar.substring(50, avatar.length());
        
        elemeKey = cookie;		//匹配elemeKey
        String regElemeKey = "\"eleme_key\":\"[0-9,a-z]+";
        Pattern pElemeKey = Pattern.compile(regElemeKey);
        Matcher mElemeKey = pElemeKey.matcher(elemeKey);
        if(mElemeKey.find()){
        	elemeKey = (mElemeKey.group());
        }else{
        	return null;
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
	public String insertCookie(List<String> cookie) throws IOException {
		if(cookie == null){		//如果Cookie为空，返回失败信息
			return "错误";
		}
		if(cookie.get(0)==null || cookie.get(1)==null){		//如果Cookie中有任意一个为空，则返回失败信息
			return "错误";
		}
		if(!"200".equals(checkCookie(cookie))){		//如果不是正确的Cookie，则返回非法的Cookie信息
			return "非法的Cookie";
		}
		if(altDao.checkCookieRepeat(cookie.get(1))<1){		//判断数据库中是否有重复Cookie，如果有则返回此Cookie已存在信息，否则则为成功
			altDao.insertCookie(cookie.get(0),cookie.get(1),"133"+(int)((Math.random()*90000000+9999999)));
			return "成功啦！\n谢谢你(´-｀ )";
		}else{
			return "此Cookie已存在";
		}
	}

	/*
	 * 检测Cookie是否合法
	 * 如果合法返回"200",其他为非法
	 */
	@Override
	public String checkCookie(List<String> cookie) throws IOException {
		String avatar = cookie.get(0);
		String elemeKey = cookie.get(1);
		
		try (CloseableHttpClient httpClient = HttpClients.createDefault()){
			HttpPost httpPost = new HttpPost("https://h5.ele.me/restapi/marketing/promotion/weixin/"+avatar);		//提交请求
			httpPost.setEntity(new StringEntity("{\"group_sn\":\"10e761aecb85c818\",\"sign\":\""+elemeKey+"\","+
					"\"phone\":\""+"133"+(int)((Math.random()*90000000+9999999))+"\","+
					"\"weixin_avatar\":\"\","+
					"\"weixin_username\":\"ε　　\"}"));	        //设置提交信息
	        //返回的信息responseHandler
	        ResponseHandler<String> responseHandler = response -> {
	            String status =  String.valueOf(response.getStatusLine().getStatusCode());
	            return status;
	        };
	        String responseBody = httpClient.execute(httpPost,responseHandler); 
	        return responseBody;
		}
	}

	/**
	 * 获取下一个id，如果为0，则返回1
	 */
	@Override
	public Integer getNextId(Integer id) {
		int nextId = altDao.getNextId(id);
		if(nextId == 0){
			return 1;
		}else{
			return nextId;
		}
	}
	
	/**
	 * 领取错误次数+1
	 */
	@Override
	public void addErrorNumber(Integer id) {
		altDao.addErrorNum(id);
	}
	
}