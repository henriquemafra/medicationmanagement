/**
 * Classe do controlador para a entidade Medicamento.
 * Responsável por lidar com as requisições relacionadas a medicamentos.
 */
package com.medicationmanagement.medicationmanagement.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.medicationmanagement.medicationmanagement.entities.Medicamento;
import com.medicationmanagement.medicationmanagement.repositories.MedicamentoRepository;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/medicamentos")
public class MedicamentoController {
    private final MedicamentoRepository medicamentoRepository;

    /**
     * Construtor da classe MedicamentoController.
     * @param medicamentoRepository Repositório de Medicamento.
     */
    public MedicamentoController(MedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    /**
     * Método para consultar todos os medicamentos.
     * @return ResponseEntity contendo a lista de medicamentos encontrados.
     */
    @GetMapping
    public ResponseEntity<List<Medicamento>> consultarMedicamentos() {
        List<Medicamento> medicamentos = medicamentoRepository.findAll();
        return ResponseEntity.ok(medicamentos);
    }

    /**
     * Método para incluir um novo medicamento.
     * @param novoMedicamento Medicamento a ser incluído.
     * @return ResponseEntity contendo o medicamento incluído ou uma mensagem de erro.
     */
    @PostMapping
    public ResponseEntity<?> incluirMedicamento(@RequestBody Medicamento novoMedicamento) {
        Optional<Medicamento> medicamentoExistente = medicamentoRepository.findByNroRegistro(novoMedicamento.getNroRegistro());
        if (medicamentoExistente != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Já existe um medicamento cadastrado com o Número de Registro informado.");
        }
        Medicamento medicamentoSalvo = medicamentoRepository.save(novoMedicamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(medicamentoSalvo);
    }
}