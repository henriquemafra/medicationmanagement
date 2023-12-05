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

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    private final EstoqueRepository estoqueRepository;

    public EstoqueController(EstoqueRepository estoqueRepository) {
        this.estoqueRepository = estoqueRepository;
    }

    @GetMapping()
    public ResponseEntity<List<Estoque>> consultarTodoEstoque() {
        List<Estoque> estoque = estoqueRepository.findAll();
        if (estoque.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(estoque);
    }

    @PutMapping()
    public ResponseEntity<?> trocarMedicamentosEntreEstoques(@RequestBody TrocaMedicamentosRequest request) {
        // Validar os campos obrigatórios
        if (request.getCnpjOrigem() == null || request.getCnpjDestino() == null ||
                request.getNroRegistro() == null || request.getQuantidade() == null) {
            return ResponseEntity.badRequest().body("Todos os campos são obrigatórios.");
        }

        // Validar se CNPJs e Números de Registro existem no sistema
        List<Estoque> estoqueOrigem = estoqueRepository.findByCnpjAndNroRegistro(
                request.getCnpjOrigem(), request.getNroRegistro());
        List<Estoque> estoqueDestino = estoqueRepository.findByCnpjAndNroRegistro(
                request.getCnpjDestino(), request.getNroRegistro());

        if (estoqueOrigem.isEmpty() || estoqueDestino.isEmpty()) {
            return ResponseEntity.badRequest().body("CNPJ ou Número de Registro não encontrados.");
        }

        // Validar se a quantidade é um número positivo maior que zero
        if (request.getQuantidade() <= 0) {
            return ResponseEntity.badRequest().body("A quantidade deve ser um número positivo maior que zero.");
        }

        // Realizar a troca de medicamentos entre os estoques
        Estoque origem = estoqueOrigem.get(0);
        Estoque destino = estoqueDestino.get(0);

        int novaQuantidadeOrigem = origem.getQuantidade() - request.getQuantidade();
        int novaQuantidadeDestino = destino.getQuantidade() + request.getQuantidade();

        // Verificar se não haverá estoques negativos após a troca
        if (novaQuantidadeOrigem < 0 || novaQuantidadeDestino < 0) {
            return ResponseEntity.badRequest()
                    .body("Você não possuí esta quantidade disponível em estoque. Quantidade disponível: "
                            + origem.getQuantidade() + ".");
        }

        // Atualizar os estoques
        origem.setQuantidade(novaQuantidadeOrigem);
        destino.setQuantidade(novaQuantidadeDestino);

        // Atualizar a data de atualização
        origem.setDataAtualizacao(LocalDateTime.now());
        destino.setDataAtualizacao(LocalDateTime.now());

        // Salvar as alterações no estoque
        estoqueRepository.save(origem);
        estoqueRepository.save(destino);

        // Verificar e excluir registros de estoque zerados
        if (novaQuantidadeOrigem <= 0) {
            estoqueRepository.delete(origem);
        }
        if (novaQuantidadeDestino <= 0) {
            estoqueRepository.delete(destino);
        }

        // Responder com os dados atualizados dos estoques
        TrocaMedicamentosResponse response = new TrocaMedicamentosResponse(
                origem.getNroRegistro(), origem.getCnpj(), novaQuantidadeOrigem,
                destino.getCnpj(), novaQuantidadeDestino);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping()
    public ResponseEntity<?> removerDoEstoque(@RequestBody Estoque request) {
        // Verificar se todos os campos obrigatórios foram informados na requisição
        if (request.getCnpj() == null || request.getNroRegistro() == null || request.getQuantidade() == null) {
            return ResponseEntity.badRequest().body("Todos os campos são obrigatórios");
        }

        // Verificar se a quantidade é um número positivo maior que zero
        if (request.getQuantidade() <= 0) {
            return ResponseEntity.badRequest().body("A quantidade deve ser um número positivo maior que zero");
        }

        // Verificar se o CNPJ e Nro de Registro estão cadastrados no sistema
        List<Estoque> estoqueExistente = estoqueRepository.findByCnpjAndNroRegistro(
                request.getCnpj(), request.getNroRegistro());

        if (estoqueExistente.isEmpty()) {
            return ResponseEntity.badRequest().body("CNPJ ou Nro de Registro não cadastrados no sistema");
        }

        // Realizar a operação de remoção/subtração da quantidade do estoque
        Estoque estoqueAtual = estoqueExistente.get(0);
        int novaQuantidade = estoqueAtual.getQuantidade() - request.getQuantidade();

        // Verificar se a quantidade após a remoção é menor que zero
        if (novaQuantidade < 0) {
            return ResponseEntity.badRequest().body("A quantidade a ser removida excede o estoque disponível");
        }

        // Atualizar a quantidade no estoque
        estoqueAtual.setQuantidade(novaQuantidade);
        estoqueAtual.setDataAtualizacao(LocalDateTime.now());

        // Verificar se a quantidade ficou zero após a atualização
        if (novaQuantidade == 0) {
            estoqueRepository.delete(estoqueAtual);
            return ResponseEntity.ok(estoqueAtual);
        }

        // Se a quantidade não for zero, salvar as alterações no estoque
        Estoque estoqueSalvo = estoqueRepository.save(estoqueAtual);

        // Retornar os dados atualizados do estoque
        return ResponseEntity.ok(estoqueSalvo);
    }

    @PostMapping()
    public ResponseEntity<?> adicionarAoEstoque(@RequestBody Estoque novoEstoque) {
        // Verifica se todos os campos obrigatórios foram informados na requisição
        if (novoEstoque.getCnpj() == null || novoEstoque.getNroRegistro() == null
                || novoEstoque.getQuantidade() == null) {
            return ResponseEntity.badRequest().body("Todos os campos são obrigatórios");
        }

        // Verifica se a quantidade é um número positivo maior que zero
        if (novoEstoque.getQuantidade() <= 0) {
            return ResponseEntity.badRequest().body("A quantidade deve ser um número positivo maior que zero");
        }

        // Verifica se o CNPJ existe no estoque
        List<Estoque> estoquePorCNPJ = estoqueRepository.findByCnpj(novoEstoque.getCnpj());
        if (estoquePorCNPJ.isEmpty()) {
            return ResponseEntity.badRequest().body("CNPJ não existe no sistema");
        }

        // Verifica se o número de registro existe no estoque para o CNPJ informado
        List<Estoque> estoqueExistente = estoqueRepository.findByCnpjAndNroRegistro(
                novoEstoque.getCnpj(), novoEstoque.getNroRegistro());

        if (estoqueExistente.isEmpty()) {
            return ResponseEntity.badRequest().body("Número de Registro não existe para este CNPJ no sistema");
        } else {
            // Se existir, atualiza a quantidade original somando a quantidade adquirida
            Estoque estoqueAtual = estoqueExistente.get(0);
            int novaQuantidade = estoqueAtual.getQuantidade() + novoEstoque.getQuantidade();
            estoqueAtual.setQuantidade(novaQuantidade);
            estoqueAtual.setDataAtualizacao(LocalDateTime.now());
            Estoque estoqueSalvo = estoqueRepository.save(estoqueAtual);
            return ResponseEntity.ok(estoqueSalvo);
        }
    }

    @GetMapping("/{cnpj}")
    public ResponseEntity<List<Estoque>> consultarEstoquePorCNPJ(@PathVariable Long cnpj) {
        List<Estoque> estoque = estoqueRepository.findByCnpj(cnpj);
        if (estoque.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(estoque);
    }
}
