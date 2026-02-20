package com.cadastrod.cadastro.controller;


import com.cadastrod.cadastro.model.UserDadosModel;
import com.cadastrod.cadastro.model.UserModel;
import com.cadastrod.cadastro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    /*
    @PostMapping -- MANDAR
    @PutMapping -- ALTERAR
    @PatchMapping -- ALTERAR (VERIFICAR A DIFERENÇA DO PUT) -- PATCH SERVE PARA ALTERAR OS DADOS PARCIALMENTE ENQUANTO O PUT NECESSITA O ENVIO DO OBJETO COMPLETO
    @DeleteMapping -- DELETAR
    @GetMapping -- PUXAR
    */

    @GetMapping("/first")
    public String boasVindas(){
        return "My firsting message";
    }

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get")
    public List<UserModel> getAll(){
        return userService.getAll();
    }

    @PostMapping("/post")
    public UserModel create(@RequestBody UserModel userModel) {
        return userService.save(userModel);
    }

    @PatchMapping("/{id}/dados")
    public UserModel atualizarDados(
            @PathVariable Integer id,
            @RequestBody UserDadosModel dados) {
        return userService.atualizarDados(id, dados);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteDados(@PathVariable Integer id) {
        boolean isDeleted = userService.delete(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}