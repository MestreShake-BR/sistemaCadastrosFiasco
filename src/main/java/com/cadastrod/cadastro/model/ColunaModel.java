package com.cadastrod.cadastro.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity @Table(name = "tb_kanban_coluna")
@Data @NoArgsConstructor @AllArgsConstructor
public class ColunaModel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) private String nome;

    @Column(nullable = false) private Integer posicao = 0; // posição da coluna no board

    private Integer wipLimit; // opcional

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private BoardModel board;

    @OneToMany(mappedBy = "coluna", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("posicao ASC") // ordena cartões pela posição
    private Set<ServicosModel> servicos = new LinkedHashSet<>();
}