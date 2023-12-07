/**
 * Este arquivo contém o código para o controlador de Estoque.
 * O controlador é responsável por lidar com as requisições relacionadas ao estoque de medicamentos.
 */
package com.medicationmanagement.medicationmanagement.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.medicationmanagement.medicationmanagement.entities.Estoque;
import com.medicationmanagement.medicationmanagement.repositories.EstoqueRepository;
import com.medicationmanagement.medicationmanagement.repositories.MedicamentoRepository;
import com.medicationmanagement.medicationmanagement.entities.Medicamento;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {
    // Repositório de Estoque
    private final EstoqueRepository estoqueRepository;
    private final MedicamentoRepository medicamentoRepository;

    // Construtor do Controlador de Estoque
    public EstoqueController(EstoqueRepository estoqueRepository, MedicamentoRepository medicamentoRepository) {
        this.estoqueRepository = estoqueRepository;
        this.medicamentoRepository = medicamentoRepository;
    }

    // Consultar todo o estoque
    @GetMapping()
    public ResponseEntity<List<Estoque>> consultarTodoEstoque() {
        List<Estoque> estoque = estoqueRepository.findAll();
        if (estoque.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(estoque);
    }

    // Trocar medicamentos entre estoques
    @PutMapping()
public ResponseEntity<?> trocarMedicamentosEntreEstoques(@RequestBody TrocaMedicamentosRequest request) {
    // Verificar se todos os campos obrigatórios estão presentes
    if (request.getCnpjOrigem() == null || request.getCnpjDestino() == null ||
            request.getNroRegistro() == null || request.getQuantidade() == null) {
        return ResponseEntity.badRequest().body("Todos os campos são obrigatórios.");
    }

    // Obter estoque de origem e destino
    List<Estoque> estoqueOrigem = estoqueRepository.findByCnpjAndNroRegistro(
            request.getCnpjOrigem(), request.getNroRegistro());
    List<Estoque> estoqueDestino = estoqueRepository.findByCnpjAndNroRegistro(
            request.getCnpjDestino(), request.getNroRegistro());

    // Verificar se o estoque de origem e destino existem
    if (estoqueOrigem.isEmpty() || estoqueDestino.isEmpty()) {
        return ResponseEntity.badRequest().body("CNPJ ou Número de Registro não encontrados.");
    }

    // Verificar se a quantidade é válida
    if (request.getQuantidade() <= 0) {
        return ResponseEntity.badRequest().body("A quantidade deve ser um número positivo maior que zero.");
    }

    // Obter os estoques de origem e destino
    Estoque origem = estoqueOrigem.get(0);
    Estoque destino = estoqueDestino.get(0);

    // Calcular a nova quantidade de medicamentos nos estoques
    int novaQuantidadeOrigem = origem.getQuantidade() - request.getQuantidade();
    int novaQuantidadeDestino = destino.getQuantidade() + request.getQuantidade();

    // Verificar se a quantidade disponível é suficiente
    if (novaQuantidadeOrigem < 0 || novaQuantidadeDestino < 0) {
        return ResponseEntity.badRequest()
                .body("Você não possuí esta quantidade disponível em estoque. Quantidade disponível: "
                        + origem.getQuantidade() + ".");
    }

    // Atualizar as quantidades nos estoques
    origem.setQuantidade(novaQuantidadeOrigem);
    destino.setQuantidade(novaQuantidadeDestino);

    // Atualizar as datas de atualização
    origem.setDataAtualizacao(LocalDateTime.now());
    destino.setDataAtualizacao(LocalDateTime.now());

    // Salvar as alterações nos estoques
    estoqueRepository.save(origem);
    estoqueRepository.save(destino);

    // Verificar se os estoques devem ser removidos (opcional)
    if (novaQuantidadeOrigem <= 0) {
        estoqueRepository.delete(origem);
    }
    if (novaQuantidadeDestino <= 0) {
        estoqueRepository.delete(destino);
    }

    // Criar a resposta de troca de medicamentos
    TrocaMedicamentosResponse response = new TrocaMedicamentosResponse(
            origem.getNroRegistro(), origem.getCnpj(), novaQuantidadeOrigem,
            destino.getCnpj(), novaQuantidadeDestino);

    return ResponseEntity.ok(response);
}

    // Remover do estoque
    @DeleteMapping()
    public ResponseEntity<?> removerDoEstoque(@RequestBody Estoque request) {
        // Verificar se todos os campos obrigatórios estão presentes
        if (request.getCnpj() == null || request.getNroRegistro() == null || request.getQuantidade() == null) {
            return ResponseEntity.badRequest().body("Todos os campos são obrigatórios");
        }

        // Verificar se a quantidade é válida
        if (request.getQuantidade() <= 0) {
            return ResponseEntity.badRequest().body("A quantidade deve ser um número positivo maior que zero");
        }

        // Verificar se o estoque existe
        List<Estoque> estoqueExistente = estoqueRepository.findByCnpjAndNroRegistro(
                request.getCnpj(), request.getNroRegistro());
        if (estoqueExistente.isEmpty()) {
            return ResponseEntity.badRequest().body("CNPJ ou Nro de Registro não cadastrados no sistema");
        }

        // Obter o estoque atual
        Estoque estoqueAtual = estoqueExistente.get(0);

        // Calcular a nova quantidade de medicamentos no estoque
        int novaQuantidade = estoqueAtual.getQuantidade() - request.getQuantidade();

        // Verificar se a quantidade a ser removida excede o estoque disponível
        if (novaQuantidade < 0) {
            return ResponseEntity.badRequest().body("A quantidade a ser removida excede o estoque disponível");
        }

        // Atualizar a quantidade e a data de atualização
        estoqueAtual.setQuantidade(novaQuantidade);
        estoqueAtual.setDataAtualizacao(LocalDateTime.now());

        // Verificar se o estoque deve ser removido
        if (novaQuantidade == 0) {
            estoqueRepository.delete(estoqueAtual);
            return ResponseEntity.ok(estoqueAtual);
        }

        // Salvar as alterações no estoque
        Estoque estoqueSalvo = estoqueRepository.save(estoqueAtual);
        return ResponseEntity.ok(estoqueSalvo);
    }

    // Adicionar ao estoque
    @PostMapping()
    public ResponseEntity<?> adicionarAoEstoque(@RequestBody Estoque novoEstoque) {
        // Verificar se todos os campos obrigatórios estão presentes
        if (novoEstoque.getCnpj() == null || novoEstoque.getNroRegistro() == null
                || novoEstoque.getQuantidade() == null) {
            return ResponseEntity.badRequest().body("Todos os campos são obrigatórios");
        }

        // Verificar se a quantidade é válida
        if (novoEstoque.getQuantidade() <= 0) {
            return ResponseEntity.badRequest().body("A quantidade deve ser um número positivo maior que zero");
        }

        // Verificar se o CNPJ existe no sistema
        List<Estoque> estoquePorCNPJ = estoqueRepository.findByCnpj(novoEstoque.getCnpj());
        if (estoquePorCNPJ.isEmpty()) {
            return ResponseEntity.badRequest().body("CNPJ não existe no sistema");
        }

        // Verificar se o número de registro existe para o CNPJ no sistema
        List<Estoque> estoqueExistente = estoqueRepository.findByCnpjAndNroRegistro(
                novoEstoque.getCnpj(), novoEstoque.getNroRegistro());

        // Se o número de registro não existe, criar um novo estoque
        if (estoqueExistente.isEmpty()) {
            return ResponseEntity.badRequest().body("Número de Registro não existe para este CNPJ no sistema");
        } else {
            // Caso contrário, atualizar a quantidade do estoque existente
            Estoque estoqueAtual = estoqueExistente.get(0);
            int novaQuantidade = estoqueAtual.getQuantidade() + novoEstoque.getQuantidade();
            estoqueAtual.setQuantidade(novaQuantidade);
            estoqueAtual.setDataAtualizacao(LocalDateTime.now());
            Estoque estoqueSalvo = estoqueRepository.save(estoqueAtual);
            return ResponseEntity.ok(estoqueSalvo);
        }
    }

    // Consultar estoque por CNPJ
    @GetMapping("/{cnpj}")
    public ResponseEntity<List<Map<String, Object>>> consultarEstoquePorCNPJ(@PathVariable Long cnpj) {
        List<Estoque> estoque = estoqueRepository.findByCnpj(cnpj);
        if (estoque.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<Map<String, Object>> responseList = new ArrayList<>();
        for (Estoque item : estoque) {
            Map<String, Object> responseItem = new HashMap<>();
            responseItem.put("nroRegistro", item.getNroRegistro());

            Medicamento medicamento = encontrarMedicamentoPorNroRegistro(item.getNroRegistro());
            if (medicamento != null) {
                responseItem.put("nome", medicamento.getNome());
            } else {
                responseItem.put("nome", "Nome do Medicamento Não Encontrado");
            }

            responseItem.put("quantidade", item.getQuantidade());
            responseItem.put("dataAtualizacao", item.getDataAtualizacao());
            responseList.add(responseItem);
        }

        return ResponseEntity.ok(responseList);
    }

    // Método para encontrar Medicamento por número de registro
    private Medicamento encontrarMedicamentoPorNroRegistro(Integer nroRegistro) {
        List<Medicamento> medicamentos = medicamentoRepository.findAll();

        for (Medicamento medicamento : medicamentos) {
            if (medicamento.getNroRegistro().equals(nroRegistro)) {
                return medicamento;
            }
        }

        return null;
    }
}