package modelo.dao;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Producto;
import modelo.Proveedor;
import servicio.Conexion;

public class ProductoDao {
    Connection conexion;

   public ProductoDao() {
        try {
            this.conexion = Conexion.obtener();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void insertar(Producto p) {
        try {
            PreparedStatement consulta = null;
            try {
                consulta = conexion.prepareStatement(
                    "INSERT INTO producto (nombre, descripcion, precio, proveedor_id) VALUES (?, ?, ?, ?)"
                );
            } catch (SQLException ex) {
                Logger.getLogger(ProveedorDao.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (consulta != null) {
                consulta.setString(1, p.getNombre());
                consulta.setString(2, p.getDescripcion());
                consulta.setDouble(3, p.getPrecio());
                consulta.setInt(4, p.getProveedor().getId());
                consulta.executeUpdate();
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProveedorDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Producto buscar(int id) {
        Producto producto = null;
        try {
            PreparedStatement consulta = conexion.prepareStatement(
                "SELECT * FROM producto WHERE id = ?"
            );
            consulta.setInt(1, id);
            ResultSet resultado = consulta.executeQuery();

            if (resultado.next()) {
                producto = new Producto();
                producto.setId(resultado.getInt("id"));
                producto.setNombre(resultado.getString("nombre"));
                producto.setDescripcion(resultado.getString("descripcion"));
                producto.setPrecio(resultado.getDouble("precio"));

                // Aseguramos que se inicialice el proveedor antes de establecer el ID
                Proveedor proveedor = new Proveedor();
                proveedor.setId(resultado.getInt("proveedor_id"));
                producto.setProveedor(proveedor);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProveedorDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return producto;
    }

    // Método para actualizar un proveedor en la base de datos
    public void actualizar(Producto p) {
        try {
            PreparedStatement consulta = conexion.prepareStatement(
                "UPDATE producto SET nombre = ?, descripcion = ?, precio = ?, proveedor_id = ?  WHERE id = ?"
            );
            consulta.setString(1, p.getNombre());
            consulta.setString(2, p.getDescripcion());
            consulta.setDouble(3, p.getPrecio());
            consulta.setInt(4, p.getProveedor().getId());
            consulta.setInt(5, p.getId());
            
            consulta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProveedorDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Método para eliminar un proveedor por su ID
    public void eliminar(int id) {
        try {
            PreparedStatement consulta = conexion.prepareStatement(
                "DELETE FROM producto WHERE id = ?"
            );
            consulta.setInt(1, id);
            consulta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProveedorDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean productoExiste(int productoId) {
        try {
            PreparedStatement consulta = conexion.prepareStatement("SELECT * FROM producto WHERE id = ?");
            consulta.setInt(1, productoId);
            ResultSet rs = consulta.executeQuery();
            return rs.next(); // Si existe el producto, devuelve true
        } catch (SQLException ex) {
            Logger.getLogger(CompraDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
