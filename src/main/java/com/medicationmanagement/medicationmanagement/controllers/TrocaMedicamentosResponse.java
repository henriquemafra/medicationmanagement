package com.medicationmanagement.medicationmanagement.controllers;

// Classe TrocaMedicamentosResponse
public class TrocaMedicamentosResponse {
    private Integer nroRegistro;
    private Long cnpjOrigem;
    private Integer quantidadeOrigem;
    private Long cnpjDestino;
    private Integer quantidadeDestino;

    // Construtor
    public TrocaMedicamentosResponse(Integer nroRegistro, Long cnpjOrigem, Integer quantidadeOrigem,
            Long cnpjDestino, Integer quantidadeDestino) {
        this.nroRegistro = nroRegistro;
        this.cnpjOrigem = cnpjOrigem;
        this.quantidadeOrigem = quantidadeOrigem;
        this.cnpjDestino = cnpjDestino;
        this.quantidadeDestino = quantidadeDestino;
    }

    // Getters e Setters

    public Integer getNroRegistro() {
        return nroRegistro;
    }

    public void setNroRegistro(Integer nroRegistro) {
        this.nroRegistro = nroRegistro;
    }

    public Long getCnpjOrigem() {
        return cnpjOrigem;
    }

    public void setCnpjOrigem(Long cnpjOrigem) {
        this.cnpjOrigem = cnpjOrigem;
    }

    public Integer getQuantidadeOrigem() {
        return quantidadeOrigem;
    }

    public void setQuantidadeOrigem(Integer quantidadeOrigem) {
        this.quantidadeOrigem = quantidadeOrigem;
    }

    public Long getCnpjDestino() {
        return cnpjDestino;
    }

    public void setCnpjDestino(Long cnpjDestino) {
        this.cnpjDestino = cnpjDestino;
    }

    public Integer getQuantidadeDestino() {
        return quantidadeDestino;
    }

    public void setQuantidadeDestino(Integer quantidadeDestino) {
        this.quantidadeDestino = quantidadeDestino;
    }
}
