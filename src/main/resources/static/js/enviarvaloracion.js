document.addEventListener("DOMContentLoaded", () => {
    const stars = document.querySelectorAll(".interaction-stars i");
    const container = document.querySelector(".interaction-stars");
    const feedbackDiv = document.getElementById("ratingFeedback");
    const ratingLabel = document.querySelector(".rating-label");

    const csrfElement = document.querySelector('meta[name="_csrf"]');
    const csrfHeaderElement = document.querySelector('meta[name="_csrf_header"]');
    const csrfToken = csrfElement ? csrfElement.content : "";
    const csrfHeaderName = csrfHeaderElement ? csrfHeaderElement.content : "";

    stars.forEach(star => {
        star.addEventListener("mouseover", function() {
            const val = parseInt(this.getAttribute("data-value"));
            stars.forEach((s, idx) => {
                s.classList.toggle("bi-star-fill", idx < val);
                s.classList.toggle("bi-star", idx >= val);
            });
        });

        star.addEventListener("click", function() {
            const valoracion = this.getAttribute("data-value");
            const idElemento = container.getAttribute("data-id-curso");

            this.style.transform = 'scale(1.2)';
            setTimeout(() => this.style.transform = 'scale(1)', 200);

            const headers = { 'Content-Type': 'application/json' };
            if (csrfHeaderName && csrfToken) { headers[csrfHeaderName] = csrfToken; }

			const contextPath = document.querySelector('meta[name="context-path"]').getAttribute('content');


			const urlFinal = `${contextPath}valoracionCurso`.replace('//', '/');
			
            fetch(urlFinal, {
                method: 'POST',
                headers: headers,
                body: JSON.stringify({
                    idElemento: parseInt(idElemento),
                    valoracion: parseInt(valoracion)
                })
            })
            .then(response => {
                if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
                return response.json();
            })
            .then(data => {
				if (data.success) {
				    // 1. Actualizar nota media
				    const avgRatingText = document.querySelector(".avg-rating-text");
				    if (avgRatingText && data.nuevaPromedio !== undefined) {
						const nuevoValor = Number(data.nuevaPromedio);
				        avgRatingText.textContent = Number(data.nuevaPromedio).toFixed(1);
						const contenedorEstrellas = avgRatingText.parentElement.querySelector(".text-warning");
						    
						    if (contenedorEstrellas) {
						        const estrellas = contenedorEstrellas.querySelectorAll("i");
						        
						        estrellas.forEach((estrella, index) => {
						            const posicion = index + 1;
						            
						            estrella.className = "bi"; 
						            
						            if (posicion <= nuevoValor) {
						                estrella.classList.add("bi-star-fill"); // Llena
						            } else if (posicion - 0.5 <= nuevoValor) {
						                estrella.classList.add("bi-star-half"); // Media estrella
						            } else {
						                estrella.classList.add("bi-star");      // Vacía
						            }
						        });
						    }
				    }

				    // 2. Control visual del mensaje sin mover estrellas
				    if (feedbackDiv) {
				        feedbackDiv.textContent = data.mensaje;
				        feedbackDiv.className = "badge bg-success px-3 py-2 rounded-pill";
				        
				        if (ratingLabel) ratingLabel.classList.add("d-none"); 
				        feedbackDiv.classList.remove("d-none"); 

				        setTimeout(() => {
				            feedbackDiv.classList.add("d-none");
				            if (ratingLabel) ratingLabel.classList.remove("d-none"); 
				        }, 2000);
				    }

				    if (container) container.style.pointerEvents = "none";
				}
				 else {
                    mostrarError(data.mensaje);
                }
            })
            .catch(error => {
                console.error('Error:', error);
                mostrarError('No se pudo guardar tu valoración.');
            });
        });
    });

    // Función auxiliar para pintar errores en rojo
    function mostrarError(msg) {
        if (feedbackDiv) {
            feedbackDiv.textContent = msg;
            feedbackDiv.className = "badge bg-danger px-3 py-2 rounded-pill"; 
            if (ratingLabel) ratingLabel.classList.add("d-none");
            feedbackDiv.classList.remove("d-none");
            setTimeout(() => {
                feedbackDiv.classList.add("d-none");
                if (ratingLabel) ratingLabel.classList.remove("d-none");
            }, 2000);
        }
    }

    container?.addEventListener("mouseleave", () => {
        stars.forEach(s => {
            s.classList.remove("bi-star-fill");
            s.classList.add("bi-star");
        });
    });
});
