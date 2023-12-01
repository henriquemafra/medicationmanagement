package com.medicationmanagement.medicationmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medicationmanagement.medicationmanagement.entities.Farmacia;

@Repository
public interface FarmaciaRepository extends JpaRepository<Farmacia, Long> {

    Farmacia findByCnpj(Long cnpj);
}
