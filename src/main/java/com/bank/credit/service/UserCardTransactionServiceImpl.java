package com.bank.credit.service;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.credit.constant.Constant;
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

/**
 * This service has method getAllTransactionByMonth is used to get all the
 * transactions by month
 * 
 * @author Priyadharshini S
 *
 * @version V1.1
 * @since 23-12-2019
 */
@Service
public class UserCardTransactionServiceImpl implements UserCardTransactionService {

	private static final Logger log = LoggerFactory.getLogger(UserCardTransactionServiceImpl.class);

	/**
	 * This will injects all the implementations of userCardTransactionRepository
	 * method
	 */
	@Autowired
	UserCardTransactionRepository userCardTransactionRepository;
	/**
	 * This will injects all the implementations of userCardRepository method
	 */
	@Autowired
	UserCardRepository userCardRepository;
	/**
	 * This will injects all the implementations of userRepository method
	 */
	@Autowired
	UserRepository userRepository;

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
	@Override
	public UserCardTransactionDto getAllTransaction(Integer userId, Integer month, Integer year)
			throws UserNotFoundException, CardNotFoundException {
		log.info("getAllTransaction service method - getting All transaction");
		Optional<User> user = userRepository.findById(userId);
		if (!user.isPresent()) {
			log.error("getAllTransaction service method - UserNotFoundException occurs");
			throw new UserNotFoundException(Constant.USER_NOT_FOUND);
		}

		Optional<UserCard> userCard = userCardRepository.findByUser(user.get());
		if (!userCard.isPresent()) {
			log.error("getAllTransaction service method - UserNotFoundException occurs");
			throw new CardNotFoundException(Constant.CARD_NOT_FOUND);
		} else {
			LocalDate endDate = Year.parse(year.toString()).atMonth(month).atEndOfMonth();
			LocalDate startDate = LocalDate.of(year, month, Constant.START_DATE_CONSTANT);
			List<UserCardTransaction> transactions = userCardTransactionRepository
					.findAllByUserAndTransactionDateBetween(user.get(), startDate, endDate);
			List<TransactionListResponseDTO> transactionLists = new ArrayList<>();
			transactions.forEach(transactionsList -> {
				TransactionListResponseDTO transactionListResponseDTO = new TransactionListResponseDTO();
				BeanUtils.copyProperties(transactionsList, transactionListResponseDTO);
				transactionLists.add(transactionListResponseDTO);
				log.info("getAllTransaction service method - added transactionList");
			});
			UserCardTransactionDto userCardTransactionDto = new UserCardTransactionDto();
			userCardTransactionDto.setCardNumber(userCard.get().getCardNumber());
			userCardTransactionDto.setHolderName(userCard.get().getHolderName());
			userCardTransactionDto.setAvailableAmount(userCard.get().getAvailableAmount());
			userCardTransactionDto.setTransactions(transactionLists);
			return userCardTransactionDto;
		}

	}

	@Override
	public TransactionResponseDto createTransaction(TransactionRequestDto transactionRequestDto)
			throws CardNotFoundException, UserNotFoundException {
		TransactionResponseDto transactionResponseDto = new TransactionResponseDto();
		Optional<UserCard> userCard = userCardRepository.findByCardNumber(transactionRequestDto.getCardNumber());
		if (!userCard.isPresent()) {
			throw new CardNotFoundException(Constant.CARD_NOT_FOUND);
		}

		Optional<User> user = userRepository.findByEmailId(transactionRequestDto.getUserId());
		if (!user.isPresent()) {
			throw new UserNotFoundException(Constant.USER_NOT_FOUND);
		}

		UserCardTransaction userCardTransaction = new UserCardTransaction();
		BeanUtils.copyProperties(transactionRequestDto, userCardTransaction);
		userCardTransaction.setTransactionDate(LocalDate.now());
		userCardTransaction.setUser(user.get());
		userCardTransaction.setUserCard(userCard.get());
		userCardTransaction = userCardTransactionRepository.save(userCardTransaction);
		transactionResponseDto.setTransactionId(userCardTransaction.getTransactionId());
		return transactionResponseDto;
	}

}
