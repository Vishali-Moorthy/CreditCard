package com.bank.credit.service;

import com.bank.credit.dto.TransactionRequestDto;
import com.bank.credit.dto.TransactionResponseDto;
import com.bank.credit.dto.UserCardTransactionDto;
import com.bank.credit.exception.CardNotFoundException;
import com.bank.credit.exception.UserNotFoundException;

/**
 * This interface has method getAllTransaction is used to get all the
 * transactions by month
 * 
 * @author Priyadharshini S
 *
 * @version V1.1
 * @since 23-12-2019
 */
public interface UserCardTransactionService {


	
	/**
	 * This method getAllTransactionByMonth is used to get all the transactions by
	 * month
	 * 
	 * @param userId by passing it, the particular userId transaction can be get
	 * @param month  denotes the month of transaction
	 * @param year   denotes the year of transaction
	 * @return UserCardTransactionDto which returns the list of transactions
	 * @throws UserNotFoundException This exception occurs when user is not found
	 * @throws CardNotFoundException This exception occurs when card is not found
	 */
	UserCardTransactionDto getAllTransactionByMonth(Integer userId, Integer month, Integer year) throws UserNotFoundException, CardNotFoundException;
			

	TransactionResponseDto createTransaction(TransactionRequestDto transactionRequestDto)
			throws CardNotFoundException, UserNotFoundException;
}
