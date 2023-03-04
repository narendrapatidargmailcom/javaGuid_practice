package com.springboot.blog.exception;

import java.text.Format;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFound extends RuntimeException{
	
  private String resourceName;
  private String feildName;
  private Long feildValue;
public ResourceNotFound(String resourceName, String feildName, Long feildValue) {
	super(String.format("%s not found with %s : %s",resourceName,feildName,feildValue));
	this.resourceName = resourceName;
	this.feildName = feildName;
	this.feildValue = feildValue;
}
public String getResourceName() {
	return resourceName;
}
public String getFeildName() {
	return feildName;
}
public Long getFeildValue() {
	return feildValue;
}
  
  
}
