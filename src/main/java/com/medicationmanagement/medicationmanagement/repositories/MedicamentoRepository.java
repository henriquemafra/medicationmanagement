package com.medicationmanagement.medicationmanagement.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medicationmanagement.medicationmanagement.entities.Medicamento;

@Repository
public interface MedicamentoRepository extends JpaRepository<Medicamento, Integer> {

    Optional<Medicamento> findByNroRegistro(Integer nroRegistro);
}
