/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author David
 */
public class Cliente {
    private Integer id;
    private String nombre, email, telefono;
    private Integer recomendadoPor;

    public Cliente() {
    }

    public Cliente(Integer id, String nombre, String email, String telefono, Integer recomendadoPor) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.recomendadoPor = recomendadoPor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Integer getRecomendadoPor() {
        return recomendadoPor;
    }

    public void setRecomendadoPor(Integer recomendadoPor) {
        this.recomendadoPor = recomendadoPor;
    }

    

}
