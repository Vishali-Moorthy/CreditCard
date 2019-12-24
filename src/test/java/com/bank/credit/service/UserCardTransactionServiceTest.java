package com.bank.credit.service;

import static org.junit.Assert.assertNotNull;

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
	List<TransactionListResponseDTO> transactionLists = null;

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

		List<TransactionListResponseDTO> transactionLists = new ArrayList<>();
		transactionLists.add(transactionListResponseDTO);

		userCardTransactionDto = new UserCardTransactionDto();
		userCardTransactionDto.setAvailableAmount(10000.0);
		userCardTransactionDto.setCardNumber(3100909382838L);
		userCardTransactionDto.setHolderName("Visha");
		userCardTransactionDto.setTransactions(transactionLists);

	}

	@Test(expected = UserNotFoundException.class)
	public void testGetAllTransactionForNegativeUser() throws UserNotFoundException, CardNotFoundException {
		user= new User();
		userCard = new UserCard();
		userCard.setCardNumber(7252424L);
		userCard.setUser(user);
		Mockito.when(userRepository.findById(17)).thenReturn(Optional.of(user));
		Mockito.when(userCardRepository.findByUser(Mockito.any())).thenReturn(Optional.of(userCard));
		userCardTransactionServiceImpl.getAllTransactionByMonth(1, 12, 2019);

	}

	@Test(expected = CardNotFoundException.class)
	public void testGetAllTransactionForNegativeUserCard() throws UserNotFoundException, CardNotFoundException {
		user = new User();
		Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
		userCardTransactionServiceImpl.getAllTransactionByMonth(2, 12, 2019);

	}

	@Test
	public void testGetAllTransaction() throws UserNotFoundException, CardNotFoundException {
		Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
		Mockito.when(userCardRepository.findByUser(user)).thenReturn(Optional.of(userCard));
		userCardTransactionServiceImpl.getAllTransactionByMonth(1, 12, 2019);
		assertNotNull(userCardTransactionDto);

	}

}
