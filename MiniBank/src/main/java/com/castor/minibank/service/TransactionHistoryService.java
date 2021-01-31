package com.castor.minibank.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castor.minibank.entity.TransactionHistory;
import com.castor.minibank.entity.User;
import com.castor.minibank.repository.TransactionHistoryRepository;
import com.castor.minibank.repository.UserRepository;

@Service
public class TransactionHistoryService {

	@Autowired
	TransactionHistoryRepository transactionHistoryRepository;
	
	@Autowired
	UserRepository userRepository;
	
	public TransactionHistory addNewHistory(User user, HttpServletRequest request, String transType, User sender) {
		Double amount= Double.parseDouble(request.getParameter("transAmount"));
		BigDecimal transactionAmount = BigDecimal.valueOf(amount);
		BigDecimal currentBalance = user.getBalance();
		BigDecimal newBalance = BigDecimal.ZERO;
		TransactionHistory newHistory = new TransactionHistory();
		
		if (transType.equals("Deposit")) {
			newBalance = currentBalance.add(transactionAmount);
			newHistory.setTransactionBy(user.getFirstName() + " " + user.getLastName());
			newHistory.setTransactionType(transType);
		} else if (transType.equals("Withdraw")) {
			newBalance = currentBalance.subtract(transactionAmount);
			newHistory.setTransactionBy(user.getFirstName() + " " + user.getLastName());
			newHistory.setTransactionType(transType);
		} else if (transType.equals("Transfer")) {
			String receiverName = request.getParameter("recName");
			Long receiverNo = Long.valueOf(request.getParameter("recNo"));
			User receiver = userRepository.findByAccountNumber(receiverNo);
			newBalance = currentBalance.subtract(transactionAmount);
			
			newHistory.setTransactionBy(user.getFirstName() + " " + user.getLastName());
			newHistory.setTransactionFor(receiverName);
			newHistory.setReceiverAccountNumber(receiver.getId());
			newHistory.setTransactionType("Bank Transfer");
		} else if (transType.equals("Receiver")) {
			String receiverName = request.getParameter("recName");
			Long receiverNo = Long.valueOf(request.getParameter("recNo"));
			User receiver = userRepository.findByAccountNumber(receiverNo);
			newBalance = currentBalance.add(transactionAmount);
			
			newHistory.setTransactionBy(sender.getFirstName() + " " + sender.getLastName());
			newHistory.setTransactionFor(receiverName);
			newHistory.setReceiverAccountNumber(receiver.getId());
			newHistory.setTransactionType("Bank Transfer");
		}
		newHistory.setTransactionDate(LocalDate.now());
		newHistory.setCurrentBalance(currentBalance);
		newHistory.setTransactionAmount(transactionAmount);
		newHistory.setTotalBalance(newBalance);
		transactionHistoryRepository.save(newHistory);
		return newHistory;
	}
	
	public List<TransactionHistory> getAllByUserId(Long id) {
		return this.transactionHistoryRepository.getAllByUserId(id);
	}
}
