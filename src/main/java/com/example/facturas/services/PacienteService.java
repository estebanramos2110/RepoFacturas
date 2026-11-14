package com.example.facturas.services;

import com.example.facturas.controllers.dto.PacienteRequest;
import com.example.facturas.controllers.dto.PacienteResponse;

import java.util.List;

public interface PacienteService {
    PacienteResponse save(PacienteRequest request);
    PacienteResponse findById(Integer id);
    List<PacienteResponse> findAll();
    List<PacienteResponse> findByEps(Integer epsId);
    PacienteResponse update(Integer id, PacienteRequest request);
    PacienteResponse findByDocumento(String cedula);

    void delete (Integer id);
}