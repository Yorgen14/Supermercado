package control;

import Vista.JFVenta;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import modelo.Cliente;
import modelo.Producto;
import modelo.Venta;
import modelo.dao.ClienteDao;
import modelo.dao.ProductoDao;
import modelo.dao.VentaDao;

/**
 *
 * ControlVenta class to handle actions related to JFVenta form
 */
public class ControlVenta implements ActionListener {

    private VentaDao ventaDao;
    private JFVenta form;

    public ControlVenta(JFVenta form) {
        this.form = form;
        this.ventaDao = new VentaDao(); // Inicializa el DAO
        this.actionListeners();
    }

    private void actionListeners() {
        this.form.btnGuardarV.addActionListener(this);
        this.form.btnActualizarV.addActionListener(this);
        this.form.btnEliminarV.addActionListener(this);
        this.form.btnBuscarV.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == form.btnGuardarV) {
            guardarVenta();
        } else if (e.getSource() == form.btnActualizarV) {
            actualizarVenta();
        } else if (e.getSource() == form.btnEliminarV) {
            eliminarVenta();
        } else if (e.getSource() == form.btnBuscarV) {
            buscarVenta();
        }
    }

    private void guardarVenta() {
    try {
        Venta nuevaVenta = new Venta();
        
        // Obtener cliente
        int clienteId = Integer.parseInt(form.txtClienteV.getText());
        ClienteDao clienteDao = new ClienteDao();
        
        // Validar si el cliente existe
        if (!clienteDao.clienteExiste(clienteId)) {
            JOptionPane.showMessageDialog(form, "Cliente no válido.");
            return; // Salir si el cliente no existe
        }
        
        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        nuevaVenta.setCliente(cliente);
        
        // Obtener producto
        int productoId = Integer.parseInt(form.txtProductoV.getText());
        ProductoDao productoDao = new ProductoDao();
        
        // Validar si el producto existe
        if (!productoDao.productoExiste(productoId)) {
            JOptionPane.showMessageDialog(form, "Producto no válido.");
            return; // Salir si el producto no existe
        }
        
        Producto producto = new Producto();
        producto.setId(productoId);
        nuevaVenta.setProducto(producto);
        
        // Asignar cantidad
        nuevaVenta.setCantidad(Integer.parseInt(form.txtCantidadV.getText()));
        
        // Convertir fecha de String a Date
        String fechaTexto = form.txtFechaV.getText();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd"); // Formato de fecha que esperas
        Date fecha = formatoFecha.parse(fechaTexto);
        nuevaVenta.setFecha(fecha);
        
        // Guardar la venta
        ventaDao.insertar(nuevaVenta);
        JOptionPane.showMessageDialog(form, "Venta guardada exitosamente");
        
        // Mostrar los detalles de la venta guardada en el txtResultados
        form.txtResultados.append("Venta guardada: \n");
        form.txtResultados.append("ID: " + nuevaVenta.getId() + "\n");
        form.txtResultados.append("Cliente ID: " + nuevaVenta.getCliente().getId() + "\n");
        form.txtResultados.append("Producto ID: " + nuevaVenta.getProducto().getId() + "\n");
        form.txtResultados.append("Cantidad: " + nuevaVenta.getCantidad() + "\n");
        form.txtResultados.append("Fecha: " + nuevaVenta.getFecha() + "\n\n");
        
        limpiarCampos();
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(form, "Error al guardar la venta: " + ex.getMessage());
    }
}


    private void actualizarVenta() {
        try {
            Venta venta = new Venta();
            venta.setId(Integer.parseInt(form.txtIdentificadorV.getText()));
            
            Cliente cliente = new Cliente();
            cliente.setId(Integer.parseInt(form.txtClienteV.getText()));
            venta.setCliente(cliente);
            Producto producto = new Producto();
            producto.setId(Integer.parseInt(form.txtProductoV.getText()));
            venta.setProducto(producto);
            
            venta.setCantidad(Integer.parseInt(form.txtCantidadV.getText()));
            venta.setFecha(new Date()); // Aquí puedes ajustar si se utiliza la fecha de entrada

            ventaDao.actualizar(venta);
            JOptionPane.showMessageDialog(form, "Venta actualizada exitosamente");
            limpiarCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, "Error al actualizar la venta: " + ex.getMessage());
        }
    }

    private void eliminarVenta() {
        try {
            int id = Integer.parseInt(form.txtIdentificadorV.getText());
            ventaDao.eliminar(id);
            JOptionPane.showMessageDialog(form, "Venta eliminada exitosamente");
            limpiarCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, "Error al eliminar la venta: " + ex.getMessage());
        }
    }

    private void buscarVenta() {
        try {
            int id = Integer.parseInt(form.txtIdentificadorV.getText());
            Venta venta = ventaDao.buscar(id);

            if (venta != null) {
                form.txtClienteV.setText(String.valueOf(venta.getCliente().getId()));
                form.txtProductoV.setText(String.valueOf(venta.getProducto().getId()));
                form.txtCantidadV.setText(String.valueOf(venta.getCantidad()));
                form.txtFechaV.setText(String.valueOf(venta.getFecha()));
                String fechaTexto = form.txtFechaV.getText();
                SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd"); // Formato de fecha que esperas
                Date fecha = formatoFecha.parse(fechaTexto);
                venta.setFecha(fecha);
                
            } else {
                JOptionPane.showMessageDialog(form, "Venta no encontrada");
                limpiarCampos();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, "Error al buscar la venta: " + ex.getMessage());
        }
    }

    private void limpiarCampos() {
        form.txtIdentificadorV.setText("");
        form.txtClienteV.setText("");
        form.txtProductoV.setText("");
        form.txtCantidadV.setText("");
        form.txtFechaV.setText("");
    }
}
