package com.castor.minibank.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "trans_history")
public class TransactionHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@CreatedDate
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "trans_date", nullable = false)
	private LocalDate transactionDate;
	
	@Column(name = "trans_by", columnDefinition = "VARCHAR(50)" )
	private String transactionBy;
	
	@Column(name = "trans_for", columnDefinition = "VARCHAR(50)" )
	private String transactionFor;
	
	@Column(name = "trans_type", columnDefinition = "VARCHAR(50)" )
	private String transactionType;
	
	@Column(name = "current_balance", columnDefinition = "DECIMAL(20,2) default 0")
	private BigDecimal currentBalance;
	
	@Column(name = "trans_amount", columnDefinition = "DECIMAL(20,2) default 0")
	private BigDecimal transactionAmount;
	
	@Column(name = "total_balance", columnDefinition = "DECIMAL(20,2) default 0")
	private BigDecimal totalBalance;
	
	@Column(name = "receiver_acc_no")
	private Long receiverAccountNumber;
	
	public TransactionHistory() {
		super();
	}
	
	public TransactionHistory(Integer id, LocalDate transactionDate, String transactionBy, String transactionFor, String transactionType, BigDecimal currentBalance, BigDecimal transactionAmount, Long receiverAccountNumber, BigDecimal totalBalance) {
		super();
		this.id = id;
		this.transactionDate = transactionDate;
		this.transactionBy = transactionBy;
		this.transactionFor = transactionFor;
		this.transactionType = transactionType;
		this.currentBalance = currentBalance;
		this.transactionAmount = transactionAmount;
		this.totalBalance = totalBalance;
		this.receiverAccountNumber = receiverAccountNumber;
	}
}
