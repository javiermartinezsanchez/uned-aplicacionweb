package es.alumno.uned.dto;

import java.time.LocalDate;

public class AccesoPorDia {

	private final LocalDate dia;
    private final Long cantidad;

    // El constructor debe coincidir exactamente con el orden de la Query
    public AccesoPorDia(LocalDate dia, Long cantidad) {
        this.dia = dia;
        this.cantidad = cantidad;
    }

    // Getters estándar (Obligatorios para que Thymeleaf los lea)
    public LocalDate getDia() { return dia; }
    public Long getCantidad() { return cantidad; }
}
