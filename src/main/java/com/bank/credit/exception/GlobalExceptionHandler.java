package com.bank.credit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bank.credit.constant.Constant;
import com.bank.credit.dto.ErrorDto;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorDto> accountNotFoundException() {
		ErrorDto errorDto = new ErrorDto();
		errorDto.setErrorMessage(Constant.USER_NOT_FOUND);
		errorDto.setErrorStatusCode(HttpStatus.NOT_FOUND.value());
		return ResponseEntity.status(HttpStatus.OK).body(errorDto);
	}

	@ExceptionHandler(AgeNotValidException.class)
	public ResponseEntity<ErrorDto> ageNotValidException() {
		ErrorDto errorDto = new ErrorDto();
		errorDto.setErrorMessage(Constant.AGE_NOT_VALID);
		errorDto.setErrorStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
		return ResponseEntity.status(HttpStatus.OK).body(errorDto);
	}

	@ExceptionHandler(SalaryLimitException.class)
	public ResponseEntity<ErrorDto> salaryLimitException() {
		ErrorDto errorDto = new ErrorDto();
		errorDto.setErrorMessage(Constant.SALARY_LIMIT_EXCEEDED);
		errorDto.setErrorStatusCode(HttpStatus.NOT_MODIFIED.value());
		return ResponseEntity.status(HttpStatus.OK).body(errorDto);
	}

	@ExceptionHandler(EmailAlreadyExistException.class)
	public ResponseEntity<ErrorDto> EmailAlreadyExistException() {
		ErrorDto errorDto = new ErrorDto();
		errorDto.setErrorMessage(Constant.EMAILID_ALREADY_EXIST);
		errorDto.setErrorStatusCode(HttpStatus.CONFLICT.value());
		return ResponseEntity.status(HttpStatus.OK).body(errorDto);
	}

}
