/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package Presentacion;

import Entidades.DetalleVenta;
import Entidades.Empleado;
import Entidades.Producto;
import Entidades.Tipo_Comprobante;
import Entidades.Venta;
import Negocio.VentaNegocio;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.util.Locale;
import java.text.NumberFormat;

/**
 *
 * @author leona
 */
public class FrmVenta extends javax.swing.JInternalFrame {

    private final VentaNegocio CONTROL;
    private String accion;
    private Venta venta;
    private String resp;
    private boolean primeraCarga = true;
    private int Id_producto;
    public JFrame contenedor;

    public DefaultTableModel modeloDetalles;

    public FrmVenta(JFrame frmP) {
        initComponents();
        this.contenedor = frmP;
        this.CONTROL = new VentaNegocio();
        this.listar("");
        this.primeraCarga = false;
        TabVentas.setEnabledAt(1, false);
        this.accion = "guardar";
        this.crearDetalles();

    }

    private void listar(String texto) {
        TblVentas.setModel(this.CONTROL.listar(texto));

        TableRowSorter orden = new TableRowSorter(TblVentas.getModel());
        TblVentas.setRowSorter(orden);
        this.ocultarColumnas();
    }

    private void ocultarColumnas() {
        TblVentas.getColumnModel().getColumn(1).setMaxWidth(0);
        TblVentas.getColumnModel().getColumn(1).setMinWidth(0);
        TblVentas.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(0);
        TblVentas.getTableHeader().getColumnModel().getColumn(1).setMinWidth(0);

        TblVentas.getColumnModel().getColumn(3).setMaxWidth(0);
        TblVentas.getColumnModel().getColumn(3).setMinWidth(0);
        TblVentas.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(0);
        TblVentas.getTableHeader().getColumnModel().getColumn(3).setMinWidth(0);
    }

    private void limpiar() {
        TxtNombreC.setText("");
        TxtId.setText("");
        TxtScomprobante.setText("");
        TxtNComprobante.setText("");
        TxtImpuesto.setText("0.18");

        this.accion = "guardar";

        TxtTotal.setText("0.00");
        TxtSTotal.setText("0.00");
        TxtTImpuesto.setText("0.00");
        this.crearDetalles();
        BtnGuardar.setVisible(true);
        
        CbxTComprobante.setSelectedIndex(-1);
    }

    private void crearDetalles() {
        modeloDetalles = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                if (columna == 3) {
                    return columna == 3;// Cantidad
                }
                if (columna == 4) {
                    return columna == 4;// Precio
                }
                if (columna == 5) {
                    return columna == 5;// Descuento
                }
                return columna == 3;
            }

            @Override
            public Object getValueAt(int row, int col) {
                if (col == 6) {
                    Double cantD;
                    try {
                        cantD = Double.parseDouble((String) getValueAt(row, 3));
                    } catch (Exception e) {
                        cantD = 1.0;
                    }
                    Double precioD = Double.parseDouble((String) getValueAt(row, 4));
                    Double descuentoD = Double.parseDouble((String) getValueAt(row, 5));
                    if (cantD != null && precioD != null && descuentoD != null) {
                        return String.format("%.2f", (cantD * precioD) - descuentoD);
                    } else {
                        return 0;
                    }
                }
                return super.getValueAt(row, col);
            }

            @Override
            public void setValueAt(Object aValue, int row, int col) {
                super.setValueAt(aValue, row, col);
                try {
                    int cantD = Integer.parseInt((String) getValueAt(row, 3));
                    int stockD = Integer.parseInt((String) getValueAt(row, 2));
                    if (cantD > stockD) {
                        super.setValueAt(stockD, row, 3);
                        JOptionPane.showMessageDialog(null, "La cantidad a vender no puede superar el stock. usted puede vender como máximo: " + stockD);

                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                calcularTotales();
                fireTableDataChanged();
            }

        };

        modeloDetalles.setColumnIdentifiers(new Object[]{"Id", "ARTICULO", "STOCK", "CANTIDAD", "PRECIO", "DESCUENTO", "SUBTOTAL"});
        TblDVentas.setModel(modeloDetalles);
    }

