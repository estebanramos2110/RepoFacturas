package com.example.facturas.controllers;

import com.example.facturas.controllers.dto.PacienteRequest;
import com.example.facturas.controllers.dto.PacienteResponse;
import com.example.facturas.services.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.media.Content;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pacientes")
@RequiredArgsConstructor
@Tag(name = "pacientes", description = "Administración de pacientes y su EPS")
public class PacienteController {

    private final PacienteService pacienteService;

    @PostMapping
    @Operation(summary = "Crear paciente", description = "Registra un paciente asociado a una EPS existente. El documento no puede repetirse.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Paciente creado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "La EPS no existe"),
            @ApiResponse(responseCode = "409", description = "Ya existe un paciente con ese documento")
    })
    public ResponseEntity<PacienteResponse> save(@Valid @RequestBody PacienteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteService.save(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar paciente por id", description = "Devuelve un paciente con su EPS")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paciente encontrado"),
            @ApiResponse(responseCode = "404", description = "El paciente no existe")
    })
    public ResponseEntity<PacienteResponse> findById(
            @Parameter(description = "Id del paciente", example = "1") @PathVariable Integer id) {
        return ResponseEntity.ok(pacienteService.findById(id));
    }

    @GetMapping
    @Operation(summary = "Listar pacientes", description = "Devuelve todos los pacientes con su EPS")
    @ApiResponse(responseCode = "200", description = "Listado de pacientes")
    public ResponseEntity<List<PacienteResponse>> findAll() {
        return ResponseEntity.ok(pacienteService.findAll());
    }

    @GetMapping("/eps/{epsId}")
    @Operation(summary = "Listar pacientes por EPS", description = "Devuelve los pacientes que pertenecen a una EPS")
    @ApiResponse(responseCode = "200", description = "Listado de pacientes de la EPS (vacío si no hay)")
    public ResponseEntity<List<PacienteResponse>> findByEps(
            @Parameter(description = "Id de la EPS", example = "1") @PathVariable Integer epsId) {
        return ResponseEntity.ok(pacienteService.findByEps(epsId));
    }

    @GetMapping("/documento/{documento}")
    @Operation(summary = "Buscar paciente por documento", description = "Devuelve un paciente según su número de documento")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paciente encontrado"),
            @ApiResponse(responseCode = "404", description = "No existe un paciente con ese documento", content = @Content)
    })
    public ResponseEntity<PacienteResponse> findByDocumento(
            @Parameter(description = "Número de documento", example = "1001234567") @PathVariable String documento) {
        return ResponseEntity.ok(pacienteService.findByDocumento(documento));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar paciente", description = "Elimina un paciente por su id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Paciente eliminado"),
            @ApiResponse(responseCode = "404", description = "El paciente no existe", content = @Content)
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "Id del paciente", example = "1") @PathVariable Integer id) {
        pacienteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar paciente", description = "Modifica los datos de un paciente existente, incluida su EPS")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paciente actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "El paciente o la EPS no existen", content = @Content),
            @ApiResponse(responseCode = "409", description = "Otro paciente ya tiene ese documento", content = @Content)
    })



    public ResponseEntity<PacienteResponse> update(
            @Parameter(description = "Id del paciente", example = "1") @PathVariable Integer id,
            @Valid @RequestBody PacienteRequest request) {
        return ResponseEntity.ok(pacienteService.update(id, request));
    }
}