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
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;


@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http,
			CustomLoginSuccessHandler successHandler,
			CustomLogoutHandler customLogoutHandler
			) throws Exception {
	    http
	        .authorizeHttpRequests((requests) -> requests
	            .requestMatchers("/", "/home", "/registro/**",
	                             "/webjars/**", "/login", "/login*","/invalidSession",
	                             "/css/**", "/js/**", "/images/**", "/error", "/valoracionCurso", "/viewcurso/**").permitAll()
	            .requestMatchers("/estudiante*", "/estudiante/**").hasAnyAuthority("ROLE_ESTUD", "ROLE_ADMIN")
	            .requestMatchers("/profesor/**").hasAuthority("ROLE_PROFE")
	            .requestMatchers("/admin/**" ).hasAuthority("ROLE_ADMIN")
	            .anyRequest().authenticated()
	        )
	        .exceptionHandling(ex -> ex
	                .accessDeniedPage("/error/error-403")
	            )
	        .formLogin(form -> form
	            .loginPage("/login")
	            .successHandler(successHandler)
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
	
	@Bean
	public SessionAuthenticationStrategy sessionAuthenticationStrategy(SessionRegistry sessionRegistry) {
	    return new RegisterSessionAuthenticationStrategy(sessionRegistry());
	}
}
