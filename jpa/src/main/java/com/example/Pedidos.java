package com.example;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Pedidos")
public class Pedidos {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_pedido")
    private int id_pedido;

    @Column(name = "producto")
    private String producto;

    @Column(name = "precio")
    private double precio;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha")
    private Date fecha_Pedido;

    // Relación con la entidad Clientes
    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    private Clientes cliente;

    public Pedidos() {}

    public Pedidos(int id_pedido, String producto, double precio, Date fecha_Pedido, Clientes cliente) {
        this.id_pedido = id_pedido;
        this.producto = producto;
        this.precio = precio;
        this.fecha_Pedido = fecha_Pedido;
        this.cliente = cliente;
    }

    // Getters y setters

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Date getFecha_Pedido() {
        return fecha_Pedido;
    }

    public void setFecha_Pedido(Date fecha_Pedido) {
        this.fecha_Pedido = fecha_Pedido;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }
}
