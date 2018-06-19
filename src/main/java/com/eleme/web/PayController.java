package com.eleme.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eleme.entity.Pay;
import com.eleme.service.StatService;
import com.eleme.util.PayUtil;
import java.util.HashMap;
import java.util.Map;

@Controller  
public class PayController {  
	
	@Autowired
	private StatService statService;

    //
	@RequestMapping(value = "/pay",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> pay(@RequestParam (value="price", required = false)float price,
			@RequestParam (value="istype", required = false)int istype) throws Exception {
		System.out.println(price);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> remoteMap = new HashMap<String, Object>();
		remoteMap.put("price", price+"");
		remoteMap.put("istype", istype);
		remoteMap.put("orderid", PayUtil.getOrderIdByUUId());
		remoteMap.put("orderuid", null);
		remoteMap.put("goodsname", null);
		resultMap.put("data", PayUtil.payOrder(remoteMap));
		return resultMap;
    }

	@RequestMapping(value="/notifyPay",method = RequestMethod.POST)
	@ResponseBody
	public void notifyPay(@RequestParam (value="pay", required = false) Pay pay) throws Exception {
		// 保证密钥一致性
		if (PayUtil.checkPayKey(pay)) {
			System.out.println("支付成功");
		} else {
			// TODO 该怎么做就怎么做
		}
	}
}
