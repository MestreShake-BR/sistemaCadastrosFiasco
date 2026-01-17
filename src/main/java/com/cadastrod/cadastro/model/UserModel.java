package com.cadastrod.cadastro.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// Entity ele transforma uma classe em uam entidade do banco de dados
@Getter
@Setter
@Entity
@Table(name = "tb_cadastro_de_usuarios")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String nome;
    String email;
    String telefone;

    public UserModel() {
    }

    public UserModel(String nome, String email, String telefone) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

   // public String getNome() {
     //   return nome;
    //}

    //public void setNome(String nome) {
     //   this.nome = nome;
   // }

   // public String getEmail() {
     //   return email;
    //}

    //public void setEmail(String email) {
      //  this.email = email;
   // }

    //public String getTelefone() {
      //  return telefone;
    //}

    //public void setTelefone(String telefone) {
      //  this.telefone = telefone;
    //}
}
