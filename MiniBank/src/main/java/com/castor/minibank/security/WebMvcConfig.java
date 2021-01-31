package com.castor.minibank.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.castor.minibank.entity.User;
import com.castor.minibank.repository.UserRepository;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Autowired
	UserRepository userRepository;

	public User getUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return this.userRepository.getUserbyEmail(auth.getName());
	}

	@Bean
	public RequestHandlerConfig requestHandlerConfig() {
		return new RequestHandlerConfig();
	}

	@Override
	public void addInterceptors(final InterceptorRegistry registry) {
	    registry.addInterceptor(this.requestHandlerConfig());
	}
}
