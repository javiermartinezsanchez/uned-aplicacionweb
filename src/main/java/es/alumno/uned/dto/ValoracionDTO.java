package es.alumno.uned.dto;
/** DTO para la valoración de los cursos/módulos
 * 
 *  Se utilizará para intercambiar información con la vista.
 *  
 *  idElemento idCurso a valorar
 *  valoracion Valoración (1-5) para mostrar la valoración del curso.
 *  
 */
public class ValoracionDTO {
	private Long idElemento;
    private Integer valoracion;

    public Long getIdElemento() { return idElemento; }
    public void setIdElemento(Long idElemento) { this.idElemento = idElemento; }
    public Integer getValoracion() { return valoracion; }
    public void setValoracion(Integer valoracion) { this.valoracion = valoracion; }
}
