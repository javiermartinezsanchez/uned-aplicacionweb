package es.alumno.uned.service;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import es.alumno.uned.dto.UsuarioRegistroDTO;
import es.alumno.uned.model.entities.Usuario;
import es.alumno.uned.model.util.PaginacionComun;
import jakarta.validation.Valid;

public interface UsuarioService extends UserDetailsService{

	/**
	 * Guardamos la información de un usuario a través del POJO UsuarioRegistroDTO.
	 * 
	 * @param registroDTO La informacion del usuario encapsulada en el DTO
	 * @param usuarioAlta Usuario que genera el Usuario (en el Alta).
	 * @return Devolvemos el usuario guardado
	 */
	public Usuario grabar(@Valid UsuarioRegistroDTO form, String username);
	/**
	 * Listado de Usuarios convertidos en DTO para no exponer las entidades.
	 * 
	 * @return List de UsuarioRegistroDTO
	 */
	public List<UsuarioRegistroDTO> listarUsuarios();

	/**
	 * Búsqueda de usuarios por su ID
	 * 
	 * @param id Valor del id del Usuario
	 * @return Usuario encontrado.
	 */
	public Usuario findById(Long id);

	/**
	 * Búsqueda de usuarios por su ID
	 * Para las vistas devolvemos el DTO
	 * 
	 * @param id Valor del id del Usuario
	 * @return Usuario encontrado.
	 */
	public UsuarioRegistroDTO getUsuario(Long id);

	/**
	 * Búsqueda de usuarios por su email (username para UserDetails)
	 * 
	 * @param email Email del usuario
	 * @return Usuario encontrado.
	 */
	public Usuario findByEmail(String email);
	public PaginacionComun<Usuario> listadoPaginado(String string, Pageable pageRequest);
	public @Nullable Object users2DTO(List<Usuario> content);


	/**
	 * Nos devuelve la lista de usuarios conectados (en sesión)
	 * 
	 * @return Lista de usuarios conectados
	 */
	public List<String> getConnectedUsers();
	
}