    public void agregarDetalles(String id, String nombre, String stock, String precio, String descuento) {
        String idT;
        boolean existe = false;
        for (int i = 0; i < this.modeloDetalles.getRowCount(); i++) {
            idT = String.valueOf(this.modeloDetalles.getValueAt(i, 0));
            if (idT.equals(id)) {
                existe = true;
            }
        }
        if (existe) {
            JOptionPane.showMessageDialog(null, "El artículo ya ha sido agregado.");
        } else {
            this.modeloDetalles.addRow(new Object[]{id, nombre, stock, "1", precio, descuento, precio});
            this.calcularTotales();
        }
    }

    private void calcularTotales() {
        double total = 0;
        double subTotal = 0;
        int items = modeloDetalles.getRowCount();

        if (items == 0) {
            total = 0;
        } else {
            for (int i = 0; i < items; i++) {
                Object valorCelda = modeloDetalles.getValueAt(i, 6);
                if (valorCelda != null) {
                    try {
                        String valor = valorCelda.toString().trim().replace(".", "").replace(",", ".");
                        double subtotalLinea = Double.parseDouble(valor);
                        total += subtotalLinea;
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Error de formato de número en la tabla: El valor '" + valorCelda + "' no se pudo interpretar correctamente. Detalles: " + e.getMessage(), "Error de Datos", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }

        double impuesto = 0.0;
        String textoImpuesto = TxtImpuesto.getText();

        if (textoImpuesto == null || textoImpuesto.trim().isEmpty()) {
            impuesto = 0.0;
        } else {
            try {
                impuesto = Double.parseDouble(textoImpuesto.replace(",", ".").trim());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error de formato: El impuesto '" + textoImpuesto + "' no es un número válido. Se asumirá 0. Detalles: " + e.getMessage(), "Error de Entrada", JOptionPane.ERROR_MESSAGE);
                impuesto = 0.0;
            }
        }

        // Si el impuesto viene como porcentaje, conviértelo a decimal.
        if (impuesto > 1) {
            impuesto = impuesto / 100.0;
        }
        subTotal = total;
        double totalImpuesto = subTotal * impuesto;
        double totalFinal = subTotal + totalImpuesto;

        TxtSTotal.setText(String.format("%.2f", subTotal));
        TxtTImpuesto.setText(String.format("%.2f", totalImpuesto));
        TxtTotal.setText(String.format("%.2f", totalFinal));
    }

    /**
     * Creates new form FrmVenta
     *
     *
     * /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TabVentas = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        TxtBuscar = new javax.swing.JTextField();
        BtnBuscar = new javax.swing.JButton();
        BtnNuevo = new javax.swing.JButton();
        BtnVventa = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TblVentas = new javax.swing.JTable();
        BtnAnular = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        TxtId = new javax.swing.JTextField();
        TxtNombreC = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        TxtImpuesto = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        TxtScomprobante = new javax.swing.JTextField();
        CbxTComprobante = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        TxtNComprobante = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        TxtNProducto = new javax.swing.JTextField();
        BtnVProductos = new javax.swing.JButton();
        BtnVClientes = new javax.swing.JButton();
        BtnQuitar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        TblDVentas = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        TxtSTotal = new javax.swing.JTextField();
        TxtTImpuesto = new javax.swing.JTextField();
        TxtTotal = new javax.swing.JTextField();
        BtnCancelar = new javax.swing.JButton();
        BtnGuardar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Mantenimiento de Ventas");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Cliente ");

        BtnBuscar.setForeground(new java.awt.Color(0, 0, 0));
        BtnBuscar.setText("Buscar");
        BtnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBuscarActionPerformed(evt);
            }
        });

        BtnNuevo.setForeground(new java.awt.Color(0, 0, 0));
        BtnNuevo.setText("Nuevo");
        BtnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNuevoActionPerformed(evt);
            }
        });

        BtnVventa.setForeground(new java.awt.Color(0, 0, 0));
        BtnVventa.setText("Ver Venta");
        BtnVventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnVventaActionPerformed(evt);
            }
        });

        TblVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(TblVentas);

        BtnAnular.setForeground(new java.awt.Color(0, 0, 0));
        BtnAnular.setText("Anular");
        BtnAnular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAnularActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel1)
                        .addGap(33, 33, 33)
                        .addComponent(TxtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(BtnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(78, 78, 78)
                        .addComponent(BtnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 320, Short.MAX_VALUE)
                        .addComponent(BtnVventa, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(BtnAnular)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(TxtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnBuscar)
                    .addComponent(BtnNuevo)
                    .addComponent(BtnVventa))
                .addGap(28, 28, 28)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(BtnAnular)
                .addContainerGap(147, Short.MAX_VALUE))
        );

        TabVentas.addTab("LISTADO", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setForeground(new java.awt.Color(0, 0, 0));

        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Cliente (*)");

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Impuesto");

        TxtImpuesto.setText("0.18");

        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Tipo de Comprobante (*)");

        CbxTComprobante.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Boleta", "Factura" }));
        CbxTComprobante.setSelectedIndex(-1);
        CbxTComprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CbxTComprobanteActionPerformed(evt);
            }
        });

        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Serie de Comprobante");

        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Número de Comprobante");

        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Producto");

        BtnVProductos.setForeground(new java.awt.Color(0, 0, 0));
        BtnVProductos.setText("Ver");
        BtnVProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnVProductosActionPerformed(evt);
            }
        });

        BtnVClientes.setText("...");
        BtnVClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnVClientesActionPerformed(evt);
            }
        });

        BtnQuitar.setForeground(new java.awt.Color(0, 0, 0));
        BtnQuitar.setText("Quitar");
        BtnQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnQuitarActionPerformed(evt);
            }
        });

        TblDVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(TblDVentas);

        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("SubTotal");

        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Total Impuesto");

        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Total");

        BtnCancelar.setForeground(new java.awt.Color(0, 0, 0));
        BtnCancelar.setText("Cancelar");
        BtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCancelarActionPerformed(evt);
            }
        });

        BtnGuardar.setForeground(new java.awt.Color(0, 0, 0));
        BtnGuardar.setText("Guardar");
        BtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGuardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(TxtNProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(BtnVProductos))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(TxtId, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(TxtNombreC, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(37, 37, 37)
                                .addComponent(CbxTComprobante, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(TxtScomprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48)
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(BtnQuitar)
                                    .addComponent(TxtNComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(BtnVClientes)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 557, Short.MAX_VALUE)
                                .addComponent(jLabel3)))))
                .addGap(18, 18, 18)
                .addComponent(TxtImpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(109, 109, 109))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(BtnCancelar)
                        .addGap(54, 54, 54)
                        .addComponent(BtnGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(65, 65, 65)
                                .addComponent(TxtSTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel9))
                                .addGap(29, 29, 29)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TxtTImpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TxtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(32, 32, 32))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(TxtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtNombreC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(TxtImpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnVClientes))
                .addGap(39, 39, 39)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(CbxTComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(TxtScomprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(TxtNComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(TxtNProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnVProductos)
                    .addComponent(BtnQuitar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(TxtSTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(TxtTImpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(TxtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(27, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BtnGuardar)
                            .addComponent(BtnCancelar))
                        .addGap(45, 45, 45))))
        );

        TabVentas.addTab("MANTENIMIENTO", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(TabVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 1224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TabVentas)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBuscarActionPerformed
        this.listar(TxtBuscar.getText());
    }//GEN-LAST:event_BtnBuscarActionPerformed

    private void BtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCancelarActionPerformed
        TabVentas.setEnabledAt(0, true);
        TabVentas.setEnabledAt(1, false);
        TabVentas.setSelectedIndex(0);
        this.limpiar();
    }//GEN-LAST:event_BtnCancelarActionPerformed

    private void BtnVventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnVventaActionPerformed
        if (TblVentas.getSelectedRowCount() == 1) {
            String id = String.valueOf(TblVentas.getValueAt(TblVentas.getSelectedRow(), 0));
            String idCliente = String.valueOf(TblVentas.getValueAt(TblVentas.getSelectedRow(), 1));
            String nombreCliente = String.valueOf(TblVentas.getValueAt(TblVentas.getSelectedRow(), 2));
            String tipoComprobante = String.valueOf(TblVentas.getValueAt(TblVentas.getSelectedRow(), 3));
            String serie = String.valueOf(TblVentas.getValueAt(TblVentas.getSelectedRow(), 4));
            String numero = String.valueOf(TblVentas.getValueAt(TblVentas.getSelectedRow(), 5));
            String impuesto = String.valueOf(TblVentas.getValueAt(TblVentas.getSelectedRow(), 7));

            TxtId.setText(idCliente);
            TxtNombreC.setText(nombreCliente);
            // Selecciona el tipo de comprobante sin importar el uso de mayúsculas
            for (int i = 0; i < CbxTComprobante.getItemCount(); i++) {
                String item = CbxTComprobante.getItemAt(i);
                if (item.equalsIgnoreCase(tipoComprobante)) {
                    CbxTComprobante.setSelectedIndex(i);
                    break;
                }
            }
            TxtScomprobante.setText(serie);
            TxtNComprobante.setText(numero);
            TxtImpuesto.setText(impuesto);
            this.modeloDetalles = CONTROL.listarDetalle(Integer.parseInt(id));
            TblDVentas.setModel(modeloDetalles);

            this.calcularTotales();

            TabVentas.setEnabledAt(1, true);
            TabVentas.setEnabledAt(0, false);
            TabVentas.setSelectedIndex(1);
            BtnGuardar.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione la venta a mostrar.");
        }
    }//GEN-LAST:event_BtnVventaActionPerformed
    private void obtenerNumero() {
        String tipoComprobante = (String) CbxTComprobante.getSelectedItem();
        String serieComprobante = this.CONTROL.ultimoSerie(tipoComprobante);
        String numComprobante = this.CONTROL.ultimoNumero(tipoComprobante, serieComprobante);
        TxtScomprobante.setText(serieComprobante);
        if (numComprobante.equals("")) {
            TxtNComprobante.setText("");
        } else {
            int num = Integer.parseInt(numComprobante) + 1;
            int length = numComprobante.length();
            String formattedNum = String.format("%0" + length + "d", num
            );
            TxtNComprobante.setText(formattedNum);
        }
    }
    private void BtnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNuevoActionPerformed
        TabVentas.setEnabledAt(1, true);
        TabVentas.setEnabledAt(0, false);
        TabVentas.setSelectedIndex(1);
        this.accion = "guardar";
        BtnGuardar.setText("Guardar");
        this.limpiar();
    }//GEN-LAST:event_BtnNuevoActionPerformed

    private void BtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGuardarActionPerformed
        if (TxtId.getText().length() == 0) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un cliente.", "Sistema", JOptionPane.WARNING_MESSAGE);
            BtnVClientes.requestFocus();
            return;
        }
        if (TxtScomprobante.getText().length() > 7) {
            JOptionPane.showMessageDialog(this, "Debes ingresar una serie no mayor a 7 caracteres.", "Sistema", JOptionPane.WARNING_MESSAGE);
            TxtScomprobante.requestFocus();
            return;
        }
        if (TxtNComprobante.getText().length() == 0 || TxtNComprobante.getText().length() > 10) {
            JOptionPane.showMessageDialog(this, "Debes ingresar un número de comprobante no mayor a 10 caracteres.", "Sistema", JOptionPane.WARNING_MESSAGE);
            TxtNComprobante.requestFocus();
            return;
        }
        if (modeloDetalles.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Debes agregar artículos al detalle.", "Sistema", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String resp = "";
        int correlativo = Integer.parseInt(TxtNComprobante.getText().trim());
        String numComprobante = String.format("%05d", correlativo);
        TxtNComprobante.setText(numComprobante);
        resp = this.CONTROL.insertarVenta(Integer.parseInt(TxtId.getText()), (String) CbxTComprobante.getSelectedItem(), TxtScomprobante.getText(), numComprobante, Double.parseDouble(TxtImpuesto.getText().replace(",", ".")), Double.parseDouble(TxtTotal.getText().replace(",", ".")), modeloDetalles);
        if (resp.equals("OK")) {
            JOptionPane.showMessageDialog(null, "Registrado correctamente");
            this.limpiar();
            this.listar("");

        } else {
            JOptionPane.showMessageDialog(null, resp);
        }
    }//GEN-LAST:event_BtnGuardarActionPerformed

    private void BtnAnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAnularActionPerformed
        if (TblVentas.getSelectedRowCount() == 1) {
            String id = String.valueOf(TblVentas.getValueAt(TblVentas.getSelectedRow(), 0));
            String comprobante = String.valueOf(TblVentas.getValueAt(TblVentas.getSelectedRow(), 3));
            String serie = String.valueOf(TblVentas.getValueAt(TblVentas.getSelectedRow(), 4));
            String numero = String.valueOf(TblVentas.getValueAt(TblVentas.getSelectedRow(), 5));

            if (JOptionPane.showConfirmDialog(this, "Deseas anular el registro: " + comprobante + " " + serie + "-" + numero + " ?", "Anular", JOptionPane.YES_NO_OPTION) == 0) {
                String resp = this.CONTROL.anular(Integer.parseInt(id));
                if (resp.equals("OK")) {
                    JOptionPane.showMessageDialog(null, "Registro anulado");
                    this.listar("");
                } else {
                    JOptionPane.showMessageDialog(null, resp);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione 1 registro a Anular.");
        }
    }//GEN-LAST:event_BtnAnularActionPerformed


    private void BtnVClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnVClientesActionPerformed
        FrmClientesVenta frm = new FrmClientesVenta(contenedor, this, true);
        frm.toFront();

    }//GEN-LAST:event_BtnVClientesActionPerformed

    private void BtnVProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnVProductosActionPerformed
        FrmArticulosVenta frm = new FrmArticulosVenta(contenedor, this, true);
        frm.toFront();
    }//GEN-LAST:event_BtnVProductosActionPerformed

    private void BtnQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnQuitarActionPerformed
        if (TblDVentas.getSelectedRowCount() == 1) {
            this.modeloDetalles.removeRow(TblDVentas.getSelectedRow());
            this.calcularTotales();
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione el detalle a quitar.");
        }
    }//GEN-LAST:event_BtnQuitarActionPerformed

    private void CbxTComprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CbxTComprobanteActionPerformed
        this.obtenerNumero();
    }//GEN-LAST:event_CbxTComprobanteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnAnular;
    private javax.swing.JButton BtnBuscar;
    private javax.swing.JButton BtnCancelar;
    private javax.swing.JButton BtnGuardar;
    private javax.swing.JButton BtnNuevo;
    private javax.swing.JButton BtnQuitar;
    private javax.swing.JButton BtnVClientes;
    private javax.swing.JButton BtnVProductos;
    private javax.swing.JButton BtnVventa;
    private javax.swing.JComboBox<String> CbxTComprobante;
    private javax.swing.JTabbedPane TabVentas;
    private javax.swing.JTable TblDVentas;
    private javax.swing.JTable TblVentas;
    private javax.swing.JTextField TxtBuscar;
    public javax.swing.JTextField TxtId;
    private javax.swing.JTextField TxtImpuesto;
    private javax.swing.JTextField TxtNComprobante;
    private javax.swing.JTextField TxtNProducto;
    public javax.swing.JTextField TxtNombreC;
    private javax.swing.JTextField TxtSTotal;
    private javax.swing.JTextField TxtScomprobante;
    private javax.swing.JTextField TxtTImpuesto;
    private javax.swing.JTextField TxtTotal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
