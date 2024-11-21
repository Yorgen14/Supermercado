/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import Vista.JFProveedor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Proveedor;
import modelo.dao.ProveedorDao;

/**
 *
 * @author estudiante
 */
public class ControlProveedor implements ActionListener {

    private ProveedorDao proveedorDao;
    private JFProveedor form;
    private ArrayList<ProveedorDao> listaProveedor;

    public ControlProveedor(JFProveedor form) {
    this.form = form;
    this.proveedorDao = new ProveedorDao(); // Asegúrate de inicializarlo aquí
    this.listaProveedor = new ArrayList<>();
    this.actionListeners();
    }

    
    private void actionListeners() {
        this.form.btnGuardarPR.addActionListener(this);
        this.form.btnActualizarPR.addActionListener(this);
        this.form.btnEliminarPR.addActionListener(this);
        this.form.btnBuscarPR.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == form.btnGuardarPR) {
            guardarProveedor();
        } else if (e.getSource() == form.btnActualizarPR) {
            actualizarProveedor();
        } else if (e.getSource() == form.btnEliminarPR) {
            eliminarProveedor();
        } else if (e.getSource() == form.btnBuscarPR) {
            buscarProveedor();
        }
    }
    
    private void guardarProveedor() {
        try {
            Proveedor nuevoProveedor = new Proveedor();
            nuevoProveedor.setNomnbre(form.txtNombrePr.getText());
            nuevoProveedor.setContacto(form.txtContactoPr.getText());
            nuevoProveedor.setTelefono(form.txtTelefonoPr.getText());

            proveedorDao.insertar(nuevoProveedor);
            JOptionPane.showMessageDialog(form, "Proveedor guardado exitosamente");
            limpiarCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, "Error al guardar el proveedor: " + ex.getMessage());
        }
    }

    private void actualizarProveedor() {
        try {
            Proveedor proveedor = new Proveedor();
            proveedor.setId(Integer.parseInt(form.txtIdentificadorP.getText()));
            proveedor.setNomnbre(form.txtNombrePr.getText());
            proveedor.setContacto(form.txtContactoPr.getText());
            proveedor.setTelefono(form.txtTelefonoPr.getText());

            proveedorDao.actualizar(proveedor);
            JOptionPane.showMessageDialog(form, "Proveedor actualizado exitosamente");
            limpiarCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, "Error al actualizar el proveedor: " + ex.getMessage());
        }
    }

    private void eliminarProveedor() {
        try {
            int id = Integer.parseInt(form.txtIdentificadorP.getText());
            proveedorDao.eliminar(id);
            JOptionPane.showMessageDialog(form, "Proveedor eliminado exitosamente");
            limpiarCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, "Error al eliminar el proveedor: " + ex.getMessage());
        }
    }

    private void buscarProveedor() {
        try {
            int id = Integer.parseInt(form.txtIdentificadorP.getText());
            Proveedor proveedor = proveedorDao.buscar(id);

            if (proveedor != null) {
                form.txtNombrePr.setText(proveedor.getNomnbre());
                form.txtContactoPr.setText(proveedor.getContacto());
                form.txtTelefonoPr.setText(proveedor.getTelefono());
            } else {
                JOptionPane.showMessageDialog(form, "Proveedor no encontrado");
                limpiarCampos();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, "Error al buscar el proveedor: " + ex.getMessage());
        }
    }

    private void limpiarCampos() {
        form.txtIdentificadorP.setText("");
        form.txtNombrePr.setText("");
        form.txtContactoPr.setText("");
        form.txtTelefonoPr.setText("");
    }  
}
