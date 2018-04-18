package com.eleme.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.eleme.service.AltService;
import java.io.IOException;

@Controller  
public class HongbaoController {  
	
	@Autowired
    private AltService altService;

	@RequestMapping(value = "/getHongbao", method = RequestMethod.POST)
    public String getHongbao(@RequestParam String phoneNum,@RequestParam String url) throws IOException {
        altService.getHongbao(phoneNum,url);
        return "/hongbao";  
    }
    @RequestMapping(value = "/" )  
    public String index() throws IOException{  
        return "/hongbao";  
    } 
    
    @RequestMapping(value = "/resetPhoneNum" )  
    public String resetPhoneNum() throws IOException{  
        return "/hongbao";  
    }
}