package org.sample.web.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * @author mpostelnicu
 * 
 */
@Configuration
@Lazy
@EnableWebSecurity
public class SpringSecurityWebConfiguration extends
		WebSecurityConfigurerAdapter {

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/img/**", "/css*/**", "/js*/**",
				"/assets*/**", "/wicket/resource/**/*.js",
				"/wicket/resource/**/*.css", "/wicket/resource/**/*.png",
				"/wicket/resource/**/*.jpg","/wicket/resource/**/*.gif","/login/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {		
		http
        .formLogin()
            .loginPage("/login").and().rememberMe().and().sessionManagement().
            sessionCreationPolicy(SessionCreationPolicy.NEVER).and().csrf().disable();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("password")
				.roles(Roles.USER);
	}
}