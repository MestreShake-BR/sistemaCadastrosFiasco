package com.cadastrod.cadastro.repository;

import com.cadastrod.cadastro.model.BoardModel;
import com.cadastrod.cadastro.model.ColunaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ColunaRepository extends JpaRepository<ColunaModel, Long> {
    List<ColunaModel> findByBoardOrderByPosicaoAsc(BoardModel board);
}