package com.saborpaisa.dao;

import com.saborpaisa.conexion.Conexion;
import com.saborpaisa.modelo.Local;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO (Data Access Object) para realizar operaciones CRUD
 * sobre la tabla LOCAL en la base de datos.
 */
public class LocalDAO {

    /**
     * Inserta un nuevo registro de Local en la base de datos.
     * 
     * @param local objeto Local con los datos a insertar
     * @return true si la inserción fue exitosa, false en caso contrario
     */
    public boolean insertar(Local local) {
        String sql = "INSERT INTO LOCAL (NOMBRE_COMERCIAL, DIRECCION, TELEFONO, GERENTE, HORA_APERTURA, HORA_CIERRE) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, local.getNombreComercial());
            pstmt.setString(2, local.getDireccion());
            pstmt.setString(3, local.getTelefono());
            pstmt.setString(4, local.getGerente());
            pstmt.setTime(5, local.getHoraApertura());
            pstmt.setTime(6, local.getHoraCierre());
            
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("✓ Local insertado exitosamente");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("✗ Error al insertar local: " + e.getMessage());
        }
        return false;
    }

    /**
     * Actualiza un registro existente de Local en la base de datos.
     * 
     * @param local objeto Local con los datos actualizados
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    public boolean actualizar(Local local) {
        String sql = "UPDATE LOCAL SET NOMBRE_COMERCIAL = ?, DIRECCION = ?, TELEFONO = ?, " +
                     "GERENTE = ?, HORA_APERTURA = ?, HORA_CIERRE = ? WHERE ID_LOCAL = ?";
        
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, local.getNombreComercial());
            pstmt.setString(2, local.getDireccion());
            pstmt.setString(3, local.getTelefono());
            pstmt.setString(4, local.getGerente());
            pstmt.setTime(5, local.getHoraApertura());
            pstmt.setTime(6, local.getHoraCierre());
            pstmt.setInt(7, local.getIdLocal());
            
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("✓ Local actualizado exitosamente");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("✗ Error al actualizar local: " + e.getMessage());
        }
        return false;
    }

    /**
     * Elimina un registro de Local de la base de datos por su ID.
     * 
     * @param idLocal ID del local a eliminar
     * @return true si la eliminación fue exitosa, false en caso contrario
     */
    public boolean eliminar(int idLocal) {
        // Primero verificar si existen pedidos asociados
        String sqlVerificar = "SELECT COUNT(*) FROM PEDIDO WHERE ID_LOCAL = ?";
        String sqlEliminar = "DELETE FROM LOCAL WHERE ID_LOCAL = ?";
        
        try (Connection conn = Conexion.obtenerConexion()) {
            
            // Verificar si hay pedidos asociados
            try (PreparedStatement pstmtVerificar = conn.prepareStatement(sqlVerificar)) {
                pstmtVerificar.setInt(1, idLocal);
                try (ResultSet rs = pstmtVerificar.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        System.err.println("✗ No se puede eliminar: el local tiene pedidos asociados");
                        throw new SQLException("No se puede eliminar el local porque tiene pedidos asociados. Elimine primero los pedidos relacionados.");
                    }
                }
            }
            
            // Si no hay pedidos, proceder con la eliminación
            try (PreparedStatement pstmtEliminar = conn.prepareStatement(sqlEliminar)) {
                pstmtEliminar.setInt(1, idLocal);
                
                int filasAfectadas = pstmtEliminar.executeUpdate();
                
                if (filasAfectadas > 0) {
                    System.out.println("✓ Local eliminado exitosamente");
                    return true;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("✗ Error al eliminar local: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return false;
    }

    /**
     * Consulta todos los registros de Local en la base de datos.
     * 
     * @return Lista de objetos Local con todos los registros
     */
    public List<Local> listarTodos() {
        List<Local> locales = new ArrayList<>();
        String sql = "SELECT * FROM LOCAL ORDER BY ID_LOCAL";
        
        try (Connection conn = Conexion.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Local local = new Local();
                local.setIdLocal(rs.getInt("ID_LOCAL"));
                local.setNombreComercial(rs.getString("NOMBRE_COMERCIAL"));
                local.setDireccion(rs.getString("DIRECCION"));
                local.setTelefono(rs.getString("TELEFONO"));
                local.setGerente(rs.getString("GERENTE"));
                local.setHoraApertura(rs.getTime("HORA_APERTURA"));
                local.setHoraCierre(rs.getTime("HORA_CIERRE"));
                
                locales.add(local);
            }
            
            System.out.println("✓ Se consultaron " + locales.size() + " locales");
        } catch (SQLException e) {
            System.err.println("✗ Error al consultar locales: " + e.getMessage());
        }
        return locales;
    }

    /**
     * Busca un Local por su ID.
     * 
     * @param idLocal ID del local a buscar
     * @return objeto Local si se encuentra, null en caso contrario
     */
    public Local buscarPorId(int idLocal) {
        String sql = "SELECT * FROM LOCAL WHERE ID_LOCAL = ?";
        Local local = null;
        
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idLocal);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    local = new Local();
                    local.setIdLocal(rs.getInt("ID_LOCAL"));
                    local.setNombreComercial(rs.getString("NOMBRE_COMERCIAL"));
                    local.setDireccion(rs.getString("DIRECCION"));
                    local.setTelefono(rs.getString("TELEFONO"));
                    local.setGerente(rs.getString("GERENTE"));
                    local.setHoraApertura(rs.getTime("HORA_APERTURA"));
                    local.setHoraCierre(rs.getTime("HORA_CIERRE"));
                    
                    System.out.println("✓ Local encontrado: " + local.getNombreComercial());
                }
            }
        } catch (SQLException e) {
            System.err.println("✗ Error al buscar local: " + e.getMessage());
        }
        return local;
    }

    /**
     * Busca locales por nombre comercial (búsqueda parcial).
     * 
     * @param nombre nombre o parte del nombre a buscar
     * @return Lista de objetos Local que coinciden con la búsqueda
     */
    public List<Local> buscarPorNombre(String nombre) {
        List<Local> locales = new ArrayList<>();
        String sql = "SELECT * FROM LOCAL WHERE NOMBRE_COMERCIAL ILIKE ? ORDER BY ID_LOCAL";
        
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + nombre + "%");
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Local local = new Local();
                    local.setIdLocal(rs.getInt("ID_LOCAL"));
                    local.setNombreComercial(rs.getString("NOMBRE_COMERCIAL"));
                    local.setDireccion(rs.getString("DIRECCION"));
                    local.setTelefono(rs.getString("TELEFONO"));
                    local.setGerente(rs.getString("GERENTE"));
                    local.setHoraApertura(rs.getTime("HORA_APERTURA"));
                    local.setHoraCierre(rs.getTime("HORA_CIERRE"));
                    
                    locales.add(local);
                }
            }
            
            System.out.println("✓ Se encontraron " + locales.size() + " locales");
        } catch (SQLException e) {
            System.err.println("✗ Error al buscar locales: " + e.getMessage());
        }
        return locales;
    }
}
