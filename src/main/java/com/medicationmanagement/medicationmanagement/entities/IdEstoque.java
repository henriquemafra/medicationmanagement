/**
 * Classe que representa a identificação de um estoque.
 * Contém os atributos CNPJ e número de registro.
 */
package com.medicationmanagement.medicationmanagement.entities;

import java.io.Serializable;
import java.util.Objects;

public class IdEstoque implements Serializable {
    private Long cnpj;
    private Integer nroRegistro;

    public IdEstoque() {}

    public IdEstoque(Long cnpj, Integer nroRegistro) {
        this.cnpj = cnpj;
        this.nroRegistro = nroRegistro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdEstoque idEstoque = (IdEstoque) o;
        return Objects.equals(cnpj, idEstoque.cnpj) && Objects.equals(nroRegistro, idEstoque.nroRegistro);
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(cnpj, nroRegistro);
    }
}