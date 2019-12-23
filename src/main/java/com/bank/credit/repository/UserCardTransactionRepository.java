package com.bank.credit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.credit.entity.UserCard;

@Repository
public interface UserCardTransactionRepository extends JpaRepository<UserCard, Long>{

}
