
package com.cadastrod.cadastro.service;

import com.cadastrod.cadastro.model.UserModel;
import com.cadastrod.cadastro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean login(String email, String senhaDigitada) {
        return userRepository.findByEmail(email)
                .map(user -> passwordEncoder.matches(senhaDigitada, user.getSenha()))
                .orElse(false);
    }

    public UserModel cadastrar(UserModel user) {
        userRepository.findByEmail(user.getEmail())
                .ifPresent(u -> {
                    throw new IllegalArgumentException("E-mail já cadastrado.");
                });

        String hash = passwordEncoder.encode(user.getSenha());
        user.setSenha(hash);

        return userRepository.save(user);
    }
}