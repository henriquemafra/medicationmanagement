package com.medicationmanagement.medicationmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medicationmanagement.medicationmanagement.entities.Estoque;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

    Estoque findByCnpjAndNroRegistro(Long cnpj, Integer nroRegistro);

    Estoque findByCnpj(Long cnpj);

}
