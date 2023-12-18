package com.example.hiberante_crud.models.dao;

import com.example.hiberante_crud.models.entity.Nadador;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NadadorDaO {

    void saveNadador(Nadador nadador);

    Nadador findById(Integer id);

    List<Nadador> findByEmail(String email);

    List<Nadador> findAll();

    void buscarTodo();

    void updateNadador(Nadador nadador);

    void deleteNadador(Integer id);

    void deleteAll();
}
