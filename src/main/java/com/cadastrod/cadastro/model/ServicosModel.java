package com.cadastrod.cadastro.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tb_cadastro_de_servicos")
public class ServicosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String servico;
    private String descricao;
    private String prioridade;
    private int id_user;

    // um serviço pode ter vários usuários
    @OneToMany(mappedBy = "servicosModel")
    private List<UserModel> users;

    public ServicosModel() {
    }

    public ServicosModel(int id, String servico, String descricao, String prioridade, int id_user) {
        this.id = id;
        this.servico = servico;
        this.descricao = descricao;
        this.prioridade = prioridade;
        this.id_user = id_user;
    }
}
