package com.example.facturas.controllers.dto;

import java.time.LocalDate;

public record PacienteResponse(
        Integer id,
        String documento,
        String nombre,
        String telefono,
        LocalDate fechaNacimiento,
        EpsResponse eps) {}