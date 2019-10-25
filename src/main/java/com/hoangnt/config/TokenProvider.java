package com.hoangnt.config;

import static com.hoangnt.utils.Constants.ACCESS_TOKEN_VALIDITY_TIME;
import static com.hoangnt.utils.Constants.SECRET_KEY;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenProvider implements Serializable {
	private static final long serialVersionUID = 1L;
	String authorities;

	public String generateToken(Authentication authentication) {

		authentication.getAuthorities().forEach(authority -> {
			authorities = authority.toString();
		});
		return Jwts.builder().setSubject(authentication.getName()).signWith(SignatureAlgorithm.HS256, SECRET_KEY)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_TIME * 1000)).compact();

	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	private Boolean isTokenExpired(String token) {
		Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	UsernamePasswordAuthenticationToken getAuthentication(final String token, final Authentication existingAuth,
			final UserDetails userDetails) {

		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

}
