package com.saborpaisa.main;

import com.saborpaisa.conexion.Conexion;
import com.saborpaisa.vista.FormularioLocal;

import javax.swing.*;

/**
 * Clase principal de la aplicación.
 * Punto de entrada del programa.
 */
public class Aplicacion {
    
    /**
     * Método main - Inicia la aplicación.
     */
    public static void main(String[] args) {
        // Configurar el Look and Feel del sistema operativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("No se pudo establecer el Look and Feel: " + e.getMessage());
        }
        
        // Verificar conexión a la base de datos
        System.out.println("===========================================");
        System.out.println("  SISTEMA CRUD - SABOR PAISA");
        System.out.println("  Gestión de Locales");
        System.out.println("===========================================");
        System.out.println();
        
        if (Conexion.probarConexion()) {
            System.out.println("✓ Sistema listo para operar");
            System.out.println("===========================================");
            
            // Iniciar la interfaz gráfica en el hilo de eventos de Swing
            SwingUtilities.invokeLater(() -> {
                FormularioLocal formulario = new FormularioLocal();
                formulario.setVisible(true);
            });
        } else {
            System.err.println("✗ No se pudo conectar a la base de datos");
            System.err.println("✗ Verifique que PostgreSQL esté ejecutándose");
            System.err.println("✗ Verifique los parámetros de conexión en la clase Conexion");
            System.err.println("===========================================");
            
            JOptionPane.showMessageDialog(
                null,
                "No se pudo conectar a la base de datos.\n" +
                "Por favor verifique:\n" +
                "1. PostgreSQL está ejecutándose\n" +
                "2. La base de datos 'saborpaisa' existe\n" +
                "3. Usuario y contraseña son correctos\n" +
                "4. El servidor está en localhost:5432",
                "Error de Conexión",
                JOptionPane.ERROR_MESSAGE
            );
            
            System.exit(1);
        }
    }
}
