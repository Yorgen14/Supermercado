package modelo.dao;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Venta;
import modelo.Cliente;
import modelo.Producto;
import servicio.Conexion;

public class VentaDao {
    Connection conexion;

    public VentaDao() {
        try {
            this.conexion = Conexion.obtener();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void insertar(Venta v) {
        try {
            PreparedStatement consulta = null;
            try {
                consulta = conexion.prepareStatement(
                    "INSERT INTO venta (cliente_id, producto_id, cantidad, fecha) VALUES (?, ?, ?, ?)"
                );
            } catch (SQLException ex) {
                Logger.getLogger(VentaDao.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (consulta != null) {
                consulta.setInt(1, v.getCliente().getId());
                consulta.setInt(2, v.getProducto().getId());
                consulta.setInt(3, v.getCantidad());
                consulta.setDate(4, v.getFecha() != null ? new java.sql.Date(v.getFecha().getTime()) : null);
                consulta.executeUpdate();
            }

        } catch (SQLException ex) {
            Logger.getLogger(VentaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Venta buscar(int id) {
        Venta venta = null;
        try {
            PreparedStatement consulta = conexion.prepareStatement(
                "SELECT * FROM venta WHERE id = ?"
            );
            consulta.setInt(1, id);
            ResultSet resultado = consulta.executeQuery();

            if (resultado.next()) {
                venta = new Venta();
                venta.setId(resultado.getInt("id"));
                Cliente cliente = new Cliente();
                cliente.setId(resultado.getInt("cliente_id"));
                venta.setCliente(cliente);

                Producto producto = new Producto();
                producto.setId(resultado.getInt("producto_id"));
                venta.setProducto(producto);

                venta.setCantidad(resultado.getInt("cantidad"));
                venta.setFecha(resultado.getTimestamp("fecha"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(VentaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return venta;
    }

    public void actualizar(Venta v) {
        try {
            PreparedStatement consulta = conexion.prepareStatement(
                "UPDATE venta SET cliente_id = ?, producto_id = ?, cantidad = ?, fecha = ? WHERE id = ?"
            );
            consulta.setInt(1, v.getCliente().getId());
            consulta.setInt(2, v.getProducto().getId());
            consulta.setInt(3, v.getCantidad());
            consulta.setDate(4, v.getFecha() != null ? new java.sql.Date(v.getFecha().getTime()) : null);
            consulta.setInt(5, v.getId());

            consulta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(VentaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminar(int id) {
        try {
            PreparedStatement consulta = conexion.prepareStatement(
                "DELETE FROM venta WHERE id = ?"
            );
            consulta.setInt(1, id);
            consulta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(VentaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
