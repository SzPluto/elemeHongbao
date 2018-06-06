package com.eleme.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eleme.entity.Donate;
import com.eleme.service.DonateService;
import com.eleme.service.StatService;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller  
public class DonateController {  
	
	@Autowired
	private DonateService donateService;

    //网页提交Cookie
	@RequestMapping(value = "/getDonate",produces = "application/json;charset=UTF-8")
	@ResponseBody
    public List<Donate> getDonate() throws IOException {
		return donateService.getDonate();  
    }
	
}