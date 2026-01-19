package com.cadastrod.cadastro.repository;

import com.cadastrod.cadastro.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
}
