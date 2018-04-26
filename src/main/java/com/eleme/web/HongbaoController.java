package com.eleme.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eleme.service.AltService;
import com.eleme.service.HongbaoService;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

@Controller  
public class HongbaoController {  
	
	@Autowired
	private HongbaoService hongbaoService;

    @RequestMapping(value = "/" )  
    public String index() throws IOException{  
        return "hongbao";  
    } 
    
	@RequestMapping(value = "/getHongbao", method = RequestMethod.POST)
	@ResponseBody
    public String getHongbao(@RequestParam (value="phoneNum", required = false) String phoneNum,
    		@RequestParam (value="url", required = false) String url) throws IOException {
		String message = hongbaoService.getHongbao(phoneNum,url);
        return message;  
    }

    @RequestMapping(value = "/returnPhone" )  
    public String returnPhone() throws IOException{  
    	hongbaoService.retrunPhone(32);
        return "hongbao";  
    } 
}