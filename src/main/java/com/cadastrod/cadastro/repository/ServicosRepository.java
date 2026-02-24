package com.cadastrod.cadastro.repository;

import com.cadastrod.cadastro.model.ColunaModel;
import com.cadastrod.cadastro.model.ServicosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ServicosRepository extends JpaRepository<ServicosModel, Long> {
    List<ServicosModel> findByColunaOrderByPosicaoAsc(ColunaModel coluna);
}