package com.saborpaisa.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase para gestionar la conexión a la base de datos PostgreSQL.
 * Implementa el patrón Singleton para garantizar una única instancia de conexión.
 */
public class Conexion {
    // Parámetros de conexión a PostgreSQL
    private static final String URL = "jdbc:postgresql://localhost:5432/sabor_paisa";
    private static final String USUARIO = "postgres";
    private static final String CONTRASENA = "admin";
    
    private static Connection conexion = null;

    /**
     * Constructor privado para evitar instanciación externa.
     */
    private Conexion() {
    }

    /**
     * Obtiene una conexión a la base de datos.
     * Si no existe una conexión activa, crea una nueva.
     * 
     * @return Connection objeto de conexión a la base de datos
     * @throws SQLException si ocurre un error al conectar
     */
    public static Connection obtenerConexion() throws SQLException {
        try {
            if (conexion == null || conexion.isClosed()) {
                // Cargar el driver de PostgreSQL
                Class.forName("org.postgresql.Driver");
                
                // Establecer la conexión
                conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
                System.out.println("✓ Conexión establecida exitosamente con PostgreSQL");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("✗ Error: Driver de PostgreSQL no encontrado");
            throw new SQLException("Driver no encontrado", e);
        } catch (SQLException e) {
            System.err.println("✗ Error al conectar con la base de datos: " + e.getMessage());
            throw e;
        }
        return conexion;
    }

    /**
     * Cierra la conexión a la base de datos si está abierta.
     */
    public static void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("✓ Conexión cerrada exitosamente");
            }
        } catch (SQLException e) {
            System.err.println("✗ Error al cerrar la conexión: " + e.getMessage());
        }
    }

    /**
     * Verifica si la conexión está activa.
     * 
     * @return true si la conexión está activa, false en caso contrario
     */
    public static boolean probarConexion() {
        try {
            Connection conn = obtenerConexion();
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("✗ Error al probar la conexión: " + e.getMessage());
            return false;
        }
    }
}
