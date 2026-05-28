package es.alumno.uned.model.entities;

import java.util.Collection;
import java.util.Collections;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Clase "wrapper" del Usuario de la aplicación con el UserDetails de Spring Security
 * <p> Por defecto todas las cuentas están "no bloquedas" y no hay política de "expiración".
 */
public class SecurityUser implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Usuario user;
	
	public SecurityUser(Usuario user) {
		super();
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRol()));
	}

	@Override
	public @Nullable String getPassword() {
		
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		
		return user.getEmail();
	}

	public Long getId() {
        return user.getId();
    }
	public String getRol() {
		return user.getRol();
	}
	
	@Override
	public boolean isEnabled() {
		return user.isActivo(); 
	}

    @Override
    public boolean isAccountNonExpired() {
        return true; 
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; 
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; 
    }

}
