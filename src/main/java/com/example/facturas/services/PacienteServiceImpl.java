package com.example.facturas.services;

import com.example.facturas.controllers.dto.EpsResponse;
import com.example.facturas.controllers.dto.PacienteRequest;
import com.example.facturas.controllers.dto.PacienteResponse;
import com.example.facturas.persistence.entities.Eps;
import com.example.facturas.persistence.entities.Paciente;
import com.example.facturas.persistence.repositories.EpsJpaRepository;
import com.example.facturas.persistence.repositories.PacienteJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PacienteServiceImpl implements PacienteService {

    private final PacienteJpaRepository pacienteRepo;
    private final EpsJpaRepository epsRepo;
    private final PacienteService pacienteService;

    @Override
    @Transactional
    public PacienteResponse save(PacienteRequest request) {
        if (pacienteRepo.existsByDocumento(request.documento())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ya existe un paciente con documento " + request.documento());
        }
        Eps eps = epsRepo.findById(request.epsId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "La EPS con id " + request.epsId() + " no existe"));

        Paciente p = new Paciente();
        p.setDocumento(request.documento());
        p.setNombre(request.nombre());
        p.setTelefono(request.telefono());
        p.setFechaNacimiento(request.fechaNacimiento());
        p.setEps(eps);
        return toResponse(pacienteRepo.save(p));
    }


    @Override
    @Transactional
    public PacienteResponse update(Integer id, PacienteRequest request) {
        Paciente p = pacienteRepo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "El paciente con id " + id + " no existe"));

        if (pacienteRepo.existsByDocumentoAndIdNot(request.documento(), id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ya existe otro paciente con documento " + request.documento());
        }

        Eps eps = epsRepo.findById(request.epsId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "La EPS con id " + request.epsId() + " no existe"));

        p.setDocumento(request.documento());
        p.setNombre(request.nombre());
        p.setTelefono(request.telefono());
        p.setFechaNacimiento(request.fechaNacimiento());
        p.setEps(eps);

        return toResponse(pacienteRepo.save(p));
    }

    @Override
    @Transactional(readOnly = true)
    public PacienteResponse findById(Integer id) {
        return pacienteRepo.findById(id).map(this::toResponse).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "El paciente con id " + id + " no existe"));
    }
    @Override
    @Transactional(readOnly = true)
    public PacienteResponse findByDocumento(String documento) {
        return pacienteRepo.findByDocumento(documento)
                .map(this::toResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "El paciente con documento " + documento + " no existe"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PacienteResponse> findAll() {
        return pacienteRepo.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PacienteResponse> findByEps(Integer epsId) {
        return pacienteRepo.findByEpsId(epsId).stream().map(this::toResponse).toList();
    }

    @Override
    public void delete(Integer id) {
        if (!pacienteRepo.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Producto con id " + id + " no existe");
        }
        pacienteRepo.deleteById(id);
    }

    private PacienteResponse toResponse(Paciente p) {
        Eps e = p.getEps();
        return new PacienteResponse(p.getId(), p.getDocumento(), p.getNombre(),
                p.getTelefono(), p.getFechaNacimiento(),
                new EpsResponse(e.getId(), e.getNombre(), e.getNit(), e.isActiva()));
    }


}