package com.projeto.egoodapp.data.model;

import com.google.firebase.Timestamp;
import java.util.List;

public class Vehicle {
    private String concessionariaId; // null para catálogo geral[cite: 1]
    private String marca;
    private String modelo;
    private int ano;
    private long preco;
    private int km;
    private double bateriaKwh;
    private int autonomiaKm;
    private double consumoKwh;
    private int potenciaCv;
    private String especificacaoCarga;
    private String categoria;
    private String status; // "disponivel", "reservado", "vendido"
    private List<String> urlsImagens;
    private Timestamp dataCriacao;

    public Vehicle() {} // Exigência do Firestore

    // Lembre-se de gerar os Getters e Setters para esta classe também!
}