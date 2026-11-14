package com.example.facturas.persistence.repositories;

import com.example.facturas.persistence.entities.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PacienteJpaRepository extends JpaRepository<Paciente, Integer> {
    boolean existsByDocumento(String documento);
    List<Paciente> findByEpsId(Integer epsId);
    boolean existsByDocumentoAndIdNot(String documento, Integer id);
    Optional<Paciente> findByDocumento(String documento);
}