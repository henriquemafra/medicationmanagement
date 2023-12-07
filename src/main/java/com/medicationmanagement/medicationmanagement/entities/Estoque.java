/**
 * Classe que representa a entidade Estoque.
 * Esta classe contém informações sobre o estoque de uma farmácia, como o CNPJ da farmácia,
 * o número de registro do estoque, a quantidade de medicamentos disponíveis, a data de atualização
 * e a referência para a farmácia correspondente.
 */
package com.medicationmanagement.medicationmanagement.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "ESTOQUES")
@IdClass(IdEstoque.class)
public class Estoque {

    @Id
    private Long cnpj;

    @Id
    private Integer nroRegistro;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(name = "data_atualizacao", nullable = false)
    @UpdateTimestamp
    private LocalDateTime dataAtualizacao;

    @ManyToOne
    @JoinColumn(name = "cnpj", referencedColumnName = "CNPJ", insertable = false, updatable = false)
    private Farmacia farmacia;

    public Estoque(Long cnpj, Integer nroRegistro, Integer quantidade, LocalDateTime dataAtualizacao) {
        this.cnpj = cnpj;
        this.nroRegistro = nroRegistro;
        this.quantidade = quantidade;
        this.dataAtualizacao = dataAtualizacao;
    }

    public Estoque() {
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

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Farmacia getFarmacia() {
        return farmacia;
    }

    public void setFarmacia(Farmacia farmacia) {
        this.farmacia = farmacia;
    }

    public Estoque get(int i) {
        return null;
    }
}