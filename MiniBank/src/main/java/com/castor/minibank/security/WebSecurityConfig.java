package com.castor.minibank.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	AuthenticationHandler successHandler;
	
	@Autowired
	private DataSource dataSource;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new PasswordEncoderDisabler();
	}
	
	@Override
    public void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(this.dataSource)
		.passwordEncoder(this.passwordEncoder())
		.usersByUsernameQuery("SELECT email, password, true as enabled"
				+ " FROM user"
				+ " WHERE user.email=?")
		.authoritiesByUsernameQuery("SELECT email, password, true as enabled"
				+ " FROM user"
				+ " WHERE user.email=?");
	}
	
	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/login").permitAll()
			.and()
			.formLogin()
			.successHandler(this.successHandler)
			.loginPage("/login").permitAll()
			.failureUrl("/login?error=true") // When input wrong password
			.usernameParameter("email")
			.passwordParameter("password")
			.and()
			.logout()
			.logoutUrl("/logout")
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/login?logout").permitAll() // When logged out
			.invalidateHttpSession(false)
			.and()
			.exceptionHandling();
		http
			.sessionManagement()
			.maximumSessions(32767)
			.maxSessionsPreventsLogin(false);
	}
	
	@Override
	public void configure(final WebSecurity web) throws Exception {
	    web
	       .ignoring()
	       .antMatchers("/resources/**", "/static/**", "/css/**","/js/**","/lib/**");
	    this.getHttp()
        .authorizeRequests()
        .antMatchers("/", "/index").anonymous();
    	this.getHttp().csrf().disable();

	}
}
