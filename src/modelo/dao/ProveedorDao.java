/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

/**
 *
 * @author estudiante
 */
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Proveedor;
import servicio.Conexion;

public class ProveedorDao {

    Connection conexion;
    
    public ProveedorDao() {
        try {
            this.conexion = Conexion.obtener();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    // Método para insertar un proveedor en la base de datos
    public void insertar(Proveedor proveedor) {
        try {
            PreparedStatement consulta = null;
            try {
                consulta = conexion.prepareStatement(
                    "INSERT INTO proveedor (nombre, contacto, telefono) VALUES (?, ?, ?)"
                );
            } catch (SQLException ex) {
                Logger.getLogger(ProveedorDao.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (consulta != null) {
                consulta.setString(1, proveedor.getNomnbre());
                consulta.setString(2, proveedor.getContacto());
                consulta.setString(3, proveedor.getTelefono());
                consulta.executeUpdate();
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProveedorDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    // Método para buscar un proveedor por su ID
    public Proveedor buscar(int id) {
        Proveedor proveedor = null;
        try {
            PreparedStatement consulta = conexion.prepareStatement(
                "SELECT * FROM proveedor WHERE id = ?"
            );
            consulta.setInt(1, id);
            ResultSet resultado = consulta.executeQuery();

            if (resultado.next()) {
                proveedor = new Proveedor();
                proveedor.setId(resultado.getInt("id"));
                proveedor.setNomnbre(resultado.getString("nombre"));
                proveedor.setContacto(resultado.getString("contacto"));
                proveedor.setTelefono(resultado.getString("telefono"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProveedorDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return proveedor;
    }

    // Método para actualizar un proveedor en la base de datos
    public void actualizar(Proveedor proveedor) {
        try {
            PreparedStatement consulta = conexion.prepareStatement(
                "UPDATE proveedor SET nombre = ?, contacto = ?, telefono = ? WHERE id = ?"
            );
            consulta.setString(1, proveedor.getNomnbre());
            consulta.setString(2, proveedor.getContacto());
            consulta.setString(3, proveedor.getTelefono());
            consulta.setInt(4, proveedor.getId());
            consulta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProveedorDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Método para eliminar un proveedor por su ID
    public void eliminar(int id) {
        try {
            PreparedStatement consulta = conexion.prepareStatement(
                "DELETE FROM proveedor WHERE id = ?"
            );
            consulta.setInt(1, id);
            consulta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProveedorDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Método para verificar si un proveedor existe
    public boolean proveedorExiste(int proveedorId) {
        try {
            PreparedStatement consulta = conexion.prepareStatement("SELECT * FROM proveedor WHERE id = ?");
            consulta.setInt(1, proveedorId);
            ResultSet rs = consulta.executeQuery();
            return rs.next(); // Si hay resultados, el proveedor existe
        } catch (SQLException ex) {
            Logger.getLogger(ProveedorDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}

