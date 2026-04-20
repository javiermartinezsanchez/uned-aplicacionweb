package es.alumno.uned.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {
	
	private final CustomLoginSuccessHandler customLoginSuccessHandler;
	private final CustomLogoutHandler customLogoutHandler;
	public SecurityConfiguration(CustomLoginSuccessHandler customLoginSuccessHandler, 
			CustomLogoutHandler customLogoutHandler) {
		this.customLoginSuccessHandler = customLoginSuccessHandler; 
		this.customLogoutHandler = customLogoutHandler;
	}
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	        .authorizeHttpRequests((requests) -> requests
	            .requestMatchers("/", "/home", "/registro/**",
	                             "/webjars/**", "/login", "/login*","/invalidSession",
	                             "/css/**", "/js/**", "/images/**", "/error").permitAll()
	            .requestMatchers("/estudiante/**").hasAuthority("ROLE_ESTUD")
	            .requestMatchers("/profesor/**").hasAuthority("ROLE_PROFE")
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
            .sessionManagement((sessionManagement) -> sessionManagement.invalidSessionUrl("/invalidSession.html")
                    .maximumSessions(1)
                    .sessionRegistry(sessionRegistry()))
	        .logout(logout -> logout
	            .logoutUrl("/logout")
	            .addLogoutHandler(customLogoutHandler)
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
	
	@Bean
	public SessionRegistry sessionRegistry() {
	        return new SessionRegistryImpl();
    }

}
