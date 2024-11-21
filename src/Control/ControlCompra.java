package control;

import Vista.JFCompra;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import modelo.Compra;
import modelo.Producto;
import modelo.Proveedor;
import modelo.dao.CompraDao;
import modelo.dao.ProductoDao;
import modelo.dao.ProveedorDao;

public class ControlCompra implements ActionListener {

    private CompraDao compraDao;
    private JFCompra form;

    public ControlCompra(JFCompra form) {
        this.form = form;
        this.compraDao = new CompraDao();
        this.actionListeners();
    }

    private void actionListeners() {
        this.form.btnGuardarCm.addActionListener(this);
        this.form.btnActualizarCm.addActionListener(this);
        this.form.btnEliminarCm.addActionListener(this);
        this.form.btnBuscarCm.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == form.btnGuardarCm) {
            guardarCompra();
        } else if (e.getSource() == form.btnActualizarCm) {
            actualizarCompra();
        } else if (e.getSource() == form.btnEliminarCm) {
            eliminarCompra();
        } else if (e.getSource() == form.btnBuscarCm) {
            buscarCompra();
        }
    }

    private void guardarCompra() {
    try {
        Compra nuevaCompra = new Compra();

        // Convertir fecha de String a Date
        String fechaTexto = form.txtFechaCm.getText();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd"); // Formato de fecha que esperas
        Date fecha = formatoFecha.parse(fechaTexto);
        nuevaCompra.setFecha(fecha);

        // Validar si el proveedor existe
        int proveedorId = Integer.parseInt(form.txtProveedorCm.getText());
        ProveedorDao proveedorDao = new ProveedorDao();
        
        if (!proveedorDao.proveedorExiste(proveedorId)) {
            JOptionPane.showMessageDialog(form, "Proveedor no válido.");
            return; // Salir si el proveedor no existe
        }

        // Asignar Proveedor
        Proveedor proveedor = new Proveedor();
        proveedor.setId(proveedorId);
        nuevaCompra.setProveedor(proveedor);

        // Validar si el producto existe
        int productoId = Integer.parseInt(form.txtProductoCm.getText());
        ProductoDao productoDao = new ProductoDao();
        
        if (!productoDao.productoExiste(productoId)) {
            JOptionPane.showMessageDialog(form, "Producto no válido.");
            return; // Salir si el producto no existe
        }

        // Asignar Producto
        Producto producto = new Producto();
        producto.setId(productoId);
        nuevaCompra.setProducto(producto);

        // Asignar cantidad
        nuevaCompra.setCantidad(Integer.parseInt(form.txtCantidadCm.getText()));

        // Guardar la compra
        compraDao.insertar(nuevaCompra);
        JOptionPane.showMessageDialog(form, "Compra guardada exitosamente");
        limpiarCampos();
        
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(form, "Error al guardar la compra: " + ex.getMessage());
    }
}


    private void actualizarCompra() {
        try {
            Compra compra = new Compra();
            compra.setId(Integer.parseInt(form.txtIdentificadorCm.getText()));

            // Convertir fecha de String a Date
            String fechaTexto = form.txtFechaCm.getText();
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd"); // Formato de fecha que esperas
            Date fecha = formatoFecha.parse(fechaTexto);
            compra.setFecha(fecha);

            // Asignar Proveedor
            Proveedor proveedor = new Proveedor();
            proveedor.setId(Integer.parseInt(form.txtProveedorCm.getText()));
            compra.setProveedor(proveedor);

            // Asignar Producto
            Producto producto = new Producto();
            producto.setId(Integer.parseInt(form.txtProductoCm.getText()));
            compra.setProducto(producto);

            // Asignar cantidad
            compra.setCantidad(Integer.parseInt(form.txtCantidadCm.getText()));

            compraDao.actualizar(compra);
            JOptionPane.showMessageDialog(form, "Compra actualizada exitosamente");
            limpiarCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, "Error al actualizar la compra: " + ex.getMessage());
        }
    }

    private void eliminarCompra() {
        try {
            int id = Integer.parseInt(form.txtIdentificadorCm.getText());
            compraDao.eliminar(id);
            JOptionPane.showMessageDialog(form, "Compra eliminada exitosamente");
            limpiarCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, "Error al eliminar la compra: " + ex.getMessage());
        }
    }

    private void buscarCompra() {
        try {
            int id = Integer.parseInt(form.txtIdentificadorCm.getText());
            Compra compra = compraDao.buscar(id);

            if (compra != null) {
                // Convertir fecha de Date a String para mostrar en el formulario
                SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
                String fechaTexto = formatoFecha.format(compra.getFecha());
                form.txtFechaCm.setText(fechaTexto);

                form.txtProveedorCm.setText(String.valueOf(compra.getProveedor().getId()));
                form.txtProductoCm.setText(String.valueOf(compra.getProducto().getId()));
                form.txtCantidadCm.setText(String.valueOf(compra.getCantidad()));
            } else {
                JOptionPane.showMessageDialog(form, "Compra no encontrada");
                limpiarCampos();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, "Error al buscar la compra: " + ex.getMessage());
        }
    }

    private void limpiarCampos() {
        form.txtIdentificadorCm.setText("");
        form.txtFechaCm.setText("");
        form.txtProveedorCm.setText("");
        form.txtProductoCm.setText("");
        form.txtCantidadCm.setText("");
    }
}
