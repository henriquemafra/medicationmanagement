package com.medicationmanagement.medicationmanagement.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medicationmanagement.medicationmanagement.entities.Farmacia;
import com.medicationmanagement.medicationmanagement.repositories.FarmaciaRepository;

import java.util.List;

@RestController
@RequestMapping("/farmacias")
public class FarmaciaController {

    private final FarmaciaRepository farmaciaRepository;

    public FarmaciaController(FarmaciaRepository farmaciaRepository) {
        this.farmaciaRepository = farmaciaRepository;
    }

    @PostMapping
    public ResponseEntity<?> incluirFarmacia(@RequestBody Farmacia novaFarmacia) {
        // Verifica se o CNPJ já está cadastrado
        Farmacia farmaciaExistente = farmaciaRepository.findByCnpj(novaFarmacia.getCnpj());
        if (farmaciaExistente != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Já existe uma farmácia cadastrada com o CNPJ informado.");
        }

        Farmacia farmaciaSalva = farmaciaRepository.save(novaFarmacia);
        return ResponseEntity.status(HttpStatus.CREATED).body(farmaciaSalva);
    }

    @GetMapping("/{cnpj}")
    public ResponseEntity<?> obterFarmaciaPorCNPJ(@PathVariable Long cnpj) {
        Farmacia farmacia = farmaciaRepository.findByCnpj(cnpj);

        if (farmacia != null) {
            return ResponseEntity.ok(farmacia);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Farmácia com CNPJ " + cnpj + " não encontrada.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Farmacia>> obterTodasFarmacias() {
        List<Farmacia> farmacias = farmaciaRepository.findAll();
        return ResponseEntity.ok(farmacias);
    }
}
