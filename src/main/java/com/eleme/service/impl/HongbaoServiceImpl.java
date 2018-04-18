package com.eleme.service.impl;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eleme.dao.AltDao;
import com.eleme.service.AltService;
import com.eleme.service.HongbaoService;

@Service("hongbaoService")
public class HongbaoServiceImpl implements HongbaoService {
	
	@Autowired
    private AltService altService;
	
	@Resource
	private AltDao altDao;
	
	int id = 1;
	//领红包方法
	@Override
	public int hongbao(String url,String avatar,String elemeKey,Integer id,String phoneNum) throws IOException{
		try (CloseableHttpClient httpClient = HttpClients.createDefault()){
			String sn = url;
			String regSn = "&sn=[0-9,a-z]+";
			Pattern pSn = Pattern.compile(regSn);
	        Matcher mSn = pSn.matcher(sn);
	        while(mSn.find()){
	        	sn=(mSn.group());
	        }
	        sn = sn.substring(4, sn.length());
			HttpPost httpPost = new HttpPost("https://h5.ele.me/restapi/marketing/promotion/weixin/"+avatar);		//提交请求
			httpPost.setEntity(new StringEntity("{\"group_sn\":\""+sn+"\",\"sign\":\""+elemeKey+"\","+
					"\"phone\":\""+phoneNum+"\","+
					"\"weixin_avatar\":\"\","+
					"\"weixin_username\":\"ε　　\"}"));	        //设置提交信息
			altService.addUseNumber(id);
	        //返回的信息responseHandler
	        ResponseHandler<String> responseHandler = response -> {
	            int status = response.getStatusLine().getStatusCode();
	            if (status >= 200 && status < 500) {
	                HttpEntity entity = response.getEntity();
	                return entity != null ? EntityUtils.toString(entity) : null;
	            } else {
	                throw new ClientProtocolException("Unexpected response status: " + status);
	            }
	        };
	        String responseBody = httpClient.execute(httpPost,responseHandler); 
	        
	        //识别已领取红包数量
	        int count = StringUtils.countMatches(responseBody,"\"sns_username\"");

	        //识别第几个为大红包
	        String luckyNum = url;
	        String regLuckyNum = "lucky_number=[0-9]+";      //匹配lucky_number
	        Pattern pLuckyNum = Pattern.compile(regLuckyNum);
	        Matcher mLuckyNum = pLuckyNum.matcher(luckyNum);
	        while(mLuckyNum.find()){
	        	luckyNum=(mLuckyNum.group());
	        }
	        luckyNum = luckyNum.substring(13, luckyNum.length());	//substring()方法去除前13个字符，也就是"lucky_number="，剩下就是目标红包数
	        System.out.println(responseBody);
	        System.out.println("目标红包数:"+Integer.parseInt(luckyNum));
	        System.out.println("已领红包数:"+count);
	        System.out.println("剩余红包数:"+(Integer.parseInt(luckyNum) - count));
	        System.out.println("id="+id);
	        System.out.println("手机号为="+phoneNum);
            System.out.println("--------------------------------------------");
	        return Integer.parseInt(luckyNum) - count;	//返回还需要领取的次数
		}
	}
	
	//领大红包方法
	@Override
	public  String getHongbao(String phoneNum,String url) throws IOException{
		int residueNum = 3;	//剩余需要次数
		while(residueNum>0){
			residueNum = hongbao(url,altService.getAvatar(id),altService.getElemeKey(id),id,randomPhoneNum());
	        id++;
	        while(residueNum == 1){
	        	changePhoneNum(id,phoneNum);
				residueNum = hongbao(url,altService.getAvatar(id),altService.getElemeKey(id),id,phoneNum);
				changePhoneNum(id,randomPhoneNum());
		        id++;	//每次领取后id+1
		        if(residueNum == 0){
		        	System.out.println("红包领取成功");
					return "红包领取成功";
		        }else if(residueNum < 0){
		        	System.out.println("发生未知错误！");
					return "发生未知错误！";
		        }
	        }
	    }
		System.out.println("大红包已被领取！");
		return "大红包已被领取！";
	}
	
	//修改手机号方法
	@Override
	public void changePhoneNum(Integer id,String phoneNum) throws IOException {
		try (CloseableHttpClient httpClient = HttpClients.createDefault()){
			HttpPut httpPut = new HttpPut("https://h5.ele.me/restapi/v1/weixin/"+altService.getAvatar(id)+"/phone");	
			httpPut.setEntity(new StringEntity("{\"sign\":\""+altService.getElemeKey(id)+"\",\"phone\":\""+phoneNum+"\"}"));	
            altDao.changePhoneNum(id, phoneNum);
			System.out.println(phoneNum);
			System.out.println("id="+id);
			System.out.println(altService.getAvatar(id));
			System.out.println(altService.getElemeKey(id));
			// Create a custom response handler
            ResponseHandler<String> responseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 500) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };
            String responseBody = httpClient.execute(httpPut, responseHandler);
            if(responseBody==null){
            	System.out.println("手机号更改成功！");
            }else{
            	System.out.println(responseBody);
            	System.out.println("手机号更改失败！");
            }
            System.out.println("--------------------------------------------");
            return;
		} 
	}
	
	//生成随机手机号
	@Override
	public String randomPhoneNum(){
		String rpn = "133"+(int)((Math.random()*90000000+9999999));
		return rpn;
	}
	
	/*
	 * 重置所有手机号
	 */
	public void retrunPhone(int maxId) throws IOException{
		while(true){
			changePhoneNum(id,randomPhoneNum());
			id++;
			if(id == maxId){
				return;
			}
		}
	}
}
