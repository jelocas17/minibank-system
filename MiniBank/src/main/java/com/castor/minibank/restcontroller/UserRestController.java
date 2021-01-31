package com.castor.minibank.restcontroller;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.castor.minibank.dto.UserDto;
import com.castor.minibank.entity.User;
import com.castor.minibank.security.WebMvcConfig;
import com.castor.minibank.service.UserService;

@RestController
public class UserRestController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	private WebMvcConfig webMvcConfig;

	@PostMapping("/bank/deposit")
	public ModelAndView deposit(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		String status = this.userService.depositMoney(this.webMvcConfig.getUser(), request);
		if (status.equals("error")) {
			mav.setViewName("redirect:/bank/dashboard?error=Invalid Transaction");
		} else {
			mav.setViewName("redirect:/bank/dashboard?success=Deposit Successful. Thank you for depositing");
		}

		return mav;

	}
	
	@PostMapping("/bank/withdraw")
	public ModelAndView withdraw(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		String status = this.userService.withdrawMoney(this.webMvcConfig.getUser(), request);
		if (status.equals("error")) {
			mav.setViewName("redirect:/bank/dashboard?error=Invalid Withdraw Amount");
		} else {
			mav.setViewName("redirect:/bank/dashboard?success=Withdraw Successful. Thank you for withdrawing");
		}

		return mav;

	}
	
	@PostMapping("/bank/transfer")
	public ModelAndView transfer(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		String status = this.userService.transferMoney(this.webMvcConfig.getUser(), request);
		if (status.equals("error")) {
			mav.setViewName("redirect:/bank/dashboard?error=Invalid Transaction");
		} else {
			mav.setViewName("redirect:/bank/dashboard?success=Transfer Successful. Thank you for your transaction");
		}

		return mav;

	}
	
	@PostMapping(value = "/signup")
	public ModelAndView showRegisterForm(final ModelAndView mav, @ModelAttribute("user") UserDto dto) {
		
		String result = this.userService.saveNewUser(dto);
		mav.setViewName("redirect:/login?"+result);
		return mav;
	}
}
