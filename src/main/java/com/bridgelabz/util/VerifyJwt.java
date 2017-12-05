package com.bridgelabz.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

public class VerifyJwt {

	private static String key = "todoApp";

	// Sample method to validate and read the JWT
	public static int verify(String token) {
		JwtParser parser = Jwts.parser();
		try {
			Claims claims = parser.setSigningKey(key).parseClaimsJws(token).getBody();
			return Integer.parseInt(claims.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
