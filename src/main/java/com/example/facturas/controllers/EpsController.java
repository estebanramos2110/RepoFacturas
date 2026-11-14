package com.example.facturas.controllers;

import com.example.facturas.controllers.dto.EpsRequest;
import com.example.facturas.controllers.dto.EpsResponse;
import com.example.facturas.services.EpsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/eps")
@RequiredArgsConstructor
@Tag(name = "eps", description = "Administración de EPS")
public class EpsController {

    private final EpsService epsService;

    @PostMapping
    @Operation(summary = "Crear EPS", description = "Registra una nueva EPS. El NIT no puede repetirse.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "EPS creada"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "409", description = "Ya existe una EPS con ese NIT")
    })
    public ResponseEntity<EpsResponse> save(@Valid @RequestBody EpsRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(epsService.save(request));
    }

    @GetMapping
    @Operation(summary = "Listar EPS", description = "Devuelve todas las EPS registradas")
    @ApiResponse(responseCode = "200", description = "Listado de EPS")
    public ResponseEntity<List<EpsResponse>> findAll() {
        return ResponseEntity.ok(epsService.findAll());
    }
}