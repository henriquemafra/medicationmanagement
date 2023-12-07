/**
 * Classe que representa um medicamento.
 */
package com.medicationmanagement.medicationmanagement.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MEDICAMENTOS")
public class Medicamento {
    @Id
    private Integer nroRegistro;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String laboratorio;
    @Column(nullable = false)
    private String dosagem;
    private String descricao;
    @Column(nullable = false)
    private Float preco;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMedicamento tipo;

    /**
     * Enumeração que define os tipos de medicamento.
     */
    public enum TipoMedicamento {
        COMUM,
        CONTROLADO
    }

    public Medicamento() {
    }

    public Medicamento(Integer nroRegistro, String nome, String laboratorio, String dosagem,
            String descricao, Float preco, TipoMedicamento tipo) {
        this.nroRegistro = nroRegistro;
        this.nome = nome;
        this.laboratorio = laboratorio;
        this.dosagem = dosagem;
        this.descricao = descricao;
        this.preco = preco;
        this.tipo = tipo;
    }

    public Integer getNroRegistro() {
        return nroRegistro;
    }

    public void setNroRegistro(Integer nroRegistro) {
        this.nroRegistro = nroRegistro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public String getDosagem() {
        return dosagem;
    }

    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Float getPreco() {
        return preco;
    }

    public void setPreco(Float preco) {
        this.preco = preco;
    }

    public TipoMedicamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoMedicamento tipo) {
        this.tipo = tipo;
    }

}