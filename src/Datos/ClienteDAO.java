/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;

import Conexion.Conexion;
import Datos.Interfaces.ICliente;
import Entidades.Cliente;
import Entidades.Cliente_Juridico;
import Entidades.Cliente_Natural;
import Entidades.Tipo_Cliente;
import java.sql.Connection;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.sql.SQLException;

/**
 *
 * @author leona
 */
public class ClienteDAO implements ICliente {

    private final Conexion CNX;
    private Connection cn;
    private PreparedStatement ps;
    private ResultSet rs;
    private boolean confirmacion;

    public ClienteDAO() {
        this.CNX = Conexion.getInstancia();
    }

    @Override
    public List<Cliente> listar(String texto) {
        List<Cliente> registros = new ArrayList<>();
        try {
            String sql = "SELECT "
                    + "C.Id_Cliente, "
                    + "C.DIRECCION, "
                    + "C.TELEFONO, "
                    + "TC.Id_TipoCliente, "
                    + "TC.NOMBRE AS Tipo_Cliente_Nombre, "
                    + "CN.Id_CNatural, "
                    + "CN.NOMBRE AS CN_Nombre, "
                    + "CN.DNI, "
                    + "CJ.Id_CJuridico, "
                    + "CJ.RAZON_SOCIAL, "
                    +"CJ.RUC "
                    + "FROM CLIENTE AS C "
                    + "LEFT JOIN CLIENTE_NATURAL AS CN ON C.Id_CNatural = CN.Id_CNatural "
                    + "LEFT JOIN CLIENTE_JURIDICO AS CJ ON C.Id_CJuridico = CJ.Id_CJuridico "
                    + "LEFT JOIN TIPO_CLIENTE AS TC ON C.Id_TipoCliente = TC.Id_TipoCliente "
                    + "WHERE C.Id_Cliente LIKE ? OR COALESCE(CN.NOMBRE, CJ.RAZON_SOCIAL) LIKE ?";
            ps = CNX.conectar().prepareStatement(sql);
            ps.setString(1, "%" + texto + "%");
            ps.setString(2, "%" + texto + "%"); 
            rs = ps.executeQuery();

            while (rs.next()) {
                // 1. Creacion de la clase  principal del objeto
                Cliente cliente = new Cliente(
                        rs.getInt("Id_Cliente"),
                        rs.getString("DIRECCION"),
                        rs.getString("TELEFONO")
                );

                // 2. Create and set the Tipo_Cliente object
                Tipo_Cliente tipoCliente = new Tipo_Cliente(
                        rs.getInt("Id_TipoCliente"), // Assuming Id_TipoCliente is selected
                        rs.getString("Tipo_Cliente_Nombre")
                );
                cliente.setTipoCliente(tipoCliente);

                // 3. Check if it's a Natural Client and set it
                if (rs.getInt("Id_CNatural") > 0) { // Check if Id_CNatural is not null/0
                    Cliente_Natural clienteNatural = new Cliente_Natural(
                            rs.getInt("Id_CNatural"),
                            rs.getString("CN_Nombre"),
                            rs.getString("DNI")
                    );
                    cliente.setClienteNatural(clienteNatural);
                }

                // 4. Check if it's a Juridical Client and set it
                if (rs.getInt("Id_CJuridico") > 0) { // Check if Id_CJuridico is not null/0
                    Cliente_Juridico clienteJuridico = new Cliente_Juridico(
                            rs.getInt("Id_CJuridico"),
                            rs.getString("RAZON_SOCIAL"),
                            rs.getString("RUC")
                    );
                    cliente.setClienteJuridico(clienteJuridico);
                }

                registros.add(cliente);
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar clientes: " + e.getMessage());
        } finally {
            // Ensure resources are closed even if an exception occurs
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al cerrar recursos: " + e.getMessage());
            }
            CNX.desconectar();
        }
        return registros;
    }

