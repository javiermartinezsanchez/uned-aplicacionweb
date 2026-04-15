package es.alumno.uned.config;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import es.alumno.uned.model.entities.UserAudit;
import es.alumno.uned.model.repository.UserAuditRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Handler customizado del login de usuarios.
 * 
 * <p>Generamos la auditoría de acceso de los usuarios.
 */
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{

	@Autowired
	UserAuditRepository audit;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
		/* Si queremos identificar el Rol o el "Authority" del usuario logado.
		boolean isAdmin = authentication.getAuthorities().stream()
				.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"));
*/
		audit.save(new UserAudit(authentication.getName(), "Login usuario", LocalDateTime.now()));
		super.onAuthenticationSuccess(request, response, authentication);
	}
}

