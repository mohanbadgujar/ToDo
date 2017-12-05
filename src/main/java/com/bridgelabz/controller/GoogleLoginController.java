package com.bridgelabz.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.jsonResponse.Response;
import com.bridgelabz.model.User;
import com.bridgelabz.services.UserServices;
import com.bridgelabz.util.TokenGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class GoogleLoginController {
	
	@Autowired
	private UserServices userService;
	
	@RequestMapping(value="/googlelogin")
	public void googleLogin(HttpServletRequest request, HttpServletResponse response) {
		
		String googleUrl=GoogleConnection.generateGoogleUrl();
		System.out.println("checking google url"+googleUrl);
		try {
			response.sendRedirect(googleUrl);
		} catch (IOException e) {
			System.out.println("exception while generating google url");
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/getgooglelogin")
	public ResponseEntity<Response> getGoogleLogin(HttpServletRequest request, HttpServletResponse response,HttpSession session) {
		
		String code = (String)request.getParameter("code");
		System.out.println("code="+code);
		
		String accessToken = GoogleConnection.getAccessToken(code);
		System.out.println("accessToken="+accessToken);
		
		String googleProfileInfo = GoogleConnection.getProfileData(accessToken);
		System.out.println("google profile info="+googleProfileInfo);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			
			String email = objectMapper.readTree(googleProfileInfo).get("email").asText();
			System.out.println("email:-"+email);
			User user = userService.getUserByEmailId(email);
			
			if(user==null) {
				
				User googleUser = new User();
				googleUser.setEmail(email);
				
				String fullName = objectMapper.readTree(googleProfileInfo).get("name").asText();
				googleUser.setFullName(fullName);
			
				/*String lastName = objectMapper.readTree(googleProfileInfo).get("family_name").asText();
				googleUser.setLastName(lastName);*/
				
				String picture = objectMapper.readTree(googleProfileInfo).get("picture").asText();
				googleUser.setImage(picture);
				
				googleUser.setActive(true);
				
				User updatedUser = userService.saveUser(googleUser);
				
				if(updatedUser.getId() == 0){
					response.sendRedirect("http://localhost:8080/ToDo/login");
				}
				else {
					String myaccessToken = TokenGenerator.generate(updatedUser.getId());
					session.setAttribute("todoAppAccessToken", myaccessToken);
					response.sendRedirect("http://localhost:8080/ToDo/home");
				}
					
			} else {	
				String myaccessToken = TokenGenerator.generate(user.getId());
				System.out.println("token geneted by jwt" + myaccessToken);
				session.setAttribute("SocialLogin", myaccessToken);
				response.sendRedirect("http://localhost:8080/ToDo/#!/dummyPage");

			}
			
		} catch (IOException e) {
			System.out.println("exception occured during registering user from fb:");
			e.printStackTrace();
		}
		return null;
	}
}
