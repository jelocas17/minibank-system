package com.castor.minibank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.castor.minibank.dto.UserDto;
import com.castor.minibank.security.WebMvcConfig;
import com.castor.minibank.service.TransactionHistoryService;

@Controller
public class MainController {

	@Autowired
	private WebMvcConfig webMvcConfig;
	
	@Autowired
	private TransactionHistoryService transactionHistoryService;
	
	@GetMapping(value = { "", "/", "/login" })
	public ModelAndView login(ModelAndView mav, @RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {

		if (error != null) {
			mav.addObject("error_login", "User ID or password is wrong.");
		}

		if (logout != null) {
			mav.addObject("logout", "You have successfully logged out.");
		}
		
		if (this.webMvcConfig.getUser() == null) {
			mav.setViewName("/login");
		} else {
			System.out.println("asd");
		}

		return mav;
	}
	
	@GetMapping(value = "/bank/dashboard")
	public ModelAndView showDashboard(final ModelAndView mav) {
		mav.addObject("logged_user", this.webMvcConfig.getUser());
		mav.addObject("transactions", this.transactionHistoryService.getAllByUserId(this.webMvcConfig.getUser().getId()));
		mav.setViewName("user/dashboard");
		return mav;
	}
	
	@GetMapping(value = "/signup")
	public ModelAndView showRegisterForm(final ModelAndView mav) {
		UserDto dto = new UserDto();
		mav.addObject("user", dto);
		mav.setViewName("user/register");
		return mav;
	}
}
