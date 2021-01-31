package com.castor.minibank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.castor.minibank.entity.TransactionHistory;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Integer> {

	@Query(value = "SELECT * FROM trans_history WHERE fk_user = ?1 " , nativeQuery = true)
	List<TransactionHistory> getAllByUserId(Long id);
}
