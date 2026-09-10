package com.projeto.egoodapp.data.model;

import com.google.firebase.Timestamp;
import java.util.List;

public class User {
    private String nome; //
    private String email; //[cite: 1]
    private String tipoUsuario; // "pessoa" | "concessionaria"[cite: 1]
    private String telefone; //[cite: 1]
    private String cidade; //[cite: 1]
    private String estado; //[cite: 1]
    private Timestamp dataCriacao; //[cite: 1]

    // Específicos de Concessionária[cite: 1]
    private String cnpj; //[cite: 1]
    private String endereco; //[cite: 1]
    private String cep; //[cite: 1]
    private String descricao; //[cite: 1]
    private Double avaliacao; //[cite: 1]
    private Integer totalVendas; //[cite: 1]
    private List<String> certificacoes; //[cite: 1]

    public User() {} // Exigência do Firestore

    // Lembre-se de gerar os Getters e Setters novamente!
    // (Alt + Insert no teclado -> Getter and Setter)
}