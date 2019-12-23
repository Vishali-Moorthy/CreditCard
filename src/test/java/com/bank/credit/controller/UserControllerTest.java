package com.bank.credit.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bank.credit.constant.Constant;
import com.bank.credit.dto.RegistrationRequestDto;
import com.bank.credit.dto.RegistrationResponseDto;
import com.bank.credit.dto.UserLoginDto;
import com.bank.credit.dto.UserLoginResponseDto;
import com.bank.credit.entity.User;
import com.bank.credit.exception.AgeNotValidException;
import com.bank.credit.exception.EmailAlreadyExistException;
import com.bank.credit.exception.SalaryLimitException;
import com.bank.credit.exception.UserNotFoundException;
import com.bank.credit.service.UserService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserControllerTest {

	@InjectMocks
	UserController userController;

	@Mock
	UserService userService;

	UserLoginDto userLoginDto = null;
	UserLoginResponseDto userLoginResponseDto = null;
	RegistrationRequestDto registrationRequestDto = null;
	User user = null;

	@Before
	public void setup() {
		userLoginResponseDto = new UserLoginResponseDto();
		userLoginResponseDto.setMessage(Constant.LOGIN_SUCCESS_MESSAGE);
		userLoginResponseDto.setStatusCode(Constant.LOGIN_SUCCESS_CODE);
		userLoginResponseDto.setLoginType(Constant.LOGIN_TYPE);

		userLoginDto = new UserLoginDto();
		userLoginDto.setPassword("vishali");
		userLoginDto.setUserId("vishali@gmail.com");
		registrationRequestDto = new RegistrationRequestDto();
		registrationRequestDto.setAddress("address");
		registrationRequestDto.setCompanyName("hcl");
		registrationRequestDto.setDateOfBirth(LocalDate.of(1997, 11, 02));
		registrationRequestDto.setEmailId("pgfgv@jhgh.com");
		registrationRequestDto.setPassword("hjghv");
		registrationRequestDto.setPhoneNumber(9087654123L);
		registrationRequestDto.setSalary(14256D);
		registrationRequestDto.setUserName("priya");

		user = new User();
		user.setAddress("fgeh");
		user.setCompanyName(registrationRequestDto.getAddress());
		user.setCompanyName(registrationRequestDto.getCompanyName());
		user.setDateOfBirth(registrationRequestDto.getDateOfBirth());
		user.setEmailId(userLoginResponseDto.getEmailId());
		user.setPassword(registrationRequestDto.getPassword());
		user.setPhoneNumber(registrationRequestDto.getPhoneNumber());
		user.setSalary(registrationRequestDto.getSalary());
		user.setUserId(1);
		user.setUserName(registrationRequestDto.getUserName());

	}

	@Test
	public void testLoginUser() throws UserNotFoundException {
		Mockito.when(userService.loginUser(userLoginDto)).thenReturn(userLoginResponseDto);
		ResponseEntity<UserLoginResponseDto> result = userController.loginUser(userLoginDto);
		assertNotNull(userLoginResponseDto);
		assertEquals(Constant.LOGIN_SUCCESS_CODE, result.getStatusCodeValue());

	}

	@Test
	public void testUserRegistration() throws EmailAlreadyExistException, SalaryLimitException, AgeNotValidException {
		userService.userRegistration(registrationRequestDto);
		ResponseEntity<RegistrationResponseDto> result = userController.userRegistration(registrationRequestDto);
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

}
