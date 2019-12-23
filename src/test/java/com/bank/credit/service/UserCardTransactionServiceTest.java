package com.bank.credit.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.bank.credit.dto.TransactionListResponseDTO;
import com.bank.credit.dto.TransactionRequestDto;
import com.bank.credit.dto.TransactionResponseDto;
import com.bank.credit.dto.UserCardTransactionDto;
import com.bank.credit.entity.User;
import com.bank.credit.entity.UserCard;
import com.bank.credit.entity.UserCardTransaction;
import com.bank.credit.exception.CardNotFoundException;
import com.bank.credit.exception.UserNotFoundException;
import com.bank.credit.repository.UserCardRepository;
import com.bank.credit.repository.UserCardTransactionRepository;
import com.bank.credit.repository.UserRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserCardTransactionServiceTest {

	@InjectMocks
	UserCardTransactionServiceImpl userCardTransactionServiceImpl;

	@Mock
	UserCardTransactionRepository userCardTransactionRepository;

	@Mock
	UserRepository userRepository;

	@Mock
	UserCardRepository userCardRepository;

	User user = null;
	UserCard userCard = null;
	UserCardTransactionDto userCardTransactionDto = null;
	UserCardTransaction userCardTransaction = null;
	TransactionListResponseDTO transactionListResponseDTO = null;
	TransactionRequestDto transactionRequestDto = new TransactionRequestDto();

	@Before
	public void setup() {
		user = new User();
		user.setUserId(1);

		userCard = new UserCard();
		userCard.setCardNumber(7252424L);
		userCard.setUser(user);

		userCardTransaction = new UserCardTransaction();
		userCardTransaction.setUser(user);
		userCardTransaction.setTransactionDate(LocalDate.parse("2019-12-02"));

		transactionListResponseDTO = new TransactionListResponseDTO();
		transactionListResponseDTO.setDescription("xyz");
		transactionListResponseDTO.setTransactionAmount(1000.0);
		transactionListResponseDTO.setTransactionDate(LocalDate.parse("2019-12-02"));

		transactionRequestDto.setCardNumber(78728832L);
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

		Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
		Mockito.when(userCardRepository.findByUser(user)).thenReturn(Optional.of(userCard));
		userCardTransactionServiceImpl.getAllTransaction(1, 12, 2019);

		assertNotNull(userCardTransactionDto);

	}

	@Test(expected = UserNotFoundException.class)
	public void testGetAllTransactionForNegative() throws UserNotFoundException, CardNotFoundException {
		List<TransactionListResponseDTO> transactionLists = new ArrayList<>();
		transactionLists.add(transactionListResponseDTO);

		userCardTransactionDto = new UserCardTransactionDto();
		userCardTransactionDto.setAvailableAmount(10000.0);
		userCardTransactionDto.setCardNumber(3100909382838L);
		userCardTransactionDto.setHolderName("Visha");
		userCardTransactionDto.setTransactions(transactionLists);

		Mockito.when(userRepository.findById(2)).thenReturn(Optional.ofNullable(null));
		userCardTransactionServiceImpl.getAllTransaction(2, 12, 2019);

	}

	@Test(expected = CardNotFoundException.class)
	public void testGetAllTransactionForNegative1() throws UserNotFoundException, CardNotFoundException {
		List<TransactionListResponseDTO> transactionLists = new ArrayList<>();
		transactionLists.add(transactionListResponseDTO);

		userCardTransactionDto = new UserCardTransactionDto();
		userCardTransactionDto.setAvailableAmount(10000.0);
		userCardTransactionDto.setCardNumber(3100909382838L);
		userCardTransactionDto.setHolderName("Visha");
		userCardTransactionDto.setTransactions(transactionLists);

		UserCard userCard = new UserCard();
		userCard.setCardNumber(3100909382838L);

		Mockito.when(userRepository.findById(13)).thenReturn(Optional.of(user));
		Mockito.when(userCardRepository.findByUser(Mockito.any())).thenReturn(Optional.ofNullable(null));
		userCardTransactionServiceImpl.getAllTransaction(13, 12, 2019);

	}

	@Test(expected = CardNotFoundException.class)
	public void testCreateTransactionForCardNotFound() throws CardNotFoundException, UserNotFoundException {
		Mockito.when(userCardRepository.findByCardNumber(transactionRequestDto.getCardNumber()))
				.thenReturn(Optional.ofNullable(null));
		userCardTransactionServiceImpl.createTransaction(transactionRequestDto);

	}
	
	@Test(expected = UserNotFoundException.class)
	public void testCreateTransactionForUserNotFound() throws CardNotFoundException, UserNotFoundException {
		Mockito.when(userCardRepository.findByCardNumber(transactionRequestDto.getCardNumber()))
				.thenReturn(Optional.of(userCard));
		Mockito.when(userRepository.findByEmailId("moorthy127@ggmail.com")).thenReturn(Optional.ofNullable(null));
		userCardTransactionServiceImpl.createTransaction(transactionRequestDto);

	}
	
	@Test
	public void testCreateTransaction() throws CardNotFoundException, UserNotFoundException {
		transactionRequestDto.setUserId("moorthy127@ggmail.com");
		userCardTransaction.setTransactionId(123456L);
		user.setEmailId("moorthy127@ggmail.com");
		
		Mockito.when(userCardRepository.findByCardNumber(transactionRequestDto.getCardNumber()))
				.thenReturn(Optional.of(userCard));
		Mockito.when(userRepository.findByEmailId(transactionRequestDto.getUserId())).thenReturn(Optional.of(user));
		Mockito.when(userCardTransactionRepository.save(Mockito.any())).thenReturn(userCardTransaction);
		TransactionResponseDto response = userCardTransactionServiceImpl.createTransaction(transactionRequestDto);
		assertEquals(123456L, response.getTransactionId());

	}
}
