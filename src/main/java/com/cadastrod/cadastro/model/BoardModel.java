package com.cadastrod.cadastro.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity @Table(name = "tb_kanban_board")
@Data @NoArgsConstructor @AllArgsConstructor
public class BoardModel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private UserModel owner; // dono do quadro

    @ManyToMany
    @JoinTable(name = "tb_board_members",
            joinColumns = @JoinColumn(name = "board_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<UserModel> membros = new HashSet<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("posicao ASC") // ordena colunas pela posição
    private Set<ColunaModel> colunas = new HashSet<>();

    @CreationTimestamp private Instant createdAt;
    @UpdateTimestamp private Instant updatedAt;
}
