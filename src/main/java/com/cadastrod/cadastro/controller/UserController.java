package com.cadastrod.cadastro.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class UserController {
    /*
    @PostMapping -- MANDAR
    @PutMapping -- ALTERAR
    @PatchMapping -- ALTERAR (VERIFICAR A DIFERENÇA DO PUT)
    @DeleteMapping -- DELETAR
    @GetMapping -- PUXAR
    */

    @GetMapping("/first")
    public String boasVindas(){
        return "My firsting message";
    }
}
