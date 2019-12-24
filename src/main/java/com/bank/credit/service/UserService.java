package com.bank.credit.service;

import com.bank.credit.dto.RegistrationRequestDto;
import com.bank.credit.dto.UserLoginDto;
import com.bank.credit.dto.UserLoginResponseDto;
import com.bank.credit.exception.AgeNotValidException;
import com.bank.credit.exception.EmailAlreadyExistException;
import com.bank.credit.exception.SalaryLimitException;
import com.bank.credit.exception.UserNotFoundException;

/**
 * This interface has method loginUser for login the user
 * 
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
public interface UserService {

	/**
	 * This loginUser method is used to login the user
	 * 
	 * @param userLoginDto contains email and password fields
	 * @return userLoginResponseDto which returns the status message and status code
	 * @throws UserNotFoundException This exception occurs when the user is not
	 *                               found
	 */
	public UserLoginResponseDto loginUser(UserLoginDto userLoginDto) throws UserNotFoundException;
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
	void userRegistration(RegistrationRequestDto registrationRequestDto)
			throws EmailAlreadyExistException, SalaryLimitException, AgeNotValidException;

}
