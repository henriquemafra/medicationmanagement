package com.medicationmanagement.medicationmanagement.entities;

import java.io.Serializable;

public class IdEstoque implements Serializable {
    private Long cnpj;
    private Integer nroRegistro;

    // Getters e Setters
    
    public Long getCnpj() {
        return cnpj;
    }
    public void setCnpj(Long cnpj) {
        this.cnpj = cnpj;
    }
    public Integer getNroRegistro() {
        return nroRegistro;
    }
    public void setNroRegistro(Integer nroRegistro) {
        this.nroRegistro = nroRegistro;
    }

}
