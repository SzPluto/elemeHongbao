package com.eleme.service.impl;

import java.io.IOException;
import java.text.BreakIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

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
import com.eleme.entity.Alt;
import com.eleme.service.AltService;
import org.apache.commons.lang.StringUtils;

@Service("altService")
public class AltServiceImpl implements AltService{
	
	int id = 1;
	
	@Resource
	private AltDao altDao;

	@Override
	public Alt findById(Integer id) {
		return altDao.findById(id);
	}

	@Override
	public void addUseNumber(Integer id) {
		altDao.addUseNum(id);
	}
	
	public String randomPhoneNum(){
		String rpn = "133"+(int)((Math.random()*90000000+9999999));
		return rpn;
	}
	
	/*
	 * 根据实体查找Avatar
	 *
	 */
	public String getAvatar(Integer id){
		return altDao.findById(id).getAvatar();
	}
	
	/*
	 * 根据实体查找ElemeKey
	 */
	public String getElemeKey(Integer id){
		return altDao.findById(id).getElemeKey();
	}
	
	//修改手机号方法
	@Override
	public void changePhoneNum(Integer i,String phoneNum) throws IOException {
		try (CloseableHttpClient httpClient = HttpClients.createDefault()){
			Thread.sleep(1);
			HttpPut httpPut = new HttpPut("https://h5.ele.me/restapi/v1/weixin/"+getAvatar(id)+"/phone");	
			httpPut.setEntity(new StringEntity("{\"sign\":\""+getElemeKey(id)+"\",\"phone\":\""+phoneNum+"\"}"));	
            altDao.changePhoneNum(i, phoneNum);
			System.out.println(phoneNum);
			System.out.println("id="+id);
			System.out.println(getAvatar(id));
			System.out.println(getElemeKey(id));
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
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
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
					"\"weixin_avatar\":\"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJB3WhdjBiaQ2vNpw29Uv0MoDn9s7rpzkXR57jiaj49ewyVebx78EaGkGrKfuAFcuyLawf81U8fC98A/0\","+
					"\"weixin_username\":\"哈\"}"));	        //设置提交信息
	        addUseNumber(id);
	        //返回的信息responseHandler
	        ResponseHandler<String> responseHandler = response -> {
	            int status = response.getStatusLine().getStatusCode();
	            if (status >= 200 && status < 300) {
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
	        String regLuckyNum = "lucky_number=[0-9]+";//匹配只有三个字母的单词
	        Pattern pLuckyNum = Pattern.compile(regLuckyNum);
	        Matcher mLuckyNum = pLuckyNum.matcher(luckyNum);
	        while(mLuckyNum.find()){
	        	luckyNum=(mLuckyNum.group());
	        }
	        luckyNum = luckyNum.substring(13, luckyNum.length());
	        System.out.println(responseBody);
	        System.out.println("目标红包数:"+Integer.parseInt(luckyNum));
	        System.out.println("已领红包数:"+count);
	        System.out.println("剩余红包数:"+(Integer.parseInt(luckyNum) - count));
	        System.out.println("id="+id);
	        System.out.println("手机号为="+phoneNum);
            System.out.println("--------------------------------------------");
	        Thread.sleep(100);
	        return Integer.parseInt(luckyNum) - count;
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return 999;
	}
	
	public  String getHongbao2(String phoneNum,String url) throws IOException{
		int residueNum = 1;	//剩余需要次数
		while(residueNum>0){
			residueNum = hongbao(url,getAvatar(id),getElemeKey(id),id,randomPhoneNum());
	        id++;	//每次领取后id+1
			if(residueNum<2){
				if(residueNum == 0){   //如果剩余次数等于0，返回异常信息
					System.out.println("此红包已被领取");
					return "此红包已被领取";
				}else if(residueNum == 1){		//如果剩余次数等于1，修改指定手机号并调用红包方法
					changePhoneNum(id,phoneNum);
					residueNum = hongbao(url,getAvatar(id),getElemeKey(id),id,phoneNum);
					changePhoneNum(id,randomPhoneNum());
			        id++;	//每次领取后id+1
					if(residueNum == 0){	//如果剩余次数等于0，修改为随机手机号并返回成功信息
						System.out.println("红包领取成功");
						return "红包领取成功";
					}else if(residueNum == 1){	//如果剩余次数等于1,修改手机号并调用红包方法
						changePhoneNum(id,phoneNum);
						residueNum = hongbao(url,getAvatar(id),getElemeKey(id),id,phoneNum);
						changePhoneNum(id,randomPhoneNum());
				        id++;	//每次领取后id+1
						while(residueNum==1){		//如果没有领取到大红包则一直循环					
							changePhoneNum(id,phoneNum);
							residueNum = hongbao(url,getAvatar(id),getElemeKey(id),id,phoneNum);
							changePhoneNum(id,randomPhoneNum());
					        id++;	//每次领取后id+1
						}if(residueNum==0){		//领取成功
							System.out.println("红包领取成功");
							return "红包领取成功";
						}else{
							System.out.println("发生未知错误");
							return "发生未知错误";			
						}
					}else{
						System.out.println("发生未知错误");
						return "发生未知错误";			
					}
				}else{
					System.out.println("发生未知错误");
					return "发生未知错误";				
				}
			}
		}
		System.out.println("此红包已被领取");
		return "此红包已被领取";
	}
	
	@Override
	public  String getHongbao(String phoneNum,String url) throws IOException{
		int residueNum = 3;	//剩余需要次数
		while(residueNum>0){
			residueNum = hongbao(url,getAvatar(id),getElemeKey(id),id,randomPhoneNum());
	        id++;
	        while(residueNum == 1){
	        	changePhoneNum(id,phoneNum);
				residueNum = hongbao(url,getAvatar(id),getElemeKey(id),id,phoneNum);
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
}