package com.cadastrod.cadastro.repository;

import com.cadastrod.cadastro.model.BoardModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardModel, Long> { }