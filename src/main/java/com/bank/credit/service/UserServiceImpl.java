package com.bank.credit.service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.credit.constant.Constant;
import com.bank.credit.dto.RegistrationRequestDto;
import com.bank.credit.dto.UserLoginDto;
import com.bank.credit.dto.UserLoginResponseDto;
import com.bank.credit.entity.User;
import com.bank.credit.entity.UserCard;
import com.bank.credit.exception.AgeNotValidException;
import com.bank.credit.exception.EmailAlreadyExistException;
import com.bank.credit.exception.SalaryLimitException;
import com.bank.credit.exception.UserNotFoundException;
import com.bank.credit.repository.UserCardRepository;
import com.bank.credit.repository.UserRepository;

/**
 * This service has the method loginUser for login the user
 * 
 * @Api loginUser method is used to login the user
 * @Api userRegistration method iis used to register the new user
 * 
 * @author Vishalakshi D
 * @author Priyadharshini S
 *  
 * @version V1.1
 * @since 23-12-2019
 */
@Service
public class UserServiceImpl implements UserService {
	
	
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);


	/**
	 * This will injects all the implementations of userRepository
	 */
	@Autowired
	UserRepository userRepository;
	

	@Autowired
	UserCardRepository userCardRepository;

	/**
	 * This loginUser method is used to login the user
	 * 
	 * @param userLoginDto contains email and password fields
	 * @return userLoginResponseDto which returns the status message and status code
	 * @throws UserNotFoundException This exception occurs when the user is not
	 *                               found
	 */
	@Override
	public UserLoginResponseDto loginUser(UserLoginDto userLoginDto) throws UserNotFoundException {
		log.info("loginUser service method - login the user");
		UserLoginResponseDto userLoginResponseDto = new UserLoginResponseDto();
		Optional<User> user = userRepository.findByEmailIdAndPassword(userLoginDto.getUserId(),
				userLoginDto.getPassword());
		if (user.isPresent()) {
			userLoginResponseDto.setUserId(user.get().getUserId());
			userLoginResponseDto.setEmailId(user.get().getEmailId());
			userLoginResponseDto.setUserName(user.get().getUserName());
			return userLoginResponseDto;

		} else {
			log.error("loginUser service method - UserNotFoundException occurs");
			throw new UserNotFoundException(Constant.USER_NOT_FOUND);
		}
	}
	
	/**
	 * This method is used for user registration
	 * 
	 * @param registrationRequestDto has details to be entered by the user
	 * @return the registrationResponseDto success message and success code
	 * @throws EmailAlreadyExistException This exception occurs when the email
	 *                                    already exists
	 * @throws SalaryLimitException       This exception occurs when the salary
	 *                                    exceed 10000
	 * @throws AgeNotValidException       This exception occurs when the age is
	 *                                    above 18
	 */
	@Override
	@Transactional
	public void userRegistration(RegistrationRequestDto registrationRequestDto)
			throws EmailAlreadyExistException, SalaryLimitException, AgeNotValidException {
		log.info("userRegistration service method - registering the user");
		Optional<User> optionalUser = userRepository.findByEmailId(registrationRequestDto.getEmailId());
		Integer age = LocalDate.now().getYear() - registrationRequestDto.getDateOfBirth().getYear();
		if (optionalUser.isPresent()) {
			log.error("userRegistration service method - EmailAlreadyExistException occurs");
			throw new EmailAlreadyExistException(Constant.EMAILID_ALREADY_EXIST);
		} else if (registrationRequestDto.getSalary() < Constant.SALARY_LIMIT) {
			log.error("userRegistration service method - SalaryLimitException occurs");
			throw new SalaryLimitException(Constant.SALARY_LIMIT_EXCEEDED);
		} else if (age <= Constant.AGE_LIMIT) {
			log.error("userRegistration service method - AgeNotValidException occurs");
			throw new AgeNotValidException(Constant.AGE_NOT_VALID);
		} else {
			User user = new User();
			BeanUtils.copyProperties(registrationRequestDto, user);
			userRepository.save(user);
			log.info("userRegistration service method - registered the user");
			UserCard userCard = new UserCard();
			userCard.setCreditLimit(Constant.CREDIT_LIMIT_MULTIPLIER * registrationRequestDto.getSalary());
			userCard.setValidFrom(LocalDate.now());
			userCard.setValidTo(LocalDate.now().plusYears(Constant.VALID_TO_CONSTANT));
			userCard.setAvailableAmount(Constant.CREDIT_LIMIT_MULTIPLIER * registrationRequestDto.getSalary());
			Random random = new Random();
			Integer cvv = random.nextInt(Constant.CVV_RANDOM_NUMBER_MAX) + Constant.CVV_RANDOM_NUMBER_MIN;
			userCard.setCvv(cvv);
			userCard.setUser(user);
			userCard.setHolderName(user.getUserName());
			userCardRepository.save(userCard);
			log.info("userRegistration service method - added the userCard information");
		}

	}


}
