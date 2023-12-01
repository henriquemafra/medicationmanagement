package com.medicationmanagement.medicationmanagement.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.medicationmanagement.medicationmanagement.entities.Estoque;
import com.medicationmanagement.medicationmanagement.repositories.EstoqueRepository;

@Service
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;

    public EstoqueService(EstoqueRepository estoqueRepository) {
        this.estoqueRepository = estoqueRepository;
    }

    public Estoque consultarEstoquePorCnpj(Long cnpj) {
        return estoqueRepository.findByCnpj(cnpj);
    }

    public Estoque adicionarMedicamentoEstoque(Estoque estoque) {
        Estoque estoqueExistente = estoqueRepository.findByCnpjAndNroRegistro(estoque.getCnpj(), estoque.getNroRegistro());
        
        if (estoqueExistente != null) {
            int novaQuantidade = estoqueExistente.getQuantidade() + estoque.getQuantidade();
            estoqueExistente.setQuantidade(novaQuantidade);
            return estoqueRepository.save(estoqueExistente);
        } else {
            return estoqueRepository.save(estoque);
        }
    }

    public Estoque venderMedicamentoEstoque(Estoque estoque) {
        Estoque estoqueExistente = estoqueRepository.findByCnpjAndNroRegistro(estoque.getCnpj(), estoque.getNroRegistro());
        
        if (estoqueExistente != null && estoqueExistente.getQuantidade() >= estoque.getQuantidade()) {
            int novaQuantidade = estoqueExistente.getQuantidade() - estoque.getQuantidade();
            estoqueExistente.setQuantidade(novaQuantidade);
            return estoqueRepository.save(estoqueExistente);
        } else {
            throw new RuntimeException("Não há medicamento suficiente no estoque para a venda.");
        }
    }

    public Estoque trocarMedicamentoEstoque(Estoque trocaRequest) {
        Estoque estoqueOrigem = estoqueRepository.findByCnpjAndNroRegistro(trocaRequest.getCnpjOrigem(), trocaRequest.getNroRegistro());
        Estoque estoqueDestino = estoqueRepository.findByCnpjAndNroRegistro(trocaRequest.getCnpjDestino(), trocaRequest.getNroRegistro());
        
        if (estoqueOrigem != null && estoqueDestino != null && estoqueOrigem.getQuantidade() >= trocaRequest.getQuantidade()) {
            int novaQuantidadeOrigem = estoqueOrigem.getQuantidade() - trocaRequest.getQuantidade();
            estoqueOrigem.setQuantidade(novaQuantidadeOrigem);
            estoqueRepository.save(estoqueOrigem);

            int novaQuantidadeDestino = estoqueDestino.getQuantidade() + trocaRequest.getQuantidade();
            estoqueDestino.setQuantidade(novaQuantidadeDestino);
            return estoqueRepository.save(estoqueDestino);
        } else {
            throw new RuntimeException("Erro ao realizar a troca de medicamentos entre estoques.");
        }
    }

    public List<Estoque> findByCnpj(Long cnpj) {
        return null;
    }
}
