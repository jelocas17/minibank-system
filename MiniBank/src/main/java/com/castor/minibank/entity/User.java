package com.castor.minibank.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "acc_no")
	private Long accountNumber;
	
	@Column(name = "email", columnDefinition = "VARCHAR(50)" )
	private String email;

	@Column(name = "first_name", columnDefinition = "VARCHAR(50)" )
	private String firstName;

	@Column(name = "last_name", columnDefinition = "VARCHAR(50)")
	private String lastName;
	
	@Column(name = "middle_name", columnDefinition = "VARCHAR(50)")
	private String middleName;
	
	@Column(name = "password", columnDefinition = "VARCHAR(50)")
	private String password;

	@Column(name = "balance", columnDefinition = "DECIMAL(20,2) default 0")
	private BigDecimal balance;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_user")
	private List<TransactionHistory> history;
	
	public User() {
		super();
	}
	
	public User(Long id, Long accountNumber, String email, String firstName, String lastName, String middleName, String password, BigDecimal balance, List<TransactionHistory> history) {
		super();
		this.id = id;
		this.accountNumber = accountNumber;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.password = password;
		this.balance = balance;
		this.history = history;
	}
}
