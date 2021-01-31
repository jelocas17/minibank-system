package com.castor.minibank.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castor.minibank.dto.UserDto;
import com.castor.minibank.entity.TransactionHistory;
import com.castor.minibank.entity.User;
import com.castor.minibank.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TransactionHistoryService transactionHistoryService;
	
	public String depositMoney(User user, HttpServletRequest request){
		
		String amount = request.getParameter("transAmount");
		try {
			if (Double.parseDouble(amount) < 0) {
				return "error";
			}
			List<TransactionHistory> historyList = this.transactionHistoryService.getAllByUserId(user.getId());
			historyList.add(this.transactionHistoryService.addNewHistory(user, request, "Deposit", null));
			BigDecimal depositAmount = BigDecimal.valueOf(Double.parseDouble(amount));
			BigDecimal currentBalance = user.getBalance();
			BigDecimal newBalance = currentBalance.add(depositAmount);
			user.setBalance(newBalance);
			user.setHistory(historyList);
			userRepository.save(user);
		} catch (Exception e) {
			return "error";
		}
		return "success";
	}
	
	public String withdrawMoney(User user, HttpServletRequest request){
		
		String amount = request.getParameter("transAmount");
		try {
			if (Double.parseDouble(amount) < 0 || user.getBalance().doubleValue() < Double.parseDouble(amount)) {
				return "error";
			}
			List<TransactionHistory> historyList = this.transactionHistoryService.getAllByUserId(user.getId());
			historyList.add(this.transactionHistoryService.addNewHistory(user, request, "Withdraw", null));
			BigDecimal withdrawAmount = BigDecimal.valueOf(Double.parseDouble(amount));
			BigDecimal currentBalance = user.getBalance();
			BigDecimal newBalance = currentBalance.subtract(withdrawAmount);
			user.setBalance(newBalance);
			user.setHistory(historyList);
			userRepository.save(user);
		} catch (Exception e) {
			return "error";
		}
		return "success";
	}
	
	public String transferMoney(User user, HttpServletRequest request){
		try {
			Double amount= Double.parseDouble(request.getParameter("transAmount"));
			String receiverName = request.getParameter("recName");
			Long receiverNo = Long.valueOf(request.getParameter("recNo"));
			User receiver = userRepository.findByAccountNumber(receiverNo);
			
			if (amount < 0 || user.getBalance().doubleValue() < amount) {
				return "error";
			}
			if (receiver == null) {
				return "error";
			} else {
				List<TransactionHistory> historyList = this.transactionHistoryService.getAllByUserId(user.getId());
				historyList.add(this.transactionHistoryService.addNewHistory(user, request, "Transfer", null));
				BigDecimal transferAmount = BigDecimal.valueOf(amount);
				BigDecimal currentBalance = user.getBalance();
				BigDecimal newBalance = currentBalance.subtract(transferAmount);
				user.setBalance(newBalance);
				user.setHistory(historyList);
				userRepository.save(user);
				request.setAttribute("recName", user.getFirstName() + " " + user.getLastName());
				List<TransactionHistory> receiverHistoryList = this.transactionHistoryService.getAllByUserId(receiver.getId());
				receiverHistoryList.add(this.transactionHistoryService.addNewHistory(receiver, request, "Receiver", user));
				receiver.setBalance(receiver.getBalance().add(transferAmount));
				receiver.setHistory(receiverHistoryList);
				userRepository.save(receiver);
			}
		} catch (Exception e) {
			return "error";
		}
		return "success";
	}
	
	public String saveNewUser(UserDto dto) {
		try {
			User dupEmail = this.userRepository.findByEmail(dto.getEmail());
			if (dupEmail != null) {
				return "newuser=duplicate";
			}
			String accno = String.valueOf(LocalDate.now().getYear());  
			User newUser = new User();
			newUser.setEmail(dto.getEmail());
			newUser.setFirstName(dto.getFirstName());
			newUser.setMiddleName(dto.getMiddleName());
			newUser.setLastName(dto.getLastName());
			newUser.setPassword(dto.getPassword());
			newUser.setBalance(BigDecimal.ZERO);
			userRepository.save(newUser);
			accno = accno + "" + String.valueOf(newUser.getId());
			newUser.setAccountNumber(Long.valueOf(accno));
			userRepository.save(newUser);
		} catch (Exception e) {
			return "newuser=fail";
		}
		return "newuser=success";
	}
}
