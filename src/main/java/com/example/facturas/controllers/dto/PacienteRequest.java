package com.example.facturas.controllers.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PacienteRequest(
        @NotBlank String documento,
        @NotBlank String nombre,
        String telefono,
        LocalDate fechaNacimiento,
        @NotNull Integer epsId) {}