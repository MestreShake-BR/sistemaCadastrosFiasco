package com.cadastrod.cadastro.model;

import jakarta.persistence.*;
import lombok.*;

// Entity ele transforma uma classe em uam entidade do banco de dados

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_cadastro_de_usuarios")
public class UserModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String senha;
    private String role;

    // Varios serviços pode ter um usuário
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY) //FK
    @JoinColumn(name = "perfil_id", referencedColumnName = "id")
    private UserDadosModel userDadosModel;
}