package control;

import Vista.JFCliente;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.Cliente;
import modelo.Recomendacion;
import modelo.dao.ClienteDao;

public class ControlCliente implements ActionListener {

    private ClienteDao clienteDao;
    private JFCliente form;

    public ControlCliente(JFCliente form) {
        this.form = form;
        this.clienteDao = new ClienteDao(); // Inicialización de ClienteDao
        actionListeners(); // Método para configurar los listeners
    }

    // Asocia botones del formulario a este controlador
    private void actionListeners() {
        this.form.btnGuardarC.addActionListener(this);
        this.form.btnActualizarC.addActionListener(this);
        this.form.btnEliminarC.addActionListener(this);
        this.form.btnBuscarC.addActionListener(this);
    }

    // Método manejador de eventos
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == form.btnGuardarC) {
            guardarCliente();
        } else if (e.getSource() == form.btnActualizarC) {
            actualizarCliente();
        } else if (e.getSource() == form.btnEliminarC) {
            eliminarCliente();
        } else if (e.getSource() == form.btnBuscarC) {
            buscarCliente();
        }
    }

    // Método para guardar un cliente
    // Método para guardar un cliente
private void guardarCliente() {
    try {
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setId(Integer.parseInt(form.txtCedulaC.getText())); // Cedula como ID
        nuevoCliente.setNombre(form.txtNombreC.getText());
        nuevoCliente.setEmail(form.txtEmailC.getText());
        nuevoCliente.setTelefono(form.txtTelefonoC.getText());

        // Obtener el valor de "Recomendado por" como Integer
        Integer recomendadoPor = null;
        String recoText = form.txtRecoC.getText();
        if (!recoText.isEmpty()) {
            recomendadoPor = Integer.parseInt(recoText);
        }
        nuevoCliente.setRecomendadoPor(recomendadoPor);

        // Si tiene un recomendado, preguntar el porcentaje
        Double porcentaje = null;
        if (recomendadoPor != null) {
            String porcentajeText = form.txtPorcentaje.getText();  // Suponiendo que tienes un campo txtPorcentajeC
            if (!porcentajeText.isEmpty()) {
                porcentaje = Double.parseDouble(porcentajeText);
            }
        }

        clienteDao.insertar(nuevoCliente); // Inserción en la base de datos
        
        // Si existe un recomendado, insertar la recomendación
        if (recomendadoPor != null) {
            Recomendacion recomendacion = new Recomendacion();
            recomendacion.setClienteId(nuevoCliente.getId());
            recomendacion.setClienteRecomendadoId(recomendadoPor);
            recomendacion.setPorcentaje(porcentaje);  // Solo se agrega si existe el porcentaje
            clienteDao.insertarRecomendacion(recomendacion);
        }

        JOptionPane.showMessageDialog(form, "Cliente guardado exitosamente");
        limpiarCampos();
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(form, "Error al guardar el cliente: " + ex.getMessage());
    }
}


    // Método para actualizar un cliente
    private void actualizarCliente() {
        try {
            Cliente cliente = new Cliente();
            cliente.setId(Integer.parseInt(form.txtCedulaC.getText())); // Cedula como ID
            cliente.setNombre(form.txtNombreC.getText());
            cliente.setEmail(form.txtEmailC.getText());
            cliente.setTelefono(form.txtTelefonoC.getText());

            // Obtener el valor de "Recomendado por" como Integer
            Integer recomendadoPor = null;
            String recoText = form.txtRecoC.getText();
            if (!recoText.isEmpty()) {
                recomendadoPor = Integer.parseInt(recoText);
            }
            cliente.setRecomendadoPor(recomendadoPor);

            clienteDao.actualizar(cliente); // Actualización en la base de datos
            JOptionPane.showMessageDialog(form, "Cliente actualizado exitosamente");
            limpiarCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, "Error al actualizar el cliente: " + ex.getMessage());
        }
    }

    // Método para buscar un cliente por ID
    private void buscarCliente() {
        try {
            int id = Integer.parseInt(form.txtCedulaC.getText()); // Cedula como ID
            Cliente cliente = clienteDao.buscar(id);

            if (cliente != null) {
                form.txtNombreC.setText(cliente.getNombre());
                form.txtEmailC.setText(cliente.getEmail());
                form.txtTelefonoC.setText(cliente.getTelefono());

                // Si existe un "Recomendado por", mostrarlo
                if (cliente.getRecomendadoPor() != null) {
                    form.txtRecoC.setText(cliente.getRecomendadoPor().toString());
                } else {
                    form.txtRecoC.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(form, "Cliente no encontrado");
                limpiarCampos();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, "Error al buscar el cliente: " + ex.getMessage());
        }
    }

    // Método para eliminar un cliente por ID
    private void eliminarCliente() {
        try {
            int id = Integer.parseInt(form.txtCedulaC.getText()); // Cedula como ID
            clienteDao.eliminar(id); // Eliminación en la base de datos
            JOptionPane.showMessageDialog(form, "Cliente eliminado exitosamente");
            limpiarCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(form, "Error al eliminar el cliente: " + ex.getMessage());
        }
    }

    // Método para limpiar los campos del formulario
    private void limpiarCampos() {
        form.txtCedulaC.setText("");
        form.txtNombreC.setText("");
        form.txtEmailC.setText("");
        form.txtTelefonoC.setText("");
        form.txtRecoC.setText(""); // Limpiar campo de recomendación
        form.txtPorcentaje.setText("");
    }
}
