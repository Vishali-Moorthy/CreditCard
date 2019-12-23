package com.bank.credit.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.credit.constant.Constant;
import com.bank.credit.dto.UserLoginDto;
import com.bank.credit.dto.UserLoginResponseDto;
import com.bank.credit.entity.User;
import com.bank.credit.exception.UserNotFoundException;
import com.bank.credit.repository.UserRepository;

/**
 * This service has the method loginUser for login the user
 * 
 * @author Vishalakshi D
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

}
