package com.example.facturas.persistence.repositories;

import com.example.facturas.persistence.entities.Eps;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpsJpaRepository extends JpaRepository<Eps, Integer> {
    boolean existsByNit(String nit);

}