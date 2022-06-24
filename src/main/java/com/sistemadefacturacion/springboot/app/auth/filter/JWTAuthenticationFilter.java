package com.sistemadefacturacion.springboot.app.auth.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private AuthenticationManager authenticationManager;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login", "POST"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		String username = obtainUsername(request);
		if(username != null)
			logger.info("Username desde request parameter (form-data): " + username);
		else
			username = "";
		username = username.trim();
		String password = obtainPassword(request);
		if(password != null)	
			logger.info("Password desde request parameter (form-data): " + password);
		else
			password = "";
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
		return authenticationManager.authenticate(authToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		String username = ( (User) authResult.getPrincipal()).getUsername();
		
		SecretKey secretKey = new SecretKeySpec("Alguna.Super.Clave.Secreta.jajaja".getBytes(), SignatureAlgorithm.HS256.getJcaName());
		
		String token = Jwts.builder()
				.setSubject(username)
				.signWith(secretKey)
				.compact();
		
		response.setHeader("Authorization", "Bearer " + token);
		
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("token", token);
		body.put("user", (User) authResult.getPrincipal());
		body.put("mensaje", String.format("Hola %s, has iniciado sesión con éxito!", username));
		
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(200);
		response.setContentType("application/json");
	}
	
}
