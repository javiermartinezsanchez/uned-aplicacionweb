package es.alumno.uned.config;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import es.alumno.uned.model.entities.UserAudit;
import es.alumno.uned.model.repository.UserAuditRepository;
import es.alumno.uned.model.repository.UsuarioRepository;
import es.alumno.uned.model.util.UserUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Handler customizado del login de usuarios.
 * 
 * <p>Generamos la auditoría de acceso de los usuarios.
 * <p>Se registra la "fecha de último acceso" del usuario.
 * <p>Redirigimos dependiendo de los casos
 * <ul><li>Si viene del login directo vamos la home de cada uno de los roles.</li>
 * <li>Si es de la subscripción a un curso </li>
 * </ul>
 */
@Component
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private UserAuditRepository audit;

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    private final SessionAuthenticationStrategy sessionStrategy;

    private final RequestCache requestCache = new HttpSessionRequestCache();
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public CustomLoginSuccessHandler(SessionAuthenticationStrategy sessionStrategy) {
        this.sessionStrategy = sessionStrategy;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws ServletException, IOException {

        audit.save(new UserAudit(authentication.getName(), "Login usuario", LocalDateTime.now()));
        var usuario = usuarioRepository.findByEmail(authentication.getName()).get();
        usuario.setfUltimoAcceso(LocalDateTime.now());
        usuarioRepository.save(usuario);
        
        sessionStrategy.onAuthentication(authentication, request, response);
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            redirectStrategy.sendRedirect(request, response, targetUrl);
            return;
        }
        String continueUrl = request.getParameter("continue");
        if (continueUrl != null && !continueUrl.isEmpty() && !continueUrl.equals("null")) {
            redirectStrategy.sendRedirect(request, response, continueUrl);
            return;
        }
        
        String home = UserUtil.defineHome(UserUtil.getRoles(authentication)).concat("/home");
        redirectStrategy.sendRedirect(request, response, home);
    }
}
