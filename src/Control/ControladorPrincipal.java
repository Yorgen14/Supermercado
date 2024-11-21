package control;

import vista.FormularioMain;
import Vista.JFCliente;
import Vista.JFCompra;
import Vista.JFProducto;
import Vista.JFProveedor;
import Vista.JFVenta;
import control.ControlProveedor;
import control.ControlCompra;
import control.ControlProducto;
import control.ControlVenta;

public class ControladorPrincipal {
    private FormularioMain mainForm;

    public ControladorPrincipal() {
        mainForm = new FormularioMain();
        mainForm.setVisible(true);
        initListeners();
    }

    private void initListeners() {
        mainForm.btnCliente.addActionListener(evt -> abrirCliente());
        mainForm.btnProducto.addActionListener(evt -> abrirProducto());
        mainForm.btnProveedor.addActionListener(evt -> abrirProveedor());
        mainForm.btnCompra.addActionListener(evt -> abrirCompra());
        mainForm.btnVenta.addActionListener(evt -> abrirVenta());
    }

    private void abrirCliente() {
        JFCliente clienteForm = new JFCliente();
        clienteForm.setVisible(true);
        // Inicializar el controlador del cliente
        ControlCliente controlCliente = new ControlCliente(clienteForm);
    }

    private void abrirProducto() {
        JFProducto productoForm = new JFProducto();
        productoForm.setVisible(true);
        // Inicializar el controlador del producto
        ControlProducto controlProducto = new ControlProducto(productoForm);
    }

    private void abrirProveedor() {
        JFProveedor proveedorForm = new JFProveedor();
        proveedorForm.setVisible(true);
        // Inicializar el controlador del proveedor
        ControlProveedor controlProveedor = new ControlProveedor(proveedorForm);
    }

    private void abrirCompra() {
        JFCompra compraForm = new JFCompra();
        compraForm.setVisible(true);
        // Inicializar el controlador de la compra
        ControlCompra controlCompra = new ControlCompra(compraForm);
    }

    private void abrirVenta() {
        JFVenta ventaForm = new JFVenta();
        ventaForm.setVisible(true);
        // Inicializar el controlador de la venta
        ControlVenta controlVenta = new ControlVenta(ventaForm);
    }

}
