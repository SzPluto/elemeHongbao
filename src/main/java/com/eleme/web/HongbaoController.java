package com.eleme.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.eleme.service.AltService;
import com.eleme.service.HongbaoService;

import java.io.IOException;

@Controller  
public class HongbaoController {  
	
	@Autowired
	private HongbaoService hongbaoService;

    @RequestMapping(value = "/" )  
    public String index() throws IOException{  
        return "/hongbao";  
    } 
    
	@RequestMapping(value = "/getHongbao", method = RequestMethod.POST)
    public String getHongbao(@RequestParam String phoneNum,@RequestParam String url) throws IOException {
		hongbaoService.getHongbao(phoneNum,url);
        return "/hongbao";  
    }

    @RequestMapping(value = "/returnPhone" )  
    public String returnPhone() throws IOException{  
    	hongbaoService.retrunPhone(32);
        return "/hongbao";  
    } 
}