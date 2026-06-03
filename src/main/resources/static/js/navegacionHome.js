/**
 * Función genérica que llama a nuestro controlador para refrescar el carrusel de cursos. 
 */
$(document).ready(function() {
    $(document).off('click', '.btn-paginar-ajax').on('click', '.btn-paginar-ajax', function(e) {
        e.preventDefault();
        
        var $boton = $(this);
        var urlALlamar = $boton.data('url');   
        var idDestino = $boton.data('target'); 
		console.log("urlALlamar: ", urlALlamar);
        if (!urlALlamar || urlALlamar === '#' || !idDestino || $boton.hasClass('disabled')) {
            return;
        }

        var selectorCSS = idDestino.startsWith('#') ? idDestino : '#' + idDestino;
        var $contenedor = $(selectorCSS);

        if ($contenedor.length) {
            $boton.addClass('disabled');
            $contenedor.css('opacity', '0.5');

            // Hacemos el GET y reemplazamos el bloque entero de forma limpia
            $.get(urlALlamar, function(htmlRecibido) {
                $contenedor.replaceWith(htmlRecibido);
                $(selectorCSS).css('opacity', '1');
            }).fail(function(xhr) {
                console.error("Error AJAX:", xhr.status);
                $boton.removeClass('disabled');
                $contenedor.css('opacity', '1');
            });
        }
    });
});
