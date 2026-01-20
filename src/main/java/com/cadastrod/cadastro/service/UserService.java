package com.cadastrod.cadastro.service;

import com.cadastrod.cadastro.model.UserDadosModel;
import com.cadastrod.cadastro.model.UserModel;
import com.cadastrod.cadastro.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Listar
    public List<UserModel> getAll(){
        return userRepository.findAll();
    }

    //Criar
    public UserModel save( UserModel userModel) {
        return userRepository.save(userModel);
    }

    //Delete
    public boolean delete(int id){
        userRepository.deleteById(id);
        return true;
    }

    //Update
    public UserModel atualizarDados(Integer id, UserDadosModel dados) {

        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        user.setUserDadosModel(dados);

        return userRepository.save(user);
    }
}
