/**
 * 
 */
function enviarValoracion(idElemento, valoracion, idContenedorBase) {
	// 1. Obtener el token CSRF y el nombre del header desde los meta tags
	const csrfToken = document.querySelector('meta[name="_csrf"]').content;
	const csrfHeaderName = document.querySelector('meta[name="_csrf_header"]').content;

	// 2. Preparar los encabezados
	const headers = {
	    'Content-Type': 'application/json',
	    // Añadimos el header CSRF con el token
	    [csrfHeaderName]: csrfToken 
	};
	// 3. Identificar el elemento valorado
	const idContenedor = 'corazones-container-' + idElemento;
    const idMensaje = 'mensaje-' + idElemento;

    const contenedor = document.getElementById(idContenedor);
    const mensaje = document.getElementById(idMensaje);

    if (!contenedor || !mensaje) {
        console.error("Contenedor no encontrado para ID:", idElemento);
        return;
    }

    const corazones = contenedor.querySelectorAll('span');

    corazones.forEach((corazon, index) => {
        const valorActual = index + 1;
        
        // Efecto visual
        corazon.style.transform = 'scale(1.2)';
        setTimeout(() => corazon.style.transform = 'scale(1)', 200);

        // Lógica de relleno y color
        if (valorActual <= valoracion) {
            // Amarillo (text-warning) y relleno (bi-heart-fill)
            // Tamaño fs-5 ya está en el HTML, pero lo forzamos por seguridad
            corazon.className = 'bi bi-heart-fill text-warning fs-5 me-1';
        } else {
            // Gris (text-secondary) y vacío (bi-heart)
            corazon.className = 'bi bi-heart text-secondary fs-5 me-1';
        }
    });

    // Llamada al servidor
    fetch('/valoracionCurso', {
        method: 'POST',
        headers: headers,
        body: JSON.stringify({
            idElemento: idElemento,
            valoracion: valoracion
        })
    })
    .then(response => 	{
	        // Si la respuesta no es OK (ej: 401, 403, 500), lanzamos error
	        if (!response.ok) {
	            throw new Error(`HTTP error! status: ${response.status}`);
	        }
	        return response.json();
	    })
    .then(data => {
        if (data.success) {
			console.log("Éxito:", data);
            mensaje.textContent = data.mensaje;
            mensaje.classList.remove('d-none');
            setTimeout(() => mensaje.classList.add('d-none'), 3000);
        } else {
            alert('Error: ' + data.mensaje);
        }
    })
    .catch(error => console.error('Error:', error));
}
