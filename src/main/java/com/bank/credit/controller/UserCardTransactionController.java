package com.bank.credit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.credit.dto.UserCardTransactionDto;
import com.bank.credit.exception.UserNotFoundException;
import com.bank.credit.service.UserCardTransactionService;

@RestController
@RequestMapping("/transactions")
@CrossOrigin
public class UserCardTransactionController {
	
	@Autowired
	UserCardTransactionService userCardTransactionService;

	@GetMapping("/{userId}")
	public ResponseEntity<UserCardTransactionDto> getAllTransactionByMonth(@PathVariable Integer userId,
			@RequestParam Integer month, @RequestParam Integer year) throws UserNotFoundException {
		return new ResponseEntity<>(userCardTransactionService.getAllTransactionByMonth(userId, month, year), HttpStatus.OK);
	}


}
