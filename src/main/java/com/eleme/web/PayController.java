package com.eleme.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eleme.dao.PayRecordDao;
import com.eleme.dao.UserDao;
import com.eleme.entity.Pay;
import com.eleme.util.PayUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller  
public class PayController {  
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private PayRecordDao payRecordDao;

    //
	@RequestMapping(value = "/pay",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> pay(@RequestParam (value="price", required = false)float price,
			@RequestParam (value="istype", required = false)int istype,
		@RequestParam (value="orderuid", required = false)String orderuid) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> remoteMap = new HashMap<String, Object>();
		remoteMap.put("price", price+"");
		remoteMap.put("istype", istype);
		remoteMap.put("orderid", PayUtil.getOrderIdByUUId());
		remoteMap.put("orderuid", orderuid);
		remoteMap.put("goodsname", null);
		resultMap.put("data", PayUtil.payOrder(remoteMap));
		return resultMap;
    }

	@RequestMapping("/notifyPay")
	public void notifyPay(HttpServletRequest request, HttpServletResponse response, Pay pay) throws Exception {
		// 保证密钥一致性
		if (PayUtil.checkPayKey(pay)) {
			if(userDao.checkUser(pay.getOrderuid()) == null){
				userDao.insertUser(pay.getOrderuid());
			}
			userDao.addBalance(pay.getOrderuid(), pay.getPrice());
			Date time= new java.sql.Timestamp(new java.util.Date().getTime());
			payRecordDao.insertPayRecord(pay.getPaysapi_id(),pay.getOrderid(), pay.getPrice(), pay.getRealprice(), pay.getOrderuid(),time,1);
		}else{
			Date time= new java.sql.Timestamp(new java.util.Date().getTime());
			payRecordDao.insertPayRecord(pay.getPaysapi_id(),pay.getOrderid(), pay.getPrice(), pay.getRealprice(), pay.getOrderuid(),time,0);
		}
	}
	
	//获取记录
		@RequestMapping(value = "/checkBalance",method = RequestMethod.POST)
		@ResponseBody
	    public int checkBalance(@RequestParam (value="phone", required = false)String phone) throws Exception {
			if(userDao.checkUser(phone) == null){
				return 0;
			}else{
				return userDao.getBalance(phone);  
			}
	    }
}
