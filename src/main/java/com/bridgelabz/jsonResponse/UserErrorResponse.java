package com.bridgelabz.jsonResponse;

import java.util.List;

import org.springframework.validation.FieldError;

public class UserErrorResponse extends Response {
	
	List<FieldError> errList;

	public List<FieldError> getErrList() {
		return errList;
	}

	public void setErrList(List<FieldError> errList) {
		this.errList = errList;
	}
	
}
