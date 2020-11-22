package io.javabrains.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@Service
public class JwtUtil {

	private final String SECRETE_KEY="secrete";
	public String generateToken(UserDetails UserDetails) {
		Map<String,Object> claims=new  HashMap<>();
		return createToken(claims,UserDetails.getUsername());
	}

	private String createToken(Map<String, Object> claims, String subject) {
		// TODO Auto-generated method stub
		return Jwts.builder().setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000+60+10))
				.signWith(SignatureAlgorithm.HS256, SECRETE_KEY).compact();
	}
	public boolean validateToken(String token,UserDetails userDetails ) {
		final String userName=extractUserName(token);
		return userName.equals(userDetails.getUsername());
	}

	private String extractUserName(String token) {
		// TODO Auto-generated method stub
		return extractClaims(token,Claims::getSubject);
	}

	private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
		// TODO Auto-generated method stub
		final Claims  claims=extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		// TODO Auto-generated method stub
		return Jwts.parser().setSigningKey(SECRETE_KEY).parseClaimsJws(token).getBody();
	}
}
