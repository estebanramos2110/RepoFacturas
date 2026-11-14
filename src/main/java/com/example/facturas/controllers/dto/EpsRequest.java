package com.example.facturas.controllers.dto;

import jakarta.validation.constraints.NotBlank;

public record EpsRequest(
        @NotBlank String nombre,
        @NotBlank String nit) {}