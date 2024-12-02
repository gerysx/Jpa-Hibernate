package com.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class ClientesController {

    private EntityManagerFactory emf;

    public ClientesController() {
        this.emf = Persistence.createEntityManagerFactory("myJpaUnit");
    }

    public void close() {
        if (emf != null) {
            emf.close();
        }
    }

    // Crear un nuevo cliente
    public void createCliente(Clientes cliente) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Leer un cliente por ID
    public Clientes getClienteById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Clientes.class, id);
        } finally {
            em.close();
        }
    }

    // Obtener todos los clientes
    public List<Clientes> getAllClientes() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT c FROM Clientes c", Clientes.class).getResultList();
        } finally {
            em.close();
        }
    }

    // Actualizar un cliente
    public void updateCliente(Clientes cliente) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(cliente);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Eliminar un cliente
    public void deleteCliente(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Clientes cliente = em.find(Clientes.class, id);
            if (cliente != null) {
                em.remove(cliente);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
