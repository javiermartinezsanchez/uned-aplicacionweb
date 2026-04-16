package es.alumno.uned.config;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import es.alumno.uned.model.entities.UserAudit;
import es.alumno.uned.model.repository.UserAuditRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Handler customizado del login de usuarios.
 * 
 * <p>Generamos la auditoría de acceso de los usuarios.
 * <p>Redirigimos a la home de cada uno de los roles.
 */
@Component
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{

	@Autowired
	UserAuditRepository audit;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
		audit.save(new UserAudit(authentication.getName(), "Login usuario", LocalDateTime.now()));

		Set<String> roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

		//super.onAuthenticationSuccess(request, response, authentication);
		// Definimos la home de cada tipo de usuario
		getRedirectStrategy().sendRedirect(request, response, defineHome(roles));
	}
	
	private String defineHome(Set<String> roles) {
    if (roles.contains("ROLE_ADMIN")) {
        return "/admin/home";
    }
    if (roles.contains("ROLE_PROFE")) {
        return "/profesor/home";
    }
    return "/home";
}

}

