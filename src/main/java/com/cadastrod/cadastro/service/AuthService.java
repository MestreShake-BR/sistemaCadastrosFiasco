package com.cadastrod.cadastro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cadastrod.cadastro.model.UserModel;
import com.cadastrod.cadastro.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean login(String email, String senha) {
        return userRepository.findByEmail(email)
                .map(user -> user.getSenha().equals(senha))
                .orElse(false);
    }

    public UserModel cadastrar(UserModel user) {
        return userRepository.save(user);
    }
}

