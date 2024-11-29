package com.fl2.gd.Services;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@SuppressWarnings("deprecation")
@Service
public class JwtService {
	
	private static final String secretKey = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9reyJpc3MiOiJPbmxpbmUgSldUIEJ1aWxkZXIiLCJpYXQiOjE3MDM0NDQ2MjIsImV4cCI6MTczNDk4MDYyMiwiYXVkIjoid3d3LmV4YW1wbGUuY29tIiwic3ViIjoianJvY2tldEBleGFtcGxlLmNvbSIsIkdpdmVuTmFtZSI6IkpvaG5ueSIsIlN1cm5hbWUiOiJSb2NrZXQiLCJFbWFpbCI6Impyb2NrZXRAZXhhbXBsZS5jb20iLCJSb2xlIjpbIk1hbmFnZXIiLCJQcm9qZWN0IEFkbWluaXN0cmF0b3IiXX09S2BpozCUEgtm1kOWPZBkjshQtGYGGTnW48nfCsvGtQsx5HwuE4RAXQ9lcUDpXLLfLdeQCsdr4xHLxjU7TG53LRs";

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	public String generateToken(UserDetails userDetails){
		return generateToken(new HashMap<>(),userDetails);
	}
	
	public String generateToken(Map<String, Object> extraClamis, UserDetails userDetails) {
		return Jwts
			  .builder()
			  .setClaims(extraClamis)
			  .setSubject(userDetails.getUsername())
			  .setIssuedAt(new Date(System.currentTimeMillis()))
			  .setExpiration(new Date(System.currentTimeMillis()+ 1000*60*60))
			  .signWith(getSignInKey(), SignatureAlgorithm.HS256)
			  .compact();
	}
	
	public boolean isTokenValid(String token, UserDetails userDetails){
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}
	
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private Claims extractAllClaims(String token) {
		return Jwts
				.parser()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}