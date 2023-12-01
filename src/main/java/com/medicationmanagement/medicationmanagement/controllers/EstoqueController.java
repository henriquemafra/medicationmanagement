package com.medicationmanagement.medicationmanagement.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.medicationmanagement.medicationmanagement.entities.Estoque;
import com.medicationmanagement.medicationmanagement.services.EstoqueService;

import java.util.List;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    private final EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @GetMapping("/{cnpj}")
    public ResponseEntity<List<Estoque>> consultarEstoqueFarmacia(@PathVariable Long cnpj) {
        List<Estoque> estoque = estoqueService.findByCnpj(cnpj);
        return ResponseEntity.ok(estoque);
    }

    @PostMapping
    public ResponseEntity<?> adicionarMedicamentoEstoque(@RequestBody Estoque estoque) {
        try {
            Estoque estoqueAtualizado = estoqueService.adicionarMedicamentoEstoque(estoque);
            return ResponseEntity.status(HttpStatus.OK).body(estoqueAtualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> venderMedicamentoEstoque(@RequestBody Estoque estoque) {
        try {
            Estoque estoqueAtualizado = estoqueService.venderMedicamentoEstoque(estoque);
            return ResponseEntity.status(HttpStatus.OK).body(estoqueAtualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> trocarMedicamentoEstoque(@RequestBody Estoque trocaRequest) {
        try {
            Estoque trocaResponse = estoqueService.trocarMedicamentoEstoque(trocaRequest);
            return ResponseEntity.status(HttpStatus.OK).body(trocaResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
