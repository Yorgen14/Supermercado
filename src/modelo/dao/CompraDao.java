package modelo.dao;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Compra;
import modelo.Proveedor;
import modelo.Producto;
import servicio.Conexion;

public class CompraDao {
    Connection conexion;

    // Constructor para obtener la conexión
    public CompraDao() {
        try {
            this.conexion = Conexion.obtener();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    // Método para insertar una compra en la base de datos
    public void insertar(Compra c) {
        try {
            PreparedStatement consulta = conexion.prepareStatement(
                "INSERT INTO compra (proveedor_id, producto_id, cantidad, fecha) VALUES (?, ?, ?, ?)"
            );

            if (consulta != null) {
                // Establecemos los valores para los parámetros de la consulta
                consulta.setInt(1, c.getProveedor().getId());
                consulta.setInt(2, c.getProducto().getId());
                consulta.setInt(3, c.getCantidad());
                consulta.setDate(4, c.getFecha() != null ? new java.sql.Date(c.getFecha().getTime()) : null);
                consulta.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CompraDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Método para buscar una compra por su ID
    public Compra buscar(int id) {
        Compra compra = null;
        try {
            PreparedStatement consulta = conexion.prepareStatement(
                "SELECT * FROM compra WHERE id = ?"
            );
            consulta.setInt(1, id);
            ResultSet resultado = consulta.executeQuery();

            if (resultado.next()) {
                compra = new Compra();
                compra.setId(resultado.getInt("id"));
                
                // Establecemos el proveedor y producto relacionados
                Proveedor proveedor = new Proveedor();
                proveedor.setId(resultado.getInt("proveedor_id"));
                compra.setProveedor(proveedor);

                Producto producto = new Producto();
                producto.setId(resultado.getInt("producto_id"));
                compra.setProducto(producto);
                
                compra.setCantidad(resultado.getInt("cantidad"));
                compra.setFecha(resultado.getDate("fecha"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CompraDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return compra;
    }

    // Método para actualizar una compra en la base de datos
    public void actualizar(Compra c) {
        try {
            PreparedStatement consulta = conexion.prepareStatement(
                "UPDATE compra SET proveedor_id = ?, producto_id = ?, cantidad = ?, fecha = ? WHERE id = ?"
            );
            consulta.setInt(1, c.getProveedor().getId());
            consulta.setInt(2, c.getProducto().getId());
            consulta.setInt(3, c.getCantidad());
            consulta.setDate(4, c.getFecha() != null ? new java.sql.Date(c.getFecha().getTime()) : null);
            consulta.setInt(5, c.getId());
            
            consulta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CompraDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Método para eliminar una compra por su ID
    public void eliminar(int id) {
        try {
            PreparedStatement consulta = conexion.prepareStatement(
                "DELETE FROM compra WHERE id = ?"
            );
            consulta.setInt(1, id);
            consulta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CompraDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
