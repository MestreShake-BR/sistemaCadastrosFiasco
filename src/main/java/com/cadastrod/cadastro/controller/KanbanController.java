package com.cadastrod.cadastro.controller;

import com.cadastrod.cadastro.model.ServicosModel;
import com.cadastrod.cadastro.model.ServicosModel.Prioridade;
import com.cadastrod.cadastro.service.KanbanService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/kanban")
@RequiredArgsConstructor
@Validated
public class KanbanController {

    private final KanbanService kanbanService;

    @PostMapping("/boards")
    public ResponseEntity<Map<String, Object>> criarBoard(@RequestBody @Valid CreateBoardRequest body) {
        var board = kanbanService.criarBoard(body.getNome(), body.getOwnerId());
        return ResponseEntity.created(URI.create("/api/kanban/boards/" + board.getId()))
                .body(Map.of("id", board.getId()));
    }

    @PostMapping("/boards/{boardId}/colunas")
    public ResponseEntity<Map<String, Object>> criarColuna(@PathVariable Long boardId,
                                                           @RequestBody @Valid CreateColunaRequest body) {
        var coluna = kanbanService.criarColuna(boardId, body.getNome(), body.getPosicao());
        return ResponseEntity.created(URI.create("/api/kanban/colunas/" + coluna.getId()))
                .body(Map.of("id", coluna.getId()));
    }

    @PostMapping("/colunas/{colunaId}/cards")
    public ResponseEntity<Map<String, Object>> criarCard(@PathVariable Long colunaId,
                                                         @RequestBody @Valid CreateCardRequest body) {
        LocalDate dataLimite = body.getDataLimite() != null ? LocalDate.parse(body.getDataLimite()) : null;
        Prioridade prioridade = parsePrioridade(body.getPrioridade());
        ServicosModel card = kanbanService.criarCard(colunaId, body.getTitulo(), body.getDescricao(), prioridade, dataLimite);
        return ResponseEntity.created(URI.create("/api/kanban/cards/" + card.getId()))
                .body(Map.of("id", card.getId()));
    }

    @PatchMapping("/cards/{cardId}")
    public ResponseEntity<Void> atualizarCard(@PathVariable Long cardId,
                                              @RequestBody UpdateCardRequest body) {
        LocalDate dataLimite = body.getDataLimite() != null ? LocalDate.parse(body.getDataLimite()) : null;
        Prioridade prioridade = parsePrioridade(body.getPrioridade());
        kanbanService.atualizarCard(cardId, body.getTitulo(), body.getDescricao(), prioridade, dataLimite, body.getConcluido());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/cards/{cardId}")
    public ResponseEntity<Void> deletarCard(@PathVariable Long cardId) {
        kanbanService.deletarCard(cardId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/cards/{cardId}/assign")
    public ResponseEntity<Void> atribuir(@PathVariable Long cardId, @RequestBody AssignRequest body) {
        kanbanService.atribuirResponsavel(cardId, body.getUserId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/cards/{cardId}/unassign")
    public ResponseEntity<Void> desatribuir(@PathVariable Long cardId, @RequestBody AssignRequest body) {
        kanbanService.removerResponsavel(cardId, body.getUserId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/cards/{cardId}/move")
    public ResponseEntity<Void> moverCard(@PathVariable Long cardId,
                                          @RequestBody MoveRequest body) {
        kanbanService.moverServico(cardId, body.getColunaDestinoId(), body.getNovaPosicao());
        return ResponseEntity.noContent().build();
    }

    @Data
    public static class CreateBoardRequest {
        @NotBlank
        private String nome;
        private Integer ownerId; // opcional
    }

    @Data
    public static class CreateColunaRequest {
        @NotBlank
        private String nome;
        private Integer posicao; // opcional
    }

    @Data
    public static class CreateCardRequest {
        @NotBlank
        private String titulo;
        private String descricao;
        private String prioridade; // BAIXA/MEDIA/ALTA/CRITICA
        private String dataLimite; // ISO yyyy-MM-dd
    }

    @Data
    public static class UpdateCardRequest {
        private String titulo;
        private String descricao;
        private String prioridade; // BAIXA/MEDIA/ALTA/CRITICA
        private String dataLimite; // ISO yyyy-MM-dd
        private Boolean concluido;
    }

    @Data
    public static class AssignRequest {
        private Integer userId;
    }

    @Data
    public static class MoveRequest {
        private Long colunaDestinoId;
        @Min(0)
        private int novaPosicao;
    }

    private Prioridade parsePrioridade(String p) {
        if (p == null || p.isBlank()) return null;
        try { return Prioridade.valueOf(p.toUpperCase()); }
        catch (IllegalArgumentException ex) { throw new IllegalArgumentException("Prioridade inválida: " + p); }
    }
}