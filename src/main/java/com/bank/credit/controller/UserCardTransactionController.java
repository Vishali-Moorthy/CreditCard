package com.bank.credit.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.credit.constant.Constant;
import com.bank.credit.dto.TransactionRequestDto;
import com.bank.credit.dto.TransactionResponseDto;
import com.bank.credit.dto.UserCardTransactionDto;
import com.bank.credit.exception.CardNotFoundException;
import com.bank.credit.exception.UserNotFoundException;
import com.bank.credit.service.UserCardTransactionService;

/**
 * This controller has method getAllTransactionByMonth is used to get all the
 * transactions by month
 * 
 * @author Priyadharshini S
 *
 * @version V1.1
 * @since 23-12-2019
 */
@RestController
@RequestMapping("/transactions")
@CrossOrigin
public class UserCardTransactionController {

	private static final Logger log = LoggerFactory.getLogger(UserCardTransactionController.class);

	/**
	 * This will injects all the implementations of userCardTransactionService
	 * method
	 */
	@Autowired
	UserCardTransactionService userCardTransactionService;

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
	@GetMapping("/{userId}")
	public ResponseEntity<UserCardTransactionDto> getAllTransactionByMonth(@PathVariable Integer userId,
			@RequestParam Integer month, @RequestParam Integer year)
			throws UserNotFoundException, CardNotFoundException {
		log.info("getAllTransaction controller method - getting All transactions");
		return new ResponseEntity<>(userCardTransactionService.getAllTransactionByMonth(userId, month, year), HttpStatus.OK);
	}

	/**
	 * create a transaction from buy a product from mega market product
	 * 
	 * @param transactionRequestDto
	 * @return
	 * @throws CardNotFoundException
	 */
	@PostMapping
	public ResponseEntity<TransactionResponseDto> createTransaction(
			@RequestBody TransactionRequestDto transactionRequestDto)
			throws CardNotFoundException, UserNotFoundException {
		TransactionResponseDto transactionResponseDto = userCardTransactionService
				.createTransaction(transactionRequestDto);
		transactionResponseDto.setMessage(Constant.SUCCESS);
		transactionResponseDto.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<>(transactionResponseDto, HttpStatus.OK);
	}

}
