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
import com.fasterxml.jackson.databind.JsonNode;

@RestController
public class FbLoginController {

	@Autowired
	private UserServices userService;

	@RequestMapping(value = "/facebooklogin")
	public void fbLogin(HttpServletRequest request, HttpServletResponse response) {
		String facebookUrl = FBConnection.generateFbUrl();
		System.out.println("Facebook Url=" + facebookUrl);

		try {
			response.sendRedirect(facebookUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(value = "/socialPageRedirect")
	public ResponseEntity<Response> redirectToHome( HttpSession session) {
		
			String token = (String) session.getAttribute("SocialLogin");
			Response res = new Response();
			res.setStatus(1);
			res.setMsg(token);
			return ResponseEntity.ok(res);
	}

	@RequestMapping(value = "/getfacebooklogin")
	public ResponseEntity<Response> getFbAccessToken(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {

		String facebookbCode = request.getParameter("code");
		System.out.println("facebookbCode:-" + facebookbCode);

		String accessFbToken = FBConnection.getAccessFbToken(facebookbCode);
		System.out.println("accessFbToken:-" + accessFbToken);

		JsonNode facebookProfInfo = FBConnection.getUserProfile(accessFbToken);

		System.out.println(facebookProfInfo);

		try {

			User user = userService.getUserByEmailId(facebookProfInfo.get("email").asText());
			System.out.println("getUserByEmail:-" + user);

			if (user == null) {

				User facebookUser = new User();

				facebookUser.setEmail(facebookProfInfo.get("email").asText());

				facebookUser.setFullName(facebookProfInfo.get("name").asText());

				facebookUser.setImage(facebookProfInfo.get("picture").get("data").get("url").asText());

				System.out.println("Image url=" + facebookUser.getImage());

				facebookUser.setActive(true);

				User updatedUser = userService.saveUser(facebookUser);

				if (updatedUser.getId() == -1) {

					response.sendRedirect("http://localhost:8080/ToDo/login");

				} else {

					String accessToken = TokenGenerator.generate(updatedUser.getId());
					session.setAttribute("todoAppAccessToken", accessToken);
					response.sendRedirect("http://localhost:8080/ToDo/home");
				}
			} else {

				String accessToken = TokenGenerator.generate(user.getId());
				System.out.println("token geneted by jwt" + accessToken);
				session.setAttribute("SocialLogin", accessToken);
				response.sendRedirect("http://localhost:8080/ToDo/#!/dummyPage");
			}

		} catch (IOException e) {
			System.out.print("exception occured during registering user from fb:");
			e.printStackTrace();
		}
		
		return null;
	}
}
