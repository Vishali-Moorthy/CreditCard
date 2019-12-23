package com.bank.credit.service;

import com.bank.credit.dto.UserLoginDto;
import com.bank.credit.dto.UserLoginResponseDto;
import com.bank.credit.entity.User;
import com.bank.credit.exception.UserNotFoundException;

public interface UserService {
	
	public UserLoginResponseDto loginUser(UserLoginDto userLoginDto) throws UserNotFoundException;

}
