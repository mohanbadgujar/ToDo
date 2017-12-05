package com.bridgelabz.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.jsonResponse.Response;
import com.bridgelabz.jsonResponse.UserErrorResponse;
import com.bridgelabz.model.Login;
import com.bridgelabz.model.User;
import com.bridgelabz.services.UserServices;
import com.bridgelabz.util.SendEmail;
import com.bridgelabz.util.TokenGenerator;
import com.bridgelabz.util.VerifyJwt;
import com.bridgelabz.validator.UserValidation;

@RestController
public class UserController {

	@Autowired
	private UserServices userService;

	@Autowired
	private UserValidation userValidation;

	@Autowired
	private SendEmail sendMail;

	Response resp = new Response();

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<Response> registration(@RequestBody User user, BindingResult result,
			HttpServletRequest request) {

		System.out.println("In register api=");
		Response resp = new Response();
		UserErrorResponse err = new UserErrorResponse();

		userValidation.validate(user, result);
		String url = request.getRequestURL().toString();

		if (!result.hasErrors()) {

			try {

				userService.saveUser(user, url);

			} catch (Exception e) {

				e.printStackTrace();
				resp.setStatus(-1);
				resp.setMsg(e.getMessage());
				return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			resp.setStatus(1);
			resp.setMsg("User register sucessfully");
			return new ResponseEntity<Response>(resp, HttpStatus.OK);

		}

		err.setErrList(result.getFieldErrors());
		err.setStatus(-1);
		err.setMsg("Invalid Details");
		return new ResponseEntity<Response>(err, HttpStatus.NOT_ACCEPTABLE);

	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<Response> login(@RequestBody Login login, HttpServletRequest request,
			HttpServletResponse response) {

		String jwtToken = userService.authenticateUser(login.getEmail(), login.getPassword());

		System.out.println("Tokenis=" + jwtToken);

		if (jwtToken == null) {
			resp.setStatus(-1);
			resp.setMsg("Invalid user credential, user not present");
			return new ResponseEntity<Response>(resp, HttpStatus.UNAUTHORIZED);
		}

		/*
		 * HttpHeaders headers = new HttpHeaders(); headers.add("jwt", jwtToken);
		 */

		resp.setStatus(1);
		resp.setMsg(jwtToken);
		ResponseEntity<Response> entity = new ResponseEntity<Response>(resp, HttpStatus.OK);

		return entity;
	}

	@RequestMapping(value = "/activate/{token:.+}")
	public ResponseEntity<String> tokenActivation(@PathVariable("token") String token, HttpServletRequest request,
			HttpServletResponse response) {

		int id = VerifyJwt.verify(token);

		if (id < 0)

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Verfied");

		try {

			userService.updateUser(id);

		} catch (Exception e) {

			e.printStackTrace();

		}

		return ResponseEntity.ok("Your are verified");
	}

	@RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
	public ResponseEntity<Response> forgotProcess(@RequestBody User user, HttpServletRequest request,
			HttpServletResponse response) {

		User updatedUser = userService.getUserByEmailId(user.getEmail());

		if (updatedUser == null) {
			resp.setStatus(-1);
			resp.setMsg("Enter a valid Email");
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		String accessToken = TokenGenerator.generate(updatedUser.getId());

		sendMail.sendForgotMail(user.getEmail(), accessToken);

		resp.setStatus(1);
		resp.setMsg(accessToken);
		ResponseEntity<Response> entity = new ResponseEntity<Response>(resp, HttpStatus.OK);
		return entity;
	}

	/*
	 * @RequestMapping(value = "/resetPassword/{token:.+}", method =
	 * RequestMethod.POST) public ResponseEntity<String>
	 * resetPassword(@PathVariable("token") String token, @RequestBody User user,
	 * HttpServletRequest request, HttpServletResponse response) {
	 * 
	 * int id = VerifyJwt.verify(token);
	 * 
	 * try {
	 * 
	 * userService.updateUser(id, user.getPassword()); return
	 * ResponseEntity.ok("success");
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
	 * body("Password not reset"); }
	 */

	@RequestMapping(value = "/resetPasswords", method = RequestMethod.POST)
	public ResponseEntity<Response> resetPassword(@RequestBody User user, HttpServletRequest request,
			HttpServletResponse response) {

		System.out.println("In reset password ...");

		String token = request.getHeader("resettoken");

		System.out.println("Token is=" + token);

		int id = VerifyJwt.verify(token);

		try {

			userService.updateUser(id, user.getPassword());

		} catch (Exception e) {
			e.printStackTrace();
			resp.setStatus(1);
			resp.setMsg("Password Not reset");
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		resp.setStatus(1);
		resp.setMsg("Password Reset Successfully");
		ResponseEntity<Response> entity = new ResponseEntity<Response>(resp, HttpStatus.OK);
		return entity;

	}

	@RequestMapping(value = "/home")
	public ResponseEntity<Response> home(HttpServletRequest request, HttpServletResponse response) {

		System.out.println("In home page");

		String token = request.getHeader("token");

		System.out.println("Token is=" + token);

		if (token.equals("")) {
			resp.setStatus(-1);
			resp.setMsg("Please Login");
			return new ResponseEntity<Response>(resp, HttpStatus.UNAUTHORIZED);
		}
		resp.setStatus(1);
		resp.setMsg("Authorized");
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}
}
