package modelo.dao;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Cliente;
import modelo.Recomendacion;
import servicio.Conexion;

public class ClienteDao {

    private Connection conexion;

    public ClienteDao() {
        try {
            this.conexion = Conexion.obtener();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    // Método para insertar un cliente en la base de datos
    public void insertar(Cliente cliente) {
    try {
        // Preparamos la consulta para insertar un cliente
        String sql = "INSERT INTO cliente (id, nombre, email, telefono, cliente_recomendador_id) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement consulta = conexion.prepareStatement(sql);

        consulta.setInt(1, cliente.getId());
        consulta.setString(2, cliente.getNombre());
        consulta.setString(3, cliente.getEmail());
        consulta.setString(4, cliente.getTelefono());

        // Verificamos si el cliente tiene un valor en recomendadoPor
        if (cliente.getRecomendadoPor() != null) {
            // Buscamos si el cliente recomendado existe
            Cliente clienteRecomendador = buscar(cliente.getRecomendadoPor());
            if (clienteRecomendador != null) {
                // Si existe, creamos una relación en la tabla recomendacion
                Recomendacion recomendacion = new Recomendacion();
                recomendacion.setClienteId(cliente.getId());
                recomendacion.setClienteRecomendadoId(cliente.getRecomendadoPor());
                consulta.setInt(5, cliente.getRecomendadoPor());  // Asignamos el valor de recomendadoPor

                // Insertamos la relación en la tabla recomendacion
                insertarRecomendacion(recomendacion);
            } else {
                System.out.println("El cliente recomendado con ID " + cliente.getRecomendadoPor() + " no existe.");
                consulta.setNull(5, java.sql.Types.INTEGER);  // Si no existe, asignamos null
            }
        } else {
            consulta.setNull(5, java.sql.Types.INTEGER);  // Si recomendadoPor es null, asignamos null
        }

        // Ejecutamos la consulta para insertar el cliente
        consulta.executeUpdate();
    } catch (SQLException ex) {
        Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
    }
}



    // Método para buscar un cliente por su ID
    public Cliente buscar(int id) {
        Cliente cliente = null;
        try {
            // Preparamos la consulta para buscar el cliente por ID
            String sql = "SELECT * FROM cliente WHERE id = ?";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            consulta.setInt(1, id);
            ResultSet resultado = consulta.executeQuery();

            // Si encontramos el cliente, lo asignamos a la instancia
            if (resultado.next()) {
                cliente = new Cliente();
                cliente.setId(resultado.getInt("id"));
                cliente.setNombre(resultado.getString("nombre"));
                cliente.setEmail(resultado.getString("email"));
                cliente.setTelefono(resultado.getString("telefono"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cliente;
    }

    // Método para actualizar un cliente en la base de datos
    public void actualizar(Cliente cliente) {
        try {
            // Preparamos la consulta para actualizar el cliente por ID
            String sql = "UPDATE cliente SET nombre = ?, email = ?, telefono = ? WHERE id = ?";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            
            consulta.setString(1, cliente.getNombre());
            consulta.setString(2, cliente.getEmail());
            consulta.setString(3, cliente.getTelefono());
            consulta.setInt(4, cliente.getId());
            
            // Ejecutamos la consulta
            consulta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Método para eliminar un cliente por su ID
    public void eliminar(int id) {
        try {
            // Preparamos la consulta para eliminar el cliente por ID
            String sql = "DELETE FROM cliente WHERE id = ?";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            consulta.setInt(1, id);
            
            // Ejecutamos la consulta
            consulta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

        // Método para insertar una recomendación
        // Método para insertar una recomendación con porcentaje
    public void insertarRecomendacion(Recomendacion recomendacion) {
        try {
            // Preparamos la consulta para insertar la recomendación
            String sql = "INSERT INTO recomendacion (cliente_id, cliente_recomendado_id, porcentaje) VALUES (?, ?, ?)";
            PreparedStatement consulta = conexion.prepareStatement(sql);

            consulta.setInt(1, recomendacion.getClienteId());
            consulta.setInt(2, recomendacion.getClienteRecomendadoId());
            // Establecemos el porcentaje, que puede ser nulo
            consulta.setObject(3, recomendacion.getPorcentaje(), java.sql.Types.DOUBLE); 

            // Ejecutamos la consulta
            consulta.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    // Método para buscar si existe una recomendación
    public Recomendacion buscarRecomendacion(int clienteId, int clienteRecomendadoId) {
        Recomendacion recomendacion = null;
        try {
            // Preparamos la consulta para buscar la recomendación
            String sql = "SELECT * FROM recomendacion WHERE cliente_id = ? AND cliente_recomendado_id = ?";
            PreparedStatement consulta = conexion.prepareStatement(sql);
            consulta.setInt(1, clienteId);
            consulta.setInt(2, clienteRecomendadoId);
            ResultSet resultado = consulta.executeQuery();

            // Si encontramos la recomendación, la asignamos a la instancia
            if (resultado.next()) {
                recomendacion = new Recomendacion();
                recomendacion.setClienteId(resultado.getInt("cliente_id"));
                recomendacion.setClienteRecomendadoId(resultado.getInt("cliente_recomendado_id"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return recomendacion;
    }
    
    public boolean clienteExiste(int clienteId) {
        try {
            // Preparar la consulta SQL para verificar si el cliente existe
            PreparedStatement consulta = conexion.prepareStatement("SELECT * FROM cliente WHERE id = ?");
            consulta.setInt(1, clienteId); // Establecer el ID del cliente
            ResultSet rs = consulta.executeQuery();

            // Si la consulta retorna resultados, el cliente existe
            return rs.next();
        } catch (SQLException ex) {
            // En caso de error, registrar el error y devolver false
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }   
    
}
