package com.eleme.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.eleme.service.StatService;
import java.util.HashMap;
import java.util.Map;

@Controller  
public class StatController {  
	
	@Autowired
	private StatService statService;

    //获取记录
	@RequestMapping(value = "/getRecord",produces = "application/json;charset=UTF-8")
	@ResponseBody
    public Map<String,Object> getRecord() throws Exception {
		Map<String,Object> massage = new HashMap<String,Object>();  
		massage.put("todayGetRecordNumber", statService.todayGetRecordNumber());
		massage.put("todayGetRecordMoney", statService.todayGetRecordMoney());
		massage.put("allGetRecordNumber", statService.allGetRecordNumber());
		massage.put("allGetRecordMoney", statService.allGetRecordMoney());
		return massage;  
    }
	
}