package es.alumno.uned.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {
	
	private final CustomLoginSuccessHandler customLoginSuccessHandler;
	public SecurityConfiguration(CustomLoginSuccessHandler customLoginSuccessHandler) {
		this.customLoginSuccessHandler = customLoginSuccessHandler; 
	}
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	        .authorizeHttpRequests((requests) -> requests
	            .requestMatchers("/", "/home", "/registro/**",
	                             "/webjars/**", "/login", "/login*",
	                             "/css/**", "/js/**", "/images/**", "/error").permitAll()
	            .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
	            .anyRequest().authenticated()
	        )
	        .formLogin(form -> form
	            .loginPage("/login")
	            .successHandler(customLoginSuccessHandler)
	            //.defaultSuccessUrl("/home")
	            .failureUrl("/login?error=true")
	            .permitAll()
	        )
	        .logout(logout -> logout
	            .logoutUrl("/logout")
	            .logoutSuccessUrl("/login?logout")
	            .invalidateHttpSession(true)
	            .clearAuthentication(true)
	            .deleteCookies("JSESSIONID")
	            .permitAll()
	        );

	    return http.build();
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
}
