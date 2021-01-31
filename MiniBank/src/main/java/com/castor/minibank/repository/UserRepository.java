package com.castor.minibank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.castor.minibank.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query(value = "SELECT * FROM user WHERE email = ?1 " , nativeQuery = true)
	User getUserbyEmail(String code);
	
	User findByAccountNumber(Long accountNumber);
	
	User findByEmail(String email);
}
