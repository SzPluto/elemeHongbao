package com.eleme.controller;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HongbaoController1 {
	public void getHongbao () throws IOException{
		try (CloseableHttpClient httpClient = HttpClients.createDefault()){
		HttpPost httpPost = new HttpPost("https://h5.ele.me/restapi/marketing/promotion/weixin/19AFD7BC399DD2A4D89B358FB4C188A2");
        //设置头信息
		httpPost.setEntity(new StringEntity("{\"group_sn\":\"29edcaf1c9af40c8\",\"sign\":\"0641bb24307edbb27d5ada3217a01210\"}"));
        System.out.println("Executing request " + httpPost.getRequestLine());
        // Create a custom response handler
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
        System.out.println("----------------------------------------");
        System.out.println(responseBody);
		}
	}
}
