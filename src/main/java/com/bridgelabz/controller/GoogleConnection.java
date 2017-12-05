package com.bridgelabz.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GoogleConnection {

	private static final String CLIENT_ID="322339652751-e6qmofvaqtkrl2rme9a2jrqk1t98e5jr.apps.googleusercontent.com";
	private static final String CLIENT_SECRET="ramUvPO-ogijrp0gPvS4qaGM";
	private static final String REDIRECT_URL="http://localhost:8080/ToDo/getgooglelogin";
	
	public static String generateGoogleUrl() {
		String googleLoginUrl="";
		try {
			googleLoginUrl="https://accounts.google.com/o/oauth2/auth?client_id=" + CLIENT_ID + "&redirect_uri=" + 
					URLEncoder.encode(REDIRECT_URL, "UTF-8") + "&response_type=code" + "&scope=profile email"
					+ "&approval_prompt=force" + "&access_type=offline";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return googleLoginUrl;
	}
	
	public static String getAccessToken(String code) {
		 String urlParameters = "code=" + code + 
			       "&client_id=" + CLIENT_ID +
			       "&client_secret=" + CLIENT_SECRET + 
			       "&redirect_uri=" + URLEncoder.encode(REDIRECT_URL) +
			       "&grant_type=authorization_code";
		 
		 try {
			 
			URL url = new URL("https://accounts.google.com/o/oauth2/token");
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			String googleResponse = "";
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			
			while((line = bufferedReader.readLine()) != null){
				googleResponse = googleResponse + line;
			}
			
			ObjectMapper objectMapper = new ObjectMapper();
			
			String googleAccessToken;
			try {
				googleAccessToken = objectMapper.readTree(googleResponse).get("access_token").asText();
				System.out.println("google aceess token by code:-"+googleAccessToken);
			} catch (IOException e) {
				System.out.println("exception occured if access token is null:-");
				e.printStackTrace();
				return null;
			}
			return googleAccessToken;
		} catch (IOException e) {
			e.printStackTrace();
		}
		 return null;
	}


	public static String getProfileData(String accessToken) {
		try {
			URL googleUrlPrfl = new URL("https://www.googleapis.com/oauth2/v1/userinfo?access_token="+accessToken);
			URLConnection connection = googleUrlPrfl.openConnection();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String googleProfileInfo="";
			String line;
			while((line = bufferedReader.readLine())!= null) {
				googleProfileInfo = googleProfileInfo+line; 
			}
			return googleProfileInfo;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("connection refused if exception occured");
			e.printStackTrace();
		}
		return null;
	}

}
