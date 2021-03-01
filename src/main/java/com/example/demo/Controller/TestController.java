package com.example.demo.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.ReCaptchaService;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class TestController {
	
	@Autowired
	ReCaptchaService captchaService;
	private Logger LOG = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	@GetMapping("/all")
	public String testingPage() {
		return "This is the testing page";
	}
	
	
	@PreAuthorize("hasRole('USER')")
	public String welcomePage() {
		return "Welcome to The Dashboard";
	}
	
	@PostMapping("/token_validate")
	public ResponseEntity<?> validate(@RequestBody String recaptcha,
			HttpServletRequest request){
		
		if(recaptcha.contains("tok")){
			String token = recaptcha.substring(8,492);
		
			LOG.log(Level.INFO, "Token Response is " + token );
		}

		
		String ip = request.getRemoteAddr();
		String captchaMessage = captchaService.verifyRecaptcha(ip, recaptcha);
		Map<String, String> resp = new HashMap<>();
		resp.put("msg", "success");
		
		if(StringUtils.isEmpty(captchaMessage)) {
			Map<String, Object> response = new HashMap<>();
			
			response.put("message",captchaMessage);
			
			return ResponseEntity.badRequest().body(recaptcha);
		}
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(resp);
	}
	

}
