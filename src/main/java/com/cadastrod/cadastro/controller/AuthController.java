package com.cadastrod.cadastro.controller;

import com.cadastrod.cadastro.dto.LoginRequest;
import com.cadastrod.cadastro.model.UserModel;
import com.cadastrod.cadastro.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/first")
    public String boasVindas(){
        return "My firsting message";
    }

    @Autowired
    private  AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserModel user) {
        return ResponseEntity.ok(authService.cadastrar(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        boolean sucesso = authService.login(request.getEmail(), request.getSenha());

        if (sucesso) {
            return ResponseEntity.ok("Login realizado com sucesso");
        } else {
            return ResponseEntity.status(401).body("Email ou senha inválidos");
        }
    }
}