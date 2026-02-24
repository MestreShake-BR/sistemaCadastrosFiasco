package com.cadastrod.cadastro.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor @NoArgsConstructor @Data
@Table(name = "tb_cadastro_de_servicos")
public class ServicosModel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) private String servico;
    @Column(columnDefinition = "TEXT") private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Prioridade prioridade = Prioridade.MEDIA;

    @Column(nullable = false) private Integer posicao = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coluna_id", nullable = false)
    private ColunaModel coluna;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private BoardModel board;

    @ManyToMany
    @JoinTable(name = "tb_servico_responsaveis",
            joinColumns = @JoinColumn(name = "servico_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<UserModel> responsaveis = new HashSet<>();

    private LocalDate dataLimite;
    private Boolean concluido = false;

    @CreationTimestamp private Instant createdAt;
    @UpdateTimestamp private Instant updatedAt;

    public enum Prioridade { BAIXA, MEDIA, ALTA, CRITICA }

    // Invariantes locais: mantém board/coluna coerentes e posição válida
    public void moverParaColuna(ColunaModel novaColuna) {
        if (novaColuna == null) throw new IllegalArgumentException("Coluna destino obrigatória");
        this.setColuna(novaColuna);
        this.setBoard(novaColuna.getBoard());
    }
    public void definirPosicao(int pos) {
        if (pos < 0) throw new IllegalArgumentException("Posição não pode ser negativa");
        this.setPosicao(pos);
    }
}
