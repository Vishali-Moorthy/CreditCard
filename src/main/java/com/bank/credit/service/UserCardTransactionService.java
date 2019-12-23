package com.bank.credit.service;

import com.bank.credit.dto.UserCardTransactionDto;
import com.bank.credit.exception.UserNotFoundException;

public interface UserCardTransactionService {


	UserCardTransactionDto getAllTransactionByMonth(Integer userId, Integer month, Integer year)
			throws UserNotFoundException;

}
