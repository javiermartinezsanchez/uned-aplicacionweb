package es.alumno.uned.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import es.alumno.uned.model.entities.UserAudit;
import es.alumno.uned.model.repository.UserAuditRepository;

@Component
public class CustomLogoutHandler implements LogoutHandler {

    private static final Logger log = LoggerFactory.getLogger(CustomLogoutHandler.class);

    @Autowired
	UserAuditRepository audit;
    
    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {

        if (authentication != null && authentication.getName() != null) {
            String username = authentication.getName();
            String ip = request.getRemoteAddr();
    		audit.save(new UserAudit(username, "Logout usuario desde la IP: " + ip, LocalDateTime.now()));

            log.info("Logout | usuario={} | ip={}", username, ip);
        }
    }
}

