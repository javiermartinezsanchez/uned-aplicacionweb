package es.alumno.uned.model.util;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class UserUtil {

	/**
	 *   Nos define la url base a partir del rol
	 *   
	 *   <ul>
	 *   <li>ROLE_ADMIN -> admin </li>
	 *   <li>ROLE_PROFE -> profesor </li>
	 *   <li>ROLE_ESTUD -> estudiante </li>
	 *   <li>Si no está logado: -> "" </li>
	 *   
	 *   </ul>
	 * @param roles Es un Set de cadenas (normalmente de Authorities).
	 * @return Se devuelve la ruta para cada rol.
	 */
	public static String defineHome(Set<String> roles) {
	    if (roles.contains("ROLE_ADMIN")) {
	        return "admin";
	    }
	    if (roles.contains("ROLE_PROFE")) {
	        return "profesor";
	    }
	    if (roles.contains("ROLE_ESTUD")) {
	        return "estudiante";
	    }
	    return "";
     }
	
	/**
	 * Nos devuelve una colección con los roles de un usuario.
	 * 
	 * @param authentication Authentication de Spring
	 * @return Colección de Roles.
	 */
	 public static Set<String> getRol(Authentication authentication) {
		Set<String> roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
		return roles;
	 }
}
