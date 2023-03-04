package com.springboot.blog.exception;

import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.springboot.blog.dto.ErrorDetail;

@ControllerAdvice
public class GlobleExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(ResourceNotFound.class)
	public ResponseEntity<ErrorDetail> handleResourceNotFoundException(ResourceNotFound exception,
			WebRequest webrequest)
	{
		ErrorDetail errordetail = new ErrorDetail(new Date(),exception.getMessage(),webrequest.getDescription(false));
		return new ResponseEntity<>(errordetail , HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(BlogAPIException.class)
	public ResponseEntity<ErrorDetail> handleBlogAPIException(BlogAPIException exception,
			WebRequest webrequest)
	{
		ErrorDetail errordetail = new ErrorDetail(new Date(),exception.getMessage(),webrequest.getDescription(false));
		return new ResponseEntity<>(errordetail , HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetail> handleBlogAPIException(Exception exception,
			WebRequest webrequest)
	{
		ErrorDetail errordetail = new ErrorDetail(new Date(),exception.getMessage(),webrequest.getDescription(false));
		return new ResponseEntity<>(errordetail , HttpStatus.INTERNAL_SERVER_ERROR );
		
	}
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		// TODO Auto-generated method stub
		Map<String,String> errors  = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error)->{
			String feildName = ((FieldError)error).getField();
			String message = error.getDefaultMessage();
			errors.put(feildName,message);
		});;
		return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
	}
	
	/*
	 * @ExceptionHandler(ResourceNotFound.class) public ResponseEntity<Object>
	 * handleMethodArgumentNotValidException(MethodArgumentNotValidException
	 * exception, WebRequest webrequest) { Map<String,String> errors = new
	 * HashMap<>(); exception.getBindingResult().getAllErrors().forEach((error)->{
	 * String feildName = ((FieldError)error).getField(); String message =
	 * error.getDefaultMessage(); errors.put(feildName,message); });; return new
	 * ResponseEntity<>(errors , HttpStatus.NOT_FOUND);
	 * 
	 * }
	 */
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorDetail> handleAccessDeniedException(AccessDeniedException exception,
			WebRequest webrequest)
	{
		ErrorDetail errordetail = new ErrorDetail(new Date(),exception.getMessage(),webrequest.getDescription(false));
		return new ResponseEntity<>(errordetail , HttpStatus.UNAUTHORIZED);
		
	}

}
