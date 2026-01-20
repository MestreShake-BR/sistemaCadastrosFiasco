package com.cadastrod.cadastro.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "tb_cadastro_de_servicos")
public class ServicosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String servico;
    private String descricao;
    private String prioridade;
    //private int id_user;

    // um serviço pode ter vários usuários
    //@OneToMany(mappedBy = "servicosModel")
    //private List<UserModel> users;
}
