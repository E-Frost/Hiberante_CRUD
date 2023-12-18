package com.example.hiberante_crud.models.dao;

import com.example.hiberante_crud.HiberanteCrudApplication;
import jakarta.persistence.EntityManager;
import com.example.hiberante_crud.models.entity.Nadador;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class NadadorDaOImpl implements NadadorDaO {

    private EntityManager entityManager;

    @Autowired
    public NadadorDaOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void saveNadador(Nadador nadador) {
        entityManager.persist(nadador);
    }

    @Override
    @Transactional
    public Nadador findById(Integer id) {
        return entityManager.find(Nadador.class, id);
    }

    @Override
    @Transactional
    public List<Nadador> findByEmail(String emailBuscado) {
        TypedQuery<Nadador> nadadorTypedQuery = entityManager.createQuery("FROM Nadador  WHERE email =:emailBuscado", Nadador.class);
        nadadorTypedQuery.setParameter("emailBuscado", emailBuscado);
        return nadadorTypedQuery.getResultList();
    }

    @Override
    public List<Nadador> findAll() {
        TypedQuery <Nadador> nadadorTypedQuery = entityManager.createQuery("from Nadador ", Nadador.class);
        return nadadorTypedQuery.getResultList();
    }

    @Override
    @Transactional
    public void buscarTodo() {
        List<Nadador> nadadores = findAll();

        for (Nadador nadador : nadadores) {
            HiberanteCrudApplication.listarDatosNadador(nadador);
            System.out.println("--------------------");
        }
    }

    @Override
    @Transactional
    public void updateNadador(Nadador nadador) {
        entityManager.merge(nadador);
    }

    @Override
    @Transactional
    public void deleteNadador(Integer id) {
        Nadador nadador = entityManager.find(Nadador.class, id);
        entityManager.remove(nadador);
    }

    @Override
    @Transactional
    public void deleteAll() {
        try {
            entityManager.createQuery("DELETE FROM Nadador").executeUpdate();
            System.out.println("Todos los nadadores han sido eliminados satisfactoriamente.");
        } catch (Exception e) {
            System.out.println("Error al intentar eliminar todos los nadadores. Detalles: " + e.getMessage());
        }
    }
}
