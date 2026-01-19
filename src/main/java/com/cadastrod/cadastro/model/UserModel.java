package com.cadastrod.cadastro.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

// Entity ele transforma uma classe em uam entidade do banco de dados

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_cadastro_de_usuarios")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String email;
    private String telefone;

    // Varios serviços pode ter um usuário
    @ManyToOne
    @JoinColumn(name =  "id_servico") //FK
    private ServicosModel servicosModel;
}
