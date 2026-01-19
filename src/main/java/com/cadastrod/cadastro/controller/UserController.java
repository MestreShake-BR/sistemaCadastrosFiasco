package com.cadastrod.cadastro.controller;


import com.cadastrod.cadastro.model.UserModel;
import com.cadastrod.cadastro.service.UserService;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getuser")
    public List<UserModel> getAll(){
        return userService.getAll();
    }

    @PostMapping("/postuser")
    public UserModel create(@RequestBody UserModel userModel) {
        return userService.save(userModel);
    }


}
