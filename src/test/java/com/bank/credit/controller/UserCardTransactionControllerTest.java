package com.bank.credit.controller;

import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.bank.credit.dto.TransactionListResponseDTO;
import com.bank.credit.dto.UserCardTransactionDto;
import com.bank.credit.entity.User;
import com.bank.credit.entity.UserCard;
import com.bank.credit.entity.UserCardTransaction;
import com.bank.credit.exception.CardNotFoundException;
import com.bank.credit.exception.UserNotFoundException;
import com.bank.credit.service.UserCardTransactionService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserCardTransactionControllerTest {

	@InjectMocks
	UserCardTransactionController userCardTransactionController;

	@Mock
	UserCardTransactionService userCardTransactionService;

	User user = null;
	UserCard userCard = null;
	UserCardTransactionDto userCardTransactionDto = null;
	UserCardTransaction userCardTransaction = null;
	TransactionListResponseDTO transactionListResponseDTO = null;

	@Before
	public void setup() {
		user = new User();
		user.setUserId(1);

		userCard = new UserCard();
		userCard.setUser(user);

		userCardTransaction = new UserCardTransaction();
		userCardTransaction.setUser(user);
		userCardTransaction.setTransactionDate(LocalDate.parse("2019-12-02"));

		transactionListResponseDTO = new TransactionListResponseDTO();
		transactionListResponseDTO.setDescription("xyz");
		transactionListResponseDTO.setTransactionAmount(1000.0);
		transactionListResponseDTO.setTransactionDate(LocalDate.parse("2019-12-02"));

	}

	@Test
	public void testGetAllTransaction() throws UserNotFoundException, CardNotFoundException {
		List<TransactionListResponseDTO> transactionLists = new ArrayList<>();
		transactionLists.add(transactionListResponseDTO);

		userCardTransactionDto = new UserCardTransactionDto();
		userCardTransactionDto.setAvailableAmount(10000.0);
		userCardTransactionDto.setCardNumber(3100909382838L);
		userCardTransactionDto.setHolderName("Visha");
		userCardTransactionDto.setTransactions(transactionLists);

		Mockito.when(userCardTransactionService.getAllTransactionByMonth(1, 12, 2019)).thenReturn(userCardTransactionDto);
		userCardTransactionController.getAllTransactionByMonth(1, 12, 2019);

		assertNotNull(userCardTransactionDto);
	}
}