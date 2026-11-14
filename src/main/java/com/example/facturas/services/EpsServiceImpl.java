package com.example.facturas.services;

import com.example.facturas.controllers.dto.EpsRequest;
import com.example.facturas.controllers.dto.EpsResponse;
import com.example.facturas.persistence.entities.Eps;
import com.example.facturas.persistence.repositories.EpsJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EpsServiceImpl implements EpsService {

    private final EpsJpaRepository epsRepo;

    @Override
    @Transactional
    public EpsResponse save(EpsRequest request) {
        if (epsRepo.existsByNit(request.nit())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ya existe una EPS con NIT " + request.nit());
        }
        Eps eps = new Eps();
        eps.setNombre(request.nombre());
        eps.setNit(request.nit());
        return toResponse(epsRepo.save(eps));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EpsResponse> findAll() {
        return epsRepo.findAll().stream().map(this::toResponse).toList();
    }

    private EpsResponse toResponse(Eps e) {
        return new EpsResponse(e.getId(), e.getNombre(), e.getNit(), e.isActiva());
    }
}