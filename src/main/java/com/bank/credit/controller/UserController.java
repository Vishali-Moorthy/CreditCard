package com.bank.credit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.credit.constant.Constant;
import com.bank.credit.dto.UserLoginDto;
import com.bank.credit.dto.UserLoginResponseDto;
import com.bank.credit.entity.User;
import com.bank.credit.exception.UserNotFoundException;
import com.bank.credit.service.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping("/login")
	public ResponseEntity<UserLoginResponseDto> loginUser(@RequestBody UserLoginDto userLoginDto)
			throws UserNotFoundException {
		UserLoginResponseDto userLoginResponseDto = userService.loginUser(userLoginDto);
		userLoginResponseDto.setMessage(Constant.LOGIN_SUCCESS_MESSAGE);
		userLoginResponseDto.setStatusCode(Constant.LOGIN_SUCCESS_CODE);
		userLoginResponseDto.setLoginType(Constant.LOGIN_TYPE);
		return new ResponseEntity<UserLoginResponseDto>(userLoginResponseDto, HttpStatus.OK);
	}

}
