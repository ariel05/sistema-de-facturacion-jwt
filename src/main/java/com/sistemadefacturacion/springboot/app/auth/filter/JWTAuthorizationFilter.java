package com.sistemadefacturacion.springboot.app.auth.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String header = request.getHeader("Authorization");
		
		if(!requiresAuthentication(header)) {
			chain.doFilter(request, response);
			return;
		}
		
		boolean tokenValido;
		Claims token = null;
		
		try {
			token = Jwts.parserBuilder()
					.setSigningKey("Alguna.Super.Clave.Secreta.jajaja".getBytes())
					.build()
					.parseClaimsJws(header.replace("Bearer ", ""))
					.getBody();
			tokenValido = true;
		}catch(JwtException | IllegalArgumentException e){
			tokenValido = false;
		}
	}
	
	protected boolean requiresAuthentication(String header) {
		if(header == null || !header.startsWith("Bearer ")) {
			return false;
		}
		return true;
	}
	
}
