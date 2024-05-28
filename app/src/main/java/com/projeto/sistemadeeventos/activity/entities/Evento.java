package com.projeto.sistemadeeventos.activity.entities;

import java.io.Serializable;

public class Evento implements Serializable {

    private String idAdmin;
    private String nome;
    private String data;
    private String horario;
    private String price;
    private String local;

    public Evento(){
    }

    public Evento(String idAdmin, String nome, String data, String horario, String price, String local) {
        this.idAdmin = idAdmin;
        this.nome = nome;
        this.data = data;
        this.horario = horario;
        this.price = price;
        this.local = local;
    }

    public String getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(String idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
}
