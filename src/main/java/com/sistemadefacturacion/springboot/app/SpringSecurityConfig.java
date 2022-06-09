package com.sistemadefacturacion.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.sistemadefacturacion.springboot.app.auth.handler.LoginSuccessHandler;
import com.sistemadefacturacion.springboot.app.models.service.JpaUserDetailsService;

@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private LoginSuccessHandler successHandler;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JpaUserDetailsService userDetailService;
	
	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception{

		builder.userDetailsService(userDetailService)
		.passwordEncoder(passwordEncoder);

		
//		builder.jdbcAuthentication().dataSource(datasource)
//		.passwordEncoder(passwordEncoder)
//		.usersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username=?")
//		.authoritiesByUsernameQuery("SELECT u.username, a.authority FROM authorities a JOIN users u ON (a.user_id=u.id) WHERE u.username=?");
		
//		PasswordEncoder encoder = this.passwordEncoder;
//		
////		UserBuilder users = User.builder().passwordEncoder(password ->  encoder.encode(password));
//		UserBuilder users = User.builder().passwordEncoder(encoder::encode);
//		
//		builder.inMemoryAuthentication()
//		.withUser(users.username("admin").password("12345").roles("ADMIN", "USER"))
//		.withUser(users.username("ariel").password("12345").roles("USER"));
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/img/**", "/listar", "/locale", "/listar-rest").permitAll()
		/*.antMatchers("/ver/**").hasAnyRole("USER")
		.antMatchers("/uploads/**").hasAnyRole("USER")
		.antMatchers("/form/**").hasAnyRole("ADMIN")
		.antMatchers("/eliminar/**").hasAnyRole("ADMIN")
		.antMatchers("/factura/**").hasAnyRole("ADMIN")*/
		.anyRequest().authenticated()
		.and()
			.formLogin()
			.successHandler(successHandler)
			.loginPage("/login")
			.permitAll()
		.and()
			.logout()
			.permitAll()
		.and()
			.exceptionHandling()
			.accessDeniedPage("/error_403");
	}
	
	
	
}
