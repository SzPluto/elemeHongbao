package com.eleme.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.eleme.service.AltService;
import java.io.IOException;
import java.util.List;

@Controller  
public class AltController {  
	
	@Autowired
	private AltService altService;

    @RequestMapping(value = "/alt" )  
    public String cookie() throws IOException{  
        return "alt";  
    } 
    
    @RequestMapping(value = "/tutorial" )  
    public String tutorial() throws IOException{  
        return "tutorial";  
    } 
    
	@RequestMapping(value = "/insertCookie", method = RequestMethod.POST)
	@ResponseBody
    public String submitCookie(@RequestParam (value="urlCookie", required = false) String urlCookie) throws IOException {
		List<String> cookie = altService.formatConversion(urlCookie);
		String message = altService.insertCookie(cookie);
		return message;  
    }
}