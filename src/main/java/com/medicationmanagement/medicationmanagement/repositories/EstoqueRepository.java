package com.medicationmanagement.medicationmanagement.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medicationmanagement.medicationmanagement.entities.Estoque;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

    List<Estoque> findByCnpj(Long cnpj);

    List<Estoque> findByCnpjAndNroRegistro(Long cnpj, Integer nroRegistro);
  
}
