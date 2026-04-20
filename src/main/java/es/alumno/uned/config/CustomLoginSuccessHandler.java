package es.alumno.uned.config;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.stereotype.Component;

import es.alumno.uned.model.entities.UserAudit;
import es.alumno.uned.model.repository.UserAuditRepository;
import es.alumno.uned.model.util.UserUtil;
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
	
	private final SessionAuthenticationStrategy sessionStrategy;

    public CustomLoginSuccessHandler(SessionAuthenticationStrategy sessionStrategy) {
        this.sessionStrategy = sessionStrategy;
    }
    
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
		audit.save(new UserAudit(authentication.getName(), "Login usuario", LocalDateTime.now()));
		sessionStrategy.onAuthentication(authentication, request, response);
		// Definimos la home de cada tipo de usuario
		getRedirectStrategy().sendRedirect(request, response, UserUtil.defineHome(UserUtil.getRol(authentication)).concat("/home"));
	}
}

