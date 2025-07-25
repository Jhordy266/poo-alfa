package Presentacion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Simple purchase form to register product stock entries.
 */
public class FrmCompra extends JInternalFrame {
    private final JTable tblCompras = new JTable();
    private final JTable tblDetalles = new JTable();

    private final JTextField txtBuscar = new JTextField(20);
    private final JButton btnBuscar = new JButton("Buscar");
    private final JButton btnNuevo = new JButton("Nuevo");

    private final JTextField txtIdProducto = new JTextField(5);
    private final JTextField txtNombreProducto = new JTextField(15);
    private final JTextField txtCantidad = new JTextField(5);
    private final JTextField txtCategoria = new JTextField(10);
    private final JTextField txtProveedor = new JTextField(15);
    private final JTextField txtEmpleado = new JTextField(15);
    private final JTextField txtCosto = new JTextField(6);
    private final JButton btnAgregar = new JButton("Agregar");
    private final JButton btnQuitar = new JButton("Quitar");

    private final JTextField txtTotal = new JTextField(10);
    private final JButton btnGuardar = new JButton("Guardar");
    private final JButton btnCancelar = new JButton("Cancelar");

    private DefaultTableModel modeloDetalles;

    public FrmCompra() {
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Registro de Compras");
        setSize(800, 600);

        JTabbedPane tab = new JTabbedPane();
        tab.addTab("LISTADO", crearPanelListado());
        tab.addTab("MANTENIMIENTO", crearPanelMantenimiento());

        getContentPane().add(tab, BorderLayout.CENTER);

        crearModelo();
    }

    private JPanel crearPanelListado() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel top = new JPanel();
        top.add(new JLabel("Buscar:"));
        top.add(txtBuscar);
        top.add(btnBuscar);
        top.add(btnNuevo);
        panel.add(top, BorderLayout.NORTH);
        panel.add(new JScrollPane(tblCompras), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelMantenimiento() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel form = new JPanel();
        form.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.anchor = GridBagConstraints.WEST;

        int y = 0;

        gbc.gridx = 0; gbc.gridy = y; form.add(new JLabel("ID Producto"), gbc);
        gbc.gridx = 1; gbc.gridy = y; form.add(txtIdProducto, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; form.add(new JLabel("Nombre"), gbc);
        gbc.gridx = 1; gbc.gridy = y; form.add(txtNombreProducto, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; form.add(new JLabel("Cantidad"), gbc);
        gbc.gridx = 1; gbc.gridy = y; form.add(txtCantidad, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; form.add(new JLabel("Categoría"), gbc);
        gbc.gridx = 1; gbc.gridy = y; form.add(txtCategoria, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; form.add(new JLabel("Proveedor"), gbc);
        gbc.gridx = 1; gbc.gridy = y; form.add(txtProveedor, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; form.add(new JLabel("Empleado"), gbc);
        gbc.gridx = 1; gbc.gridy = y; form.add(txtEmpleado, gbc); y++;

        gbc.gridx = 0; gbc.gridy = y; form.add(new JLabel("Costo U."), gbc);
        gbc.gridx = 1; gbc.gridy = y; form.add(txtCosto, gbc);
        gbc.gridx = 2; gbc.gridy = y; form.add(btnAgregar, gbc);
        gbc.gridx = 3; gbc.gridy = y; form.add(btnQuitar, gbc); y++;

        panel.add(form, BorderLayout.NORTH);
        panel.add(new JScrollPane(tblDetalles), BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.add(new JLabel("Total:"));
        txtTotal.setEditable(false);
        bottom.add(txtTotal);
        bottom.add(btnGuardar);
        bottom.add(btnCancelar);
        panel.add(bottom, BorderLayout.SOUTH);

        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarDetalle();
            }
        });

        btnQuitar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quitarDetalle();
            }
        });

        return panel;
    }

    private void crearModelo() {
        modeloDetalles = new DefaultTableModel(
                new Object[]{"ID", "Producto", "Cantidad", "Categoría", "Proveedor", "Costo U.", "Subtotal"},
                0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2 || column == 5; // cantidad y costo
            }
        };
        tblDetalles.setModel(modeloDetalles);
    }

    private void agregarDetalle() {
        String id = txtIdProducto.getText().trim();
        String nombre = txtNombreProducto.getText().trim();
        String categoria = txtCategoria.getText().trim();
        String proveedor = txtProveedor.getText().trim();
        int cant = 0;
        double costo = 0;
        try {
            cant = Integer.parseInt(txtCantidad.getText().trim());
            costo = Double.parseDouble(txtCosto.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Cantidad y costo deben ser numéricos");
            return;
        }
        double subtotal = cant * costo;
        modeloDetalles.addRow(new Object[]{id, nombre, cant, categoria, proveedor, String.format("%.2f", costo), String.format("%.2f", subtotal)});
        calcularTotal();
    }

    private void quitarDetalle() {
        int fila = tblDetalles.getSelectedRow();
        if (fila >= 0) {
            modeloDetalles.removeRow(fila);
            calcularTotal();
        }
    }

    private void calcularTotal() {
        double total = 0;
        for (int i = 0; i < modeloDetalles.getRowCount(); i++) {
            Object val = modeloDetalles.getValueAt(i, 6);
            if (val != null) {
                try {
                    total += Double.parseDouble(val.toString());
                } catch (NumberFormatException ignored) {
                }
            }
        }
        txtTotal.setText(String.format("%.2f", total));
    }
}
