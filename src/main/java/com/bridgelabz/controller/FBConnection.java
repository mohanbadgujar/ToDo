package com.bridgelabz.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FBConnection {

	private static final String FB_ID = "2003591359915924";
	private static final String SECRETE_KEY = "469777879726e9c33b992f4d600f3fff";
	private static final String REDIRECT_URL = "http://localhost:8080/ToDo/getfacebooklogin";
	private static final String USER_ACCESS_URL = "https://graph.facebook.com/v2.9/me?access_token=";
	private static final String BINDING = "&fields=id,name,email,first_name,last_name,picture";

	@SuppressWarnings("deprecation")
	public static String generateFbUrl() {
		String fbLoginUrl = "";

		try {
			fbLoginUrl = "https://www.facebook.com/v2.10/dialog/oauth?" + "client_id=" + FB_ID + "&redirect_uri="
					+ URLEncoder.encode(REDIRECT_URL) + "&state=todoappstate" + "&scope=public_profile,email";

		} catch (Exception e) {
			e.printStackTrace();
		}
		return fbLoginUrl;
	}

	public static String getAccessFbToken(String code) {
		@SuppressWarnings("deprecation")
		String fbUrlParameters = "&redirect_uri=" + URLEncoder.encode(REDIRECT_URL) + "&client_id=" + FB_ID
				+ "&client_secret=" + SECRETE_KEY + "&code=" + code;

		try {
			URL url = new URL("https://graph.facebook.com/v2.10/oauth/access_token?");
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);

			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
			writer.write(fbUrlParameters);
			writer.flush();

			String fbResponse = "";
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;

			while ((line = bufferedReader.readLine()) != null) {
				fbResponse = fbResponse + line;
			}

			ObjectMapper objectMapper = new ObjectMapper();

			String fbAccessToken = objectMapper.readTree(fbResponse).get("access_token").asText();

			return fbAccessToken;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static JsonNode getUserProfile(String fbAccessToken) {

		String fbgetUserURL = USER_ACCESS_URL + fbAccessToken + BINDING;
		System.out.println("fb get user details " + fbgetUserURL);
		
		ResteasyClient restCall = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = restCall.target(fbgetUserURL);

		String headerAuth = "Bearer " + fbAccessToken;
		Response response = target.request().header("Authorization", headerAuth).accept(MediaType.APPLICATION_JSON)
				.get();
		String profile = response.readEntity(String.class);
		ObjectMapper mapper = new ObjectMapper();

		JsonNode FBprofile = null;
		try {
			FBprofile = mapper.readTree(profile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		restCall.close();
		return FBprofile;
	}

}
