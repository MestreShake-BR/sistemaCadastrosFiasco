package com.cadastrod.cadastro.service;

import com.cadastrod.cadastro.model.*;
import com.cadastrod.cadastro.model.ServicosModel.Prioridade;
import com.cadastrod.cadastro.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KanbanService {

    private final ServicosRepository servicosRepo;
    private final ColunaRepository colunaRepo;
    private final BoardRepository boardRepo;
    private final UserRepository userRepo;

    // =============================
    // CRUD: Board e Coluna
    // =============================

    @Transactional
    public BoardModel criarBoard(String nome, Integer ownerId) {
        BoardModel b = new BoardModel();
        b.setNome(nome);
        if (ownerId != null) {
            UserModel owner = userRepo.findById(ownerId)
                    .orElseThrow(() -> new IllegalArgumentException("Owner não encontrado"));
            b.setOwner(owner);
        }
        return boardRepo.save(b);
    }

    @Transactional
    public ColunaModel criarColuna(Long boardId, String nome, Integer posicaoOpt) {
        BoardModel board = boardRepo.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Board não encontrado"));
        ColunaModel c = new ColunaModel();
        c.setBoard(board);
        c.setNome(nome);
        // posição: se não veio, coloca no final
        int posicao = Optional.ofNullable(posicaoOpt)
                .orElseGet(() -> {
                    List<ColunaModel> existentes = colunaRepo.findByBoardOrderByPosicaoAsc(board);
                    return existentes.stream()
                            .map(ColunaModel::getPosicao)
                            .max(Comparator.naturalOrder())
                            .orElse(-1) + 1;
                });
        c.setPosicao(posicao);
        return colunaRepo.save(c);
    }

    @Transactional
    public ServicosModel criarCard(Long colunaId, String titulo, String descricao, Prioridade prioridade, LocalDate dataLimite) {
        ColunaModel coluna = colunaRepo.findById(colunaId)
                .orElseThrow(() -> new IllegalArgumentException("Coluna não encontrada"));
        ServicosModel card = new ServicosModel();
        card.setServico(titulo);
        card.setDescricao(descricao);
        card.setPrioridade(prioridade != null ? prioridade : Prioridade.MEDIA);
        card.setDataLimite(dataLimite);
        card.moverParaColuna(coluna);

        // posicao = final da coluna
        List<ServicosModel> itens = servicosRepo.findByColunaOrderByPosicaoAsc(coluna);
        int pos = itens.size();
        card.definirPosicao(pos);
        return servicosRepo.save(card);
    }

    @Transactional
    public ServicosModel atualizarCard(Long cardId, String titulo, String descricao, Prioridade prioridade,
                                       LocalDate dataLimite, Boolean concluido) {
        ServicosModel card = servicosRepo.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado"));
        if (titulo != null) card.setServico(titulo);
        if (descricao != null) card.setDescricao(descricao);
        if (prioridade != null) card.setPrioridade(prioridade);
        if (dataLimite != null) card.setDataLimite(dataLimite);
        if (concluido != null) card.setConcluido(concluido);
        return servicosRepo.save(card);
    }

    @Transactional
    public void deletarCard(Long cardId) {
        ServicosModel card = servicosRepo.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado"));
        ColunaModel coluna = card.getColuna();
        servicosRepo.delete(card);
        // reindexa a coluna após remoção
        reindexar(coluna);
    }

    @Transactional
    public void atribuirResponsavel(Long cardId, Integer userId) {
        ServicosModel card = servicosRepo.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado"));
        UserModel user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        card.getResponsaveis().add(user);
        servicosRepo.save(card);
    }

    @Transactional
    public void removerResponsavel(Long cardId, Integer userId) {
        ServicosModel card = servicosRepo.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado"));
        UserModel user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        card.getResponsaveis().remove(user);
        servicosRepo.save(card);
    }

    @Transactional
    public void moverServico(Long servicoId, Long colunaDestinoId, int novaPosicao) {
        ServicosModel card = servicosRepo.findById(servicoId)
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado"));
        ColunaModel destino = colunaRepo.findById(colunaDestinoId)
                .orElseThrow(() -> new IllegalArgumentException("Coluna destino não encontrada"));

        ColunaModel origem = card.getColuna();

        if (origem == null || !origem.getId().equals(destino.getId())) {
            if (origem != null) reindexar(origem);
            card.moverParaColuna(destino);
        }

        inserirNaPosicao(card, destino, novaPosicao);
        servicosRepo.save(card);
    }

    private void inserirNaPosicao(ServicosModel card, ColunaModel destino, int pos) {
        List<ServicosModel> itens = servicosRepo.findByColunaOrderByPosicaoAsc(destino);

        itens.removeIf(s -> s.getId().equals(card.getId()));

        pos = Math.max(0, Math.min(pos, itens.size()));

        for (int i = pos; i < itens.size(); i++) {
            itens.get(i).definirPosicao(i + 1);
        }
        card.definirPosicao(pos);
    }

    private void reindexar(ColunaModel coluna) {
        List<ServicosModel> itens = servicosRepo.findByColunaOrderByPosicaoAsc(coluna);
        for (int i = 0; i < itens.size(); i++) itens.get(i).definirPosicao(i);
    }
}