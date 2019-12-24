package com.bank.credit.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

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

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserServiceTest {

	@InjectMocks
	UserServiceImpl userServiceImpl;

	@Mock
	UserRepository userRepository;

	@Mock
	UserCardRepository userCardRepository;

	User user = null;
	UserLoginResponseDto userLoginResponseDto = null;
	UserLoginDto userLoginDto = null;

	RegistrationRequestDto registrationRequestDto = null;

	UserCard UserCard = null;

	@Before
	public void setup() {
		user = new User();
		user.setEmailId("vishali@gmail.com");
		user.setPassword("vishali");

		userLoginResponseDto = new UserLoginResponseDto();
		userLoginResponseDto.setEmailId(user.getEmailId());
		userLoginResponseDto.setUserId(1);
		userLoginResponseDto.setUserName("Vishalakshi");

		userLoginDto = new UserLoginDto();
		userLoginDto.setPassword(user.getPassword());
		userLoginDto.setUserId(user.getEmailId());

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

		User user = new User();
		user.setAddress("fgeh");
		user.setCompanyName(registrationRequestDto.getAddress());
		user.setCompanyName(registrationRequestDto.getCompanyName());
		user.setDateOfBirth(registrationRequestDto.getDateOfBirth());
		user.setEmailId("sdfghj@gmail.com");
		user.setPassword(registrationRequestDto.getPassword());
		user.setPhoneNumber(registrationRequestDto.getPhoneNumber());
		user.setSalary(registrationRequestDto.getSalary());
		user.setUserId(1);
		user.setUserName(registrationRequestDto.getUserName());

		UserCard = new UserCard();
		UserCard.setCardNumber(12345L);

	}

	@Test
	public void testLoginUser() throws UserNotFoundException {
		Mockito.when(userRepository.findByEmailIdAndPassword(userLoginDto.getUserId(), userLoginDto.getPassword()))
				.thenReturn(Optional.of(user));
		UserLoginResponseDto result = userServiceImpl.loginUser(userLoginDto);

		assertNotNull(userLoginResponseDto);
		assertEquals("vishali@gmail.com", result.getEmailId());
	}

	@Test(expected = UserNotFoundException.class)
	public void testLoginUserForNegative() throws UserNotFoundException {
		user = new User();
		user.setEmailId("abc@gmail.com");
		user.setPassword("abc");
		Mockito.when(userRepository.findByEmailIdAndPassword("xyz@gmail.com", "xyz")).thenReturn(Optional.of(user));
		userServiceImpl.loginUser(userLoginDto);
	}

	@Test(expected = EmailAlreadyExistException.class)
	public void testUserRegistrationForEmailAlreadyExistException()
			throws EmailAlreadyExistException, SalaryLimitException, AgeNotValidException {
		Mockito.when(userRepository.findByEmailId("pgfgv@jhgh.com")).thenReturn(Optional.of(user));
		userServiceImpl.userRegistration(registrationRequestDto);

	}

	@Test(expected = SalaryLimitException.class)
	public void testUserRegistrationForSalaryLimitException()
			throws EmailAlreadyExistException, SalaryLimitException, AgeNotValidException {

		registrationRequestDto.setEmailId("dfghjk@dfghj.com");
		user.setEmailId(registrationRequestDto.getEmailId());
		registrationRequestDto.setSalary(123D);
		Mockito.when(userRepository.findByEmailId("sdfghj@gmail.com")).thenReturn(Optional.of(user));
		userServiceImpl.userRegistration(registrationRequestDto);

	}

	@Test(expected = AgeNotValidException.class)
	public void testUserRegistrationFor()
			throws EmailAlreadyExistException, SalaryLimitException, AgeNotValidException {

		registrationRequestDto.setEmailId("dfghjk@dfghj.com");
		user.setEmailId(registrationRequestDto.getEmailId());
		registrationRequestDto.setDateOfBirth(LocalDate.now());
		Mockito.when(userRepository.findByEmailId("sdfghj@gmail.com")).thenReturn(Optional.of(user));
		userServiceImpl.userRegistration(registrationRequestDto);

	}

	@Test
	public void testUserRegistration() throws EmailAlreadyExistException, SalaryLimitException, AgeNotValidException {

		Mockito.when(userRepository.findByEmailId("sdfghj@gmail.com")).thenReturn(Optional.of(user));
		Mockito.when(userRepository.save(user)).thenReturn(user);
		Mockito.when(userCardRepository.save(UserCard)).thenReturn(UserCard);
		userServiceImpl.userRegistration(registrationRequestDto);
		assertEquals("vishali@gmail.com", user.getEmailId());
	}

}
