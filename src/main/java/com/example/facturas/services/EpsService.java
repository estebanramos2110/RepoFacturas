package com.example.facturas.services;

import com.example.facturas.controllers.dto.EpsRequest;
import com.example.facturas.controllers.dto.EpsResponse;

import java.util.List;

public interface EpsService {
    EpsResponse save(EpsRequest request);
    List<EpsResponse> findAll();
}