    @Override
    public boolean insertar(Cliente cliente) {
        confirmacion = false;
        java.sql.Connection conn = null;

        try {
            conn = (Connection) CNX.conectar();

            if (conn == null) {
                JOptionPane.showMessageDialog(null, "Error: No se pudo establecer conexión con la base de datos.");
                return false;
            }

            conn.setAutoCommit(false);

            int idNaturalGenerado = 0;
            int idJuridicoGenerado = 0;

            if (cliente.getClienteNatural() != null) {

                String sqlNatural = "INSERT INTO CLIENTE_NATURAL (NOMBRE, DNI) VALUES (?,?)";
                ps = conn.prepareStatement(sqlNatural, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, cliente.getClienteNatural().getNombre());
                ps.setString(2, cliente.getClienteNatural().getDNI());

                if (ps.executeUpdate() > 0) {
                    rs = ps.getGeneratedKeys();
                    if (rs.next()) {
                        idNaturalGenerado = rs.getInt(1);
                    }
                } else {
                    conn.rollback();
                    JOptionPane.showMessageDialog(null, "Error al insertar datos del Cliente Natural.");
                    return false;
                }
            } else if (cliente.getClienteJuridico() != null) {

                String sqlJuridico = "INSERT INTO CLIENTE_JURIDICO (RAZON_SOCIAL, RUC) VALUES (?,?)";
                ps = conn.prepareStatement(sqlJuridico, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, cliente.getClienteJuridico().getRaz_Social());
                ps.setString(2, cliente.getClienteJuridico().getRUC());

                if (ps.executeUpdate() > 0) {
                    rs = ps.getGeneratedKeys();
                    if (rs.next()) {
                        idJuridicoGenerado = rs.getInt(1);
                    }
                } else {
                    conn.rollback();
                    JOptionPane.showMessageDialog(null, "Error al insertar datos del Cliente Jurídico.");
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error: Debe especificar si el cliente es Natural o Jurídico.");
                return false;
            }

            String sqlCliente = "INSERT INTO CLIENTE (Id_CNatural, Id_CJuridico, Id_TipoCliente, Direccion, Telefono) VALUES (?,?,?,?,?)";
            ps = conn.prepareStatement(sqlCliente);

            if (idNaturalGenerado > 0) {
                ps.setInt(1, idNaturalGenerado);
                ps.setNull(2, java.sql.Types.INTEGER);
            } else {
                ps.setNull(1, java.sql.Types.INTEGER);
                ps.setInt(2, idJuridicoGenerado);
            }

            ps.setInt(3, cliente.getTipoCliente().getId_TipoCliente());
            ps.setString(4, cliente.getDireccion());
            ps.setString(5, cliente.getTelefono());

            if (ps.executeUpdate() > 0) {
                confirmacion = true;
                conn.commit();
            } else {
                conn.rollback();
                JOptionPane.showMessageDialog(null, "Error al insertar datos principales del Cliente.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error SQL general al insertar cliente: " + e.getMessage());
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al intentar revertir la transacción: " + ex.getMessage());
            }
        } finally {
            // --- PASO 3: Cierre de recursos en el bloque 'finally' ---
            try {
                if (rs != null) {
                    rs.close(); // Cerramos el ResultSet si está abierto
                }
                if (ps != null) {
                    ps.close(); // Cerramos el PreparedStatement si está abierto (se reutilizó)
                }
                if (conn != null) {
                    conn.setAutoCommit(true);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al cerrar recursos en insertar: " + ex.getMessage());
            }
            CNX.desconectar(); // Aseguramos que la conexión se desconecte o libere del pool
        }
        return confirmacion;
    }

    @Override
    public boolean eliminar(Cliente cliente) {
        confirmacion = false;
        Connection conn = null;

        try {
            conn = CNX.conectar();
            conn.setAutoCommit(false); // Inicia la transacción

            // 1. Verificar si el cliente es natural o jurídico
            String sqlTipo = "SELECT Id_CNatural, Id_CJuridico FROM CLIENTE WHERE Id_Cliente = ?";
            ps = conn.prepareStatement(sqlTipo);
            ps.setInt(1, cliente.getId_Cliente());
            rs = ps.executeQuery();

            Integer idNatural = null;
            Integer idJuridico = null;

            if (rs.next()) {
                idNatural = rs.getObject("Id_CNatural") != null ? rs.getInt("Id_CNatural") : null;
                idJuridico = rs.getObject("Id_CJuridico") != null ? rs.getInt("Id_CJuridico") : null;
            } else {
                return false; // No se encontró el cliente
            }

            // 2. Eliminar de la tabla hija correspondiente
            if (idNatural != null) {
                ps = conn.prepareStatement("DELETE FROM CLIENTE_NATURAL WHERE Id_CNatural = ?");
                ps.setInt(1, idNatural);
                ps.executeUpdate();
            } else if (idJuridico != null) {
                ps = conn.prepareStatement("DELETE FROM CLIENTE_JURIDICO WHERE Id_CJuridico = ?");
                ps.setInt(1, idJuridico);
                ps.executeUpdate();
            }

            // 3. Eliminar de la tabla CLIENTE
            ps = conn.prepareStatement("DELETE FROM CLIENTE WHERE Id_Cliente = ?");
            ps.setInt(1, cliente.getId_Cliente());
            int filasAfectadas = ps.executeUpdate();

            // Mejor alternativa: si no hubo error, consideramos todo exitoso
            conn.commit();
            confirmacion = true;

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback(); // Revertir en caso de error
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.setAutoCommit(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return confirmacion;
    }

    @Override
    public boolean modificar(Cliente cliente) {
        boolean confirmado = false;
        Connection cn = null;

        try {
            cn = CNX.conectar();
            cn.setAutoCommit(false); 

            // Actualizar tabla CLIENTE
            String sqlCliente = "UPDATE CLIENTE SET Direccion=?, Telefono=?, Id_TipoCliente=? WHERE Id_Cliente=?";
            PreparedStatement psCliente = cn.prepareStatement(sqlCliente);
            psCliente.setString(1, cliente.getDireccion());
            psCliente.setString(2, cliente.getTelefono());
            psCliente.setInt(3, cliente.getId_TipoCliente());
            psCliente.setInt(4, cliente.getId_Cliente());
            psCliente.executeUpdate();

            // Actualizar tabla CLIENTE_NATURAL
            if (cliente.getId_CNatural() != 0) {
                String sqlNatural = "UPDATE CLIENTE_NATURAL SET Nombre=?, DNI=? WHERE Id_CNatural=?";
                PreparedStatement psN = cn.prepareStatement(sqlNatural);
                psN.setString(1, cliente.getNombre());
                psN.setString(2, cliente.getDocumento());
                psN.setInt(3, cliente.getId_CNatural());
                psN.executeUpdate();
            }

            // Actualizar tabla CLIENTE_JURIDICO
            if (cliente.getId_CJuridico() != 0) {
                String sqlJuridico = "UPDATE CLIENTE_JURIDICO SET Razon_Social=?, RUC=? WHERE Id_CJuridico=?";
                PreparedStatement psJ = cn.prepareStatement(sqlJuridico);
                psJ.setString(1, cliente.getNombre());
                psJ.setString(2, cliente.getDocumento());
                psJ.setInt(3, cliente.getId_CJuridico());
                psJ.executeUpdate();
            }

            cn.commit();
            confirmado = true;

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (cn != null) {
                    cn.rollback();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } finally {
            cerrarConexiones(cn, null, null);
        }

        return confirmado;
    }

    public int[] obtenerIdDetalleCliente(int idCliente) {
        int[] ids = new int[2]; // [0] = Id_CNatural, [1] = Id_CJuridico
        String sql = "SELECT Id_CNatural, Id_CJuridico FROM CLIENTE WHERE Id_Cliente = ?";

        try {
            cn = CNX.conectar();
            ps = cn.prepareStatement(sql);
            ps.setInt(1, idCliente);
            rs = ps.executeQuery();

            if (rs.next()) {
                ids[0] = rs.getObject("Id_CNatural") != null ? rs.getInt("Id_CNatural") : 0;
                ids[1] = rs.getObject("Id_CJuridico") != null ? rs.getInt("Id_CJuridico") : 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarConexiones(cn, ps, rs);
        }

        return ids;
    }

    private void cerrarConexiones(Connection cn, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (cn != null) {
                cn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Cliente obtener(int idCliente) {
        Cliente cliente = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = CNX.conectar();
            String sql = "SELECT c.Id_Cliente, c.Id_CNatural, c.Id_CJuridico, c.Direccion, c.Telefono, "
                    + "tc.Id_TipoCliente AS Id_TipoCliente, tc.Nombre AS Nombre_TipoCliente, "
                    + "cn.Nombre AS Nombre_Natural, cn.DNI AS DNI_Natural, "
                    + "cj.Razon_Social AS RazonSocial_Juridico, cj.RUC AS RUC_Juridico "
                    + "FROM CLIENTE c "
                    + "INNER JOIN TIPO_CLIENTE tc ON c.Id_TipoCliente = tc.Id_TipoCliente "
                    + "LEFT JOIN CLIENTE_NATURAL cn ON c.Id_CNatural = cn.Id_CNatural "
                    + "LEFT JOIN CLIENTE_JURIDICO cj ON c.Id_CJuridico = cj.Id_CJuridico "
                    + "WHERE c.Id_Cliente = ?";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, idCliente);
            rs = ps.executeQuery();

            if (rs.next()) {
                cliente = new Cliente();
                cliente.setId_Cliente(rs.getInt("Id_Cliente"));
                cliente.setDireccion(rs.getString("Direccion"));
                cliente.setTelefono(rs.getString("Telefono"));

                Tipo_Cliente tipo = new Tipo_Cliente();
                tipo.setId_TipoCliente(rs.getInt("Id_TipoCliente"));
                tipo.setNombre(rs.getString("Nombre_TipoCliente"));
                cliente.setTipoCliente(tipo);

                // Carga Cliente Natural si aplica
                if (rs.getObject("Nombre_Natural") != null) {
                    Cliente_Natural cn = new Cliente_Natural();
                    cn.setId_CNatural(rs.getInt("Id_CNatural"));
                    cn.setNombre(rs.getString("Nombre_Natural"));
                    cn.setDNI(rs.getString("DNI_Natural"));
                    cliente.setClienteNatural(cn);
                    cliente.setId_CNatural(cn.getId_CNatural());
                }

                // Carga Cliente Jurídico si aplica
                if (rs.getObject("RazonSocial_Juridico") != null) {
                    Cliente_Juridico cj = new Cliente_Juridico();
                    cj.setId_CJuridico(rs.getInt("Id_CJuridico"));
                    cj.setRaz_Social(rs.getString("RazonSocial_Juridico"));
                    cj.setRUC(rs.getString("RUC_Juridico"));
                    cliente.setClienteJuridico(cj);
                    cliente.setId_CJuridico(cj.getId_CJuridico());
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener cliente: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return cliente;
    }

}
