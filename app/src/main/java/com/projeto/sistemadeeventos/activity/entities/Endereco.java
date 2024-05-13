package com.projeto.sistemadeeventos.activity.entities;

import java.io.Serializable;

public class Endereco implements Serializable {
    private String cidade;
    private String cep;
    private String estado;
    private String rua;
    private String numero;

    public Endereco()
    {
    }

    public Endereco(String cidade, String cep, String estado, String rua, String numero) {
        this.cidade = cidade;
        this.cep = cep;
        this.estado = estado;
        this.rua = rua;
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
