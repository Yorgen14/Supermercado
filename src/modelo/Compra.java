/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.Date;

/**
 *
 * @author estudiante
 */
public class Compra {
    private Integer id;
    private Proveedor proveedor;
    private Producto producto;
    private Integer cantidad;
    private Date fecha;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Compra(Integer id, Proveedor proveedor, Producto producto, Integer cantidad, Date fecha) {
        this.id = id;
        this.proveedor = proveedor;
        this.producto = producto;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

    public Compra() {
    }
}
