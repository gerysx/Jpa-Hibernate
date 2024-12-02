package com.example;

import jakarta.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Crear un EntityManagerFactory y EntityManager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJpaUnit");
        EntityManager em = emf.createEntityManager();
        Scanner scanner = new Scanner(System.in);

        // Inicializamos dateFormat de manera segura
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");

        while (true) {
            // Menú interactivo
            System.out.println("Selecciona una opción:");
            System.out.println("1. Crear Cliente");
            System.out.println("2. Ver Clientes");
            System.out.println("3. Crear Pedido para Cliente");
            System.out.println("4. Ver Pedidos de un Cliente");
            System.out.println("5. Salir");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    // Crear un cliente
                    System.out.println("Introduce el nombre del cliente:");
                    String nombre = scanner.nextLine();
                    System.out.println("Introduce el apellido del cliente:");
                    String apellido = scanner.nextLine();
                    System.out.println("Introduce la fecha de nacimiento (YYYY-MM-DD):");
                    String fecha = scanner.nextLine();

                    // Convertir la fecha con SimpleDateFormat
                    Date fechaNac = null;
                    try {
                        fechaNac = dateFormat.parse(fecha); // Convertir String a Date
                    } catch (Exception e) {
                        System.out.println("Error al convertir la fecha. Usa el formato correcto (YYYY-MM-DD).");
                        break;
                    }

                    Clientes cliente = new Clientes();
                    cliente.setNombre(nombre);
                    cliente.setApellido(apellido);
                    cliente.setFechaNac(fechaNac);

                    // Persistir el cliente en la base de datos
                    em.getTransaction().begin();
                    em.persist(cliente);
                    em.getTransaction().commit();

                    System.out.println("Cliente creado con éxito.");
                    break;

                case 2:
                    // Ver todos los clientes
                    List<Clientes> clientes = em.createQuery("SELECT c FROM Clientes c", Clientes.class).getResultList();
                    for (Clientes c : clientes) {
                        System.out.println("ID: " + c.getId() + ", Nombre: " + c.getNombre() + ", Apellido: " + c.getApellido());
                    }
                    break;

                case 3:
                    // Crear un pedido para un cliente
                    System.out.println("Introduce el ID del cliente:");
                    int idCliente = scanner.nextInt();
                    scanner.nextLine(); // Limpiar el buffer
                    Clientes clientePedido = em.find(Clientes.class, idCliente);

                    if (clientePedido != null) {
                        System.out.println("Introduce el producto del pedido:");
                        String producto = scanner.nextLine();
                        System.out.println("Introduce el precio del producto:");
                        double precio = scanner.nextDouble();
                        System.out.println("Introduce la fecha del pedido (YYYY-MM-DD):");
                        String fechaPedido = scanner.next();
                        Date fechaPedidoDate = null;
                        try {
                            fechaPedidoDate = dateFormat.parse(fechaPedido); // Convertir String a Date
                        } catch (Exception e) {
                            System.out.println("Error al convertir la fecha. Usa el formato correcto (YYYY-MM-DD).");
                            break;
                        }

                        Pedidos pedido = new Pedidos();
                        pedido.setProducto(producto);
                        pedido.setPrecio(precio);
                        pedido.setFecha_Pedido(fechaPedidoDate);
                        pedido.setCliente(clientePedido);

                        // Persistir el pedido en la base de datos
                        em.getTransaction().begin();
                        em.persist(pedido);
                        em.getTransaction().commit();

                        System.out.println("Pedido creado con éxito.");
                    } else {
                        System.out.println("Cliente no encontrado.");
                    }
                    break;

                    case 4:
                    // Ver pedidos de un cliente
                    System.out.println("Introduce el ID del cliente para ver sus pedidos:");
                    int idClientePedidos = scanner.nextInt();
                    Clientes clienteConPedidos = em.find(Clientes.class, idClientePedidos);
                
                    if (clienteConPedidos != null) {
                        List<Pedidos> pedidosCliente = clienteConPedidos.getPedidos();
                        for (Pedidos p : pedidosCliente) {
                            System.out.println("ID Pedido: " + p.getId_pedido() + ", Producto: " + p.getProducto() + ", Precio: " + p.getPrecio() + ", Fecha: " + p.getFecha_Pedido());
                        }
                    } else {
                        System.out.println("Cliente no encontrado.");
                    }
                    break;

                case 5:
                    // Salir
                    System.out.println("Saliendo...");
                    em.close();
                    emf.close();
                    scanner.close();
                    return;

                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
            }
        }
    }
}
