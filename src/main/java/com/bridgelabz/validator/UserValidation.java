package com.bridgelabz.validator;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bridgelabz.model.User;
import com.bridgelabz.services.UserServices;

public class UserValidation implements Validator {

	@Autowired
	UserServices userService;

	public boolean supports(Class<?> clazz) {
		return false;
	}

	public void validate(Object object, Errors errors) {

		User user = (User) object;
		Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fullName", "NotEmpty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");	
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mobile", "NotEmpty");

		if (user.getFullName().length() < 3 || user.getFullName().length() > 16) {
			errors.rejectValue("fullName", "Size.regForm.fullName");
		}

		if (userService.getUserByEmailId(user.getEmail()) != null) {
			
			System.out.println("User already exites with this emaill");
			errors.rejectValue("email", "Duplicate.regForm.email");
		}

		if (!(pattern.matcher(user.getEmail()).matches())) {
			errors.rejectValue("email", "user.email.invalid");
		}

		if (user.getPassword().length() < 3 || user.getPassword().length() > 16) {
			errors.rejectValue("password", "Size.regForm.password");
		}

		if (user.getMobile().length() < 10 || user.getPassword().length() > 10) {
			errors.rejectValue("mobile", "Size.regForm.mobile");
		}
	}
}
