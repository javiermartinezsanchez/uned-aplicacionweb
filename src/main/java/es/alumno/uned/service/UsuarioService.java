package es.alumno.uned.service;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import es.alumno.uned.controller.UserSessionInfoDTO;
import es.alumno.uned.dto.UserPasswordAdminChangeDTO;
import es.alumno.uned.dto.UserPasswordChangeDTO;
import es.alumno.uned.dto.UsuarioRegistroDTO;
import es.alumno.uned.model.entities.Usuario;
import es.alumno.uned.model.util.Paginacion;
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
	 * Método de grabación de nueva password.
	 * 
	 * <p> Validará:
	 * <ul>
	 * <li>Que el usuario exista</li>
	 * <li>Que la password actual coincida</li>
	 * <li>Que la nueva password tenga al menos 6 caracteres</li>
	 * <li>Que la nueva password coincida con la "confirmación"</li>
	 * </ul> 
	 * <p>En caso de error: Generará las excepciones en caso de que las validaciones no sean correctas.
	 * 
	 * <p>En caso de éxito: Guardará el usuario con su nueva password.
	 * 
	 * @param dto DTO con los valores de la actual, nueva y confirmación de la password.
	 * @param email Usuario que realiza la modificación (el e-mail).
	 * 
	 */
	public void cambioPassword(UserPasswordChangeDTO dto, String email);

	/**
	 * Método de grabación de nueva password por parte del admistrador.
	 * 
	 * <p> Validará:
	 * <ul>
	 * <li>Que el usuario exista</li>
	 * <li>Que la nueva password coincida con la "confirmación"</li>
	 * </ul> 
	 * <p>En caso de error: Generará las excepciones en caso de que las validaciones no sean correctas.
	 * 
	 * <p>En caso de éxito: Guardará el usuario con su nueva password.
	 * 
	 * @param dto DTO con los valores de la nueva y confirmación de la password.
	 * @param idUser Id del Usuario que hay que modificar (el e-mail).
	 * 
	 */
	public void cambioPasswordUserAdmin(UserPasswordAdminChangeDTO dto, Long idUser);

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

	public Paginacion<Usuario, UsuarioRegistroDTO> listadoPaginado(String url, Pageable pageRequest);

	/**
	 * Nos devuelve la lista de usuarios conectados (en sesión)
	 * 
	 * @return Lista de usuarios conectados
	 */
	public List<UserSessionInfoDTO> getConnectedUsers();
	
	/**
	 * Listado de Usuarios-PROFE convertidos en DTO para no exponer las entidades.
	 * 
	 * @return List de UsuarioRegistroDTO de los profesores.
	 */
	public List<UsuarioRegistroDTO> listarProfesores();
	
	/**
	 * Devolvemos el id del usuario.
	 * 
	 * @param email Dirección e-mail del usuario.
	 * @return Id del Usuario si existe.
	 */
	public Long getIdByEmail(String email);
	
	/**
	 * Nos devuelve una password encriptada según el método que se defina en la implementación.
	 * 
	 * @param password Cadena "limpia" de la contraseña
	 * @return Cadena encriptada.
	 */
	public String getEncriptedPass(String password);
}
