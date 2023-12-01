package com.medicationmanagement.medicationmanagement.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.medicationmanagement.medicationmanagement.entities.Medicamento;
import com.medicationmanagement.medicationmanagement.repositories.MedicamentoRepository;

import java.util.List;

@RestController
@RequestMapping("/medicamentos")
public class MedicamentoController {

    private final MedicamentoRepository medicamentoRepository;

    public MedicamentoController(MedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    @GetMapping
    public ResponseEntity<List<Medicamento>> consultarMedicamentos() {
        List<Medicamento> medicamentos = medicamentoRepository.findAll();
        return ResponseEntity.ok(medicamentos);
    }

    @PostMapping
    public ResponseEntity<?> incluirMedicamento(@RequestBody Medicamento novoMedicamento) {
        // Verifica se o número de registro já está cadastrado
        Medicamento medicamentoExistente = medicamentoRepository.findByNroRegistro(novoMedicamento.getNroRegistro());
        if (medicamentoExistente != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Já existe um medicamento cadastrado com o Número de Registro informado.");
        }

        // Se tudo estiver válido, salva o novo medicamento
        Medicamento medicamentoSalvo = medicamentoRepository.save(novoMedicamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(medicamentoSalvo);
    }

}
