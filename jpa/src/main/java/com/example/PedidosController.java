package com.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class PedidosController {

    private EntityManagerFactory emf;

    // Constructor para inicializar la unidad de persistencia
    public PedidosController() {
        this.emf = Persistence.createEntityManagerFactory("myJpaUnit");
    }

    // Cierra la conexión con la fábrica de administradores de entidades
    public void close() {
        if (emf != null) {
            emf.close();
        }
    }

    // Crear un nuevo pedido
    public void createPedido(Pedidos pedido) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(pedido);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Leer un pedido por su ID
    public Pedidos getPedidoById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Pedidos.class, id);
        } finally {
            em.close();
        }
    }

    // Obtener todos los pedidos
    public List<Pedidos> getAllPedidos() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT p FROM Pedidos p", Pedidos.class).getResultList();
        } finally {
            em.close();
        }
    }

    // Actualizar un pedido
    public void updatePedido(Pedidos pedido) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(pedido);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Eliminar un pedido por su ID
    public void deletePedido(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Pedidos pedido = em.find(Pedidos.class, id);
            if (pedido != null) {
                em.remove(pedido);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
