/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import Vista.JFProducto;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Producto;
import modelo.Proveedor;
import modelo.dao.ProductoDao;
import modelo.dao.ProveedorDao;

/**
 *
 * @author estudiante
 */
public class ControlProducto implements ActionListener {

    private ProductoDao productoDao;
    private JFProducto form;
    private ArrayList<Producto> listaProductos;

    public ControlProducto(JFProducto form) {
        this.form = form;
        this.productoDao = new ProductoDao(); // Inicialización del DAO
        this.listaProductos = new ArrayList<>();
        this.actionListeners();
    }

    private void actionListeners() {
        this.form.btnGuardarP.addActionListener(this);
        this.form.btnActualizar.addActionListener(this);
        this.form.btnEliminar.addActionListener(this);
        this.form.btnBuscar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == form.btnGuardarP) {
            guardarProducto();
        } else if (e.getSource() == form.btnActualizar) {
            actualizarProducto();
        } else if (e.getSource() == form.btnEliminar) {
            eliminarProducto();
        } else if (e.getSource() == form.btnBuscar) {
            buscarProducto();
        }
    }

   private void guardarProducto() {
    try {
        // Obtener los datos del formulario
        Producto nuevoProducto = new Producto();
        nuevoProducto.setNombre(form.txtNombre.getText());
        nuevoProducto.setDescripcion(form.txtDescripcion.getText());
        nuevoProducto.setPrecio(Double.parseDouble(form.txtPrecio.getText()));

        // Convertir el proveedor de texto a objeto Proveedor (usando solo el ID)
        int proveedorId = Integer.parseInt(form.txtProveedor.getText());

        // Crear instancia de ProveedorDao para verificar si el proveedor existe
        ProveedorDao proveedorDao = new ProveedorDao();
        
        // Verificar si el proveedor existe
        if (!proveedorDao.proveedorExiste(proveedorId)) {
            JOptionPane.showMessageDialog(form, "Proveedor no válido.");
            return; // Salir si el proveedor no existe
        }

        Proveedor proveedor = new Proveedor();
        proveedor.setId(proveedorId);
        nuevoProducto.setProveedor(proveedor);

        // Guardar el producto
        productoDao.insertar(nuevoProducto);
        JOptionPane.showMessageDialog(form, "Producto guardado exitosamente");
        limpiarCampos();

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(form, "Error al guardar el producto: " + ex.getMessage());
    }
}


    private void actualizarProducto() {
        try {
            Producto producto = new Producto();
            producto.setId(Integer.parseInt(form.txtCodigo.getText()));
            producto.setNombre(form.txtNombre.getText());
            producto.setDescripcion(form.txtDescripcion.getText());
            producto.setPrecio(Double.parseDouble(form.txtPrecio.getText()));

            // Convertir el proveedor de texto a objeto Proveedor (usando solo el ID)
            Proveedor proveedor = new Proveedor();
            proveedor.setId(Integer.parseInt(form.txtProveedor.getText()));
            producto.setProveedor(proveedor);

            productoDao.actualizar(producto);
            JOptionPane.showMessageDialog(form, "Producto actualizado exitosamente");
            limpiarCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, "Error al actualizar el producto: " + ex.getMessage());
        }
    }

    private void eliminarProducto() {
        try {
            int id = Integer.parseInt(form.txtCodigo.getText());
            productoDao.eliminar(id);
            JOptionPane.showMessageDialog(form, "Producto eliminado exitosamente");
            limpiarCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, "Error al eliminar el producto: " + ex.getMessage());
        }
    }

    private void buscarProducto() {
    try {
        int id = Integer.parseInt(form.txtCodigo.getText());
        Producto producto = productoDao.buscar(id);

        if (producto != null) {
            form.txtNombre.setText(producto.getNombre());
            form.txtDescripcion.setText(producto.getDescripcion());
            form.txtPrecio.setText(String.valueOf(producto.getPrecio()));

            if (producto.getProveedor() != null) {
                form.txtProveedor.setText(String.valueOf(producto.getProveedor().getId()));
            } else {
                form.txtProveedor.setText(""); // Campo vacío si no hay proveedor
            }
            } else {
                JOptionPane.showMessageDialog(form, "Producto no encontrado");
                limpiarCampos();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, "Error al buscar el producto: " + ex.getMessage());
        }
    }



    private void limpiarCampos() {
        form.txtCodigo.setText("");
        form.txtNombre.setText("");
        form.txtDescripcion.setText("");
        form.txtPrecio.setText("");
        form.txtProveedor.setText("");
    }
}
