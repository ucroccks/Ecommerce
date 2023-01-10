package com.order.ecommerce.handler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.order.ecommerce.dto.ErrorDto;
import com.order.ecommerce.exception.BadRequestException;
import com.order.ecommerce.exception.InternalServerErrorException;
import com.order.ecommerce.exception.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@ControllerAdvice(basePackages = "com.order.ecommerce.controller")
public class GlobalExceptionHandler {

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(value = {NotFoundException.class})
	@ResponseBody
	public ErrorDto handleNotFoundException(NotFoundException exception) {
		log.error(exception.getMessage(), exception);
		return ErrorDto.builder().timestamp(LocalDateTime.now()).status(HttpStatus.NOT_FOUND.value())
				.error(HttpStatus.NOT_FOUND.toString()).message(exception.getMessage()).build();
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = {BadRequestException.class})
	@ResponseBody
	public ErrorDto handleRequestException(BadRequestException exception) {
		log.error(exception.getMessage(), exception);
		return ErrorDto.builder().timestamp(LocalDateTime.now()).status(HttpStatus.BAD_REQUEST.value())
				.error(HttpStatus.BAD_REQUEST.toString()).message(exception.getMessage()).build();
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = {MethodArgumentNotValidException.class})
	@ResponseBody
	public ErrorDto handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
		log.error(exception.getMessage(), exception);
		List<ObjectError> objectErrors = exception.getBindingResult().getAllErrors();
		String errors = objectErrors.stream().map(ObjectError::getDefaultMessage)
				.collect(Collectors.joining(" | ", "Error Count: " + objectErrors.size() + " ", ""));

		return ErrorDto.builder().timestamp(LocalDateTime.now()).status(HttpStatus.BAD_REQUEST.value())
				.error(HttpStatus.BAD_REQUEST.toString()).message(errors).build();
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = {InternalServerErrorException.class})
	@ResponseBody
	public ErrorDto handleException(InternalServerErrorException exception) {
		log.error(exception.getMessage());
		return ErrorDto.builder().timestamp(LocalDateTime.now()).status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.error(HttpStatus.INTERNAL_SERVER_ERROR.toString()).message(exception.getMessage()).build();
	}

	// Default Exception Handler. Handles with all the other exceptions

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = {Exception.class, RuntimeException.class})
	@ResponseBody
	public ErrorDto
			defaultErrorHandler(HttpServletRequest req, Exception exception) {
		log.error(exception.getMessage(), exception);
		log.error("Request: " + req.getRequestURL() + " raised " + exception);
		return ErrorDto.builder().timestamp(LocalDateTime.now()).status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.error(HttpStatus.INTERNAL_SERVER_ERROR.toString()).message(exception.getMessage()).build();
	}
}
