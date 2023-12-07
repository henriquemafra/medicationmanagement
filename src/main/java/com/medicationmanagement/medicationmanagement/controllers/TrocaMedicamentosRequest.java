/**
 * Classe que representa uma solicitação de troca de medicamentos.
 */
package com.medicationmanagement.medicationmanagement.controllers;

public class TrocaMedicamentosRequest {
    private Long cnpjOrigem;
    private Long cnpjDestino;
    private Integer nroRegistro;
    private Integer quantidade;

    public Long getCnpjOrigem() {
        return cnpjOrigem;
    }

    public void setCnpjOrigem(Long cnpjOrigem) {
        this.cnpjOrigem = cnpjOrigem;
    }

    public Long getCnpjDestino() {
        return cnpjDestino;
    }

    public void setCnpjDestino(Long cnpjDestino) {
        this.cnpjDestino = cnpjDestino;
    }

    public Integer getNroRegistro() {
        return nroRegistro;
    }

    public void setNroRegistro(Integer nroRegistro) {
        this.nroRegistro = nroRegistro;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}