package com.saborpaisa.vista;

import com.saborpaisa.dao.LocalDAO;
import com.saborpaisa.modelo.Local;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Formulario principal para realizar operaciones CRUD sobre la tabla LOCAL.
 * Interfaz gráfica Swing con componentes para insertar, actualizar, eliminar y consultar locales.
 */
public class FormularioLocal extends JFrame {
    
    // Componentes del formulario
    private JTextField txtId;
    private JTextField txtNombreComercial;
    private JTextField txtDireccion;
    private JTextField txtTelefono;
    private JTextField txtGerente;
    private JTextField txtHoraApertura;
    private JTextField txtHoraCierre;
    private JTextField txtBuscar;
    
    // Botones de operaciones CRUD
    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JButton btnBuscar;
    private JButton btnListar;
    
    // Tabla para mostrar resultados
    private JTable tablaLocales;
    private DefaultTableModel modeloTabla;
    
    // DAO para operaciones de base de datos
    private LocalDAO localDAO;
    
    /**
     * Constructor del formulario.
     */
    public FormularioLocal() {
        localDAO = new LocalDAO();
        inicializarComponentes();
        configurarEventos();
        listarLocales();
    }
    
    /**
     * Inicializa y configura todos los componentes del formulario.
     */
    private void inicializarComponentes() {
        // Configuración de la ventana principal
        setTitle("CRUD Local - Sabor Paisa");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        // Panel superior - Título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(52, 152, 219));
        JLabel lblTitulo = new JLabel("Gestión de Locales - Sabor Paisa");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        add(panelTitulo, BorderLayout.NORTH);
        
        // Panel central - Formulario y tabla
        JPanel panelCentral = new JPanel(new BorderLayout(10, 10));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel de formulario
        JPanel panelFormulario = crearPanelFormulario();
        panelCentral.add(panelFormulario, BorderLayout.NORTH);
        
        // Panel de tabla
        JPanel panelTabla = crearPanelTabla();
        panelCentral.add(panelTabla, BorderLayout.CENTER);
        
        add(panelCentral, BorderLayout.CENTER);
        
        // Panel inferior - Botones de acción
        JPanel panelBotones = crearPanelBotones();
        add(panelBotones, BorderLayout.SOUTH);
    }
    
    /**
     * Crea el panel del formulario con los campos de entrada.
     */
    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            "Datos del Local",
            0,
            0,
            new Font("Arial", Font.BOLD, 14),
            new Color(52, 152, 219)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // ID Local
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("ID Local:"), gbc);
        
        gbc.gridx = 1;
        txtId = new JTextField(10);
        txtId.setEnabled(false);
        txtId.setBackground(Color.LIGHT_GRAY);
        panel.add(txtId, gbc);
        
        // Nombre Comercial
        gbc.gridx = 2;
        panel.add(new JLabel("Nombre Comercial:"), gbc);
        
        gbc.gridx = 3;
        txtNombreComercial = new JTextField(20);
        panel.add(txtNombreComercial, gbc);
        
        // Dirección
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Dirección:"), gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 3;
        txtDireccion = new JTextField(40);
        panel.add(txtDireccion, gbc);
        
        // Teléfono
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
        panel.add(new JLabel("Teléfono:"), gbc);
        
        gbc.gridx = 1;
        txtTelefono = new JTextField(15);
        panel.add(txtTelefono, gbc);
        
        // Gerente
        gbc.gridx = 2;
        panel.add(new JLabel("Gerente:"), gbc);
        
        gbc.gridx = 3;
        txtGerente = new JTextField(20);
        panel.add(txtGerente, gbc);
        
        // Hora Apertura
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Hora Apertura (HH:MM):"), gbc);
        
        gbc.gridx = 1;
        txtHoraApertura = new JTextField(10);
        txtHoraApertura.setToolTipText("Formato: HH:MM (ejemplo: 08:00)");
        panel.add(txtHoraApertura, gbc);
        
        // Hora Cierre
        gbc.gridx = 2;
        panel.add(new JLabel("Hora Cierre (HH:MM):"), gbc);
        
        gbc.gridx = 3;
        txtHoraCierre = new JTextField(10);
        txtHoraCierre.setToolTipText("Formato: HH:MM (ejemplo: 22:00)");
        panel.add(txtHoraCierre, gbc);
        
        return panel;
    }
    
    /**
     * Crea el panel de la tabla para mostrar los registros.
     */
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            "Lista de Locales",
            0,
            0,
            new Font("Arial", Font.BOLD, 14),
            new Color(52, 152, 219)
        ));
        
        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.add(new JLabel("Buscar por nombre:"));
        txtBuscar = new JTextField(20);
        panelBusqueda.add(txtBuscar);
        btnBuscar = new JButton("🔍 Buscar");
        btnBuscar.setBackground(new Color(241, 196, 15));
        btnBuscar.setForeground(Color.BLACK);
        panelBusqueda.add(btnBuscar);
        btnListar = new JButton("📋 Listar Todos");
        btnListar.setBackground(new Color(46, 204, 113));
        btnListar.setForeground(Color.BLACK);
        panelBusqueda.add(btnListar);
        
        panel.add(panelBusqueda, BorderLayout.NORTH);
        
        // Configuración de la tabla
        String[] columnas = {"ID", "Nombre Comercial", "Dirección", "Teléfono", 
                             "Gerente", "Hora Apertura", "Hora Cierre"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabla de solo lectura
            }
        };
        
        tablaLocales = new JTable(modeloTabla);
        tablaLocales.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaLocales.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaLocales.getTableHeader().setBackground(new Color(52, 152, 219));
        tablaLocales.getTableHeader().setForeground(Color.WHITE);
        tablaLocales.setRowHeight(25);
        
        // Agregar scroll a la tabla
        JScrollPane scrollPane = new JScrollPane(tablaLocales);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Crea el panel de botones de acción.
     */
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBackground(new Color(236, 240, 241));
        
        // Botón Guardar
        btnGuardar = new JButton("💾 Guardar");
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 14));
        btnGuardar.setBackground(new Color(46, 204, 113));
        btnGuardar.setForeground(Color.BLACK);
        btnGuardar.setPreferredSize(new Dimension(140, 40));
        panel.add(btnGuardar);
        
        // Botón Actualizar
        btnActualizar = new JButton("✏️ Actualizar");
        btnActualizar.setFont(new Font("Arial", Font.BOLD, 14));
        btnActualizar.setBackground(new Color(52, 152, 219));
        btnActualizar.setForeground(Color.BLACK);
        btnActualizar.setPreferredSize(new Dimension(140, 40));
        panel.add(btnActualizar);
        
        // Botón Eliminar
        btnEliminar = new JButton("🗑️ Eliminar");
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 14));
        btnEliminar.setBackground(new Color(231, 76, 60));
        btnEliminar.setForeground(Color.BLACK);
        btnEliminar.setPreferredSize(new Dimension(140, 40));
        panel.add(btnEliminar);
        
        // Botón Limpiar
        btnLimpiar = new JButton("🧹 Limpiar");
        btnLimpiar.setFont(new Font("Arial", Font.BOLD, 14));
        btnLimpiar.setBackground(new Color(149, 165, 166));
        btnLimpiar.setForeground(Color.BLACK);
        btnLimpiar.setPreferredSize(new Dimension(140, 40));
        panel.add(btnLimpiar);
        
        return panel;
    }
    
    /**
     * Configura los eventos de los botones y componentes.
     */
    private void configurarEventos() {
        // Evento: Guardar nuevo local
        btnGuardar.addActionListener(e -> guardarLocal());
        
        // Evento: Actualizar local existente
        btnActualizar.addActionListener(e -> actualizarLocal());
        
        // Evento: Eliminar local
        btnEliminar.addActionListener(e -> eliminarLocal());
        
        // Evento: Limpiar campos
        btnLimpiar.addActionListener(e -> limpiarCampos());
        
        // Evento: Buscar por nombre
        btnBuscar.addActionListener(e -> buscarPorNombre());
        
        // Evento: Listar todos
        btnListar.addActionListener(e -> listarLocales());
        
        // Evento: Seleccionar fila de la tabla
        tablaLocales.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarDatosSeleccionados();
            }
        });
    }
    
    /**
     * Guarda un nuevo local en la base de datos.
     */
    private void guardarLocal() {
        try {
            // Validar campos obligatorios
            if (txtNombreComercial.getText().trim().isEmpty() || 
                txtDireccion.getText().trim().isEmpty()) {
                mostrarMensaje("Por favor complete los campos obligatorios (Nombre y Dirección)", 
                              "Campos Incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Crear objeto Local
            Local local = new Local();
            local.setNombreComercial(txtNombreComercial.getText().trim());
            local.setDireccion(txtDireccion.getText().trim());
            local.setTelefono(txtTelefono.getText().trim());
            local.setGerente(txtGerente.getText().trim());
            
            // Convertir horas
            local.setHoraApertura(convertirHora(txtHoraApertura.getText().trim()));
            local.setHoraCierre(convertirHora(txtHoraCierre.getText().trim()));
            
            // Insertar en la base de datos
            if (localDAO.insertar(local)) {
                mostrarMensaje("Local guardado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                listarLocales();
            } else {
                mostrarMensaje("Error al guardar el local", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception ex) {
            mostrarMensaje("Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Actualiza un local existente en la base de datos.
     */
    private void actualizarLocal() {
        try {
            // Validar que haya un ID seleccionado
            if (txtId.getText().trim().isEmpty()) {
                mostrarMensaje("Seleccione un local de la tabla para actualizar", 
                              "Sin Selección", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Validar campos obligatorios
            if (txtNombreComercial.getText().trim().isEmpty() || 
                txtDireccion.getText().trim().isEmpty()) {
                mostrarMensaje("Por favor complete los campos obligatorios (Nombre y Dirección)", 
                              "Campos Incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Crear objeto Local
            Local local = new Local();
            local.setIdLocal(Integer.parseInt(txtId.getText().trim()));
            local.setNombreComercial(txtNombreComercial.getText().trim());
            local.setDireccion(txtDireccion.getText().trim());
            local.setTelefono(txtTelefono.getText().trim());
            local.setGerente(txtGerente.getText().trim());
            
            // Convertir horas
            local.setHoraApertura(convertirHora(txtHoraApertura.getText().trim()));
            local.setHoraCierre(convertirHora(txtHoraCierre.getText().trim()));
            
            // Actualizar en la base de datos
            if (localDAO.actualizar(local)) {
                mostrarMensaje("Local actualizado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                listarLocales();
            } else {
                mostrarMensaje("Error al actualizar el local", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception ex) {
            mostrarMensaje("Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Elimina un local de la base de datos.
     */
    private void eliminarLocal() {
        try {
            // Validar que haya un ID seleccionado
            if (txtId.getText().trim().isEmpty()) {
                mostrarMensaje("Seleccione un local de la tabla para eliminar", 
                              "Sin Selección", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Confirmar eliminación
            int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar el local '" + txtNombreComercial.getText() + "'?\n" +
                "Esta acción no se puede deshacer.",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                int idLocal = Integer.parseInt(txtId.getText().trim());
                
                // Eliminar de la base de datos
                if (localDAO.eliminar(idLocal)) {
                    mostrarMensaje("Local eliminado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    listarLocales();
                } else {
                    mostrarMensaje("No se pudo eliminar el local", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            
        } catch (RuntimeException ex) {
            // Capturar errores específicos como restricciones de clave foránea
            mostrarMensaje(ex.getMessage(), "Error de Eliminación", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            mostrarMensaje("Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Lista todos los locales en la tabla.
     */
    private void listarLocales() {
        modeloTabla.setRowCount(0); // Limpiar tabla
        
        List<Local> locales = localDAO.listarTodos();
        
        for (Local local : locales) {
            Object[] fila = {
                local.getIdLocal(),
                local.getNombreComercial(),
                local.getDireccion(),
                local.getTelefono(),
                local.getGerente(),
                local.getHoraApertura() != null ? local.getHoraApertura().toString().substring(0, 5) : "",
                local.getHoraCierre() != null ? local.getHoraCierre().toString().substring(0, 5) : ""
            };
            modeloTabla.addRow(fila);
        }
    }
    
    /**
     * Busca locales por nombre comercial.
     */
    private void buscarPorNombre() {
        String nombre = txtBuscar.getText().trim();
        
        if (nombre.isEmpty()) {
            mostrarMensaje("Ingrese un nombre para buscar", "Campo Vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        modeloTabla.setRowCount(0); // Limpiar tabla
        
        List<Local> locales = localDAO.buscarPorNombre(nombre);
        
        if (locales.isEmpty()) {
            mostrarMensaje("No se encontraron locales con ese nombre", 
                          "Sin Resultados", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (Local local : locales) {
                Object[] fila = {
                    local.getIdLocal(),
                    local.getNombreComercial(),
                    local.getDireccion(),
                    local.getTelefono(),
                    local.getGerente(),
                    local.getHoraApertura() != null ? local.getHoraApertura().toString().substring(0, 5) : "",
                    local.getHoraCierre() != null ? local.getHoraCierre().toString().substring(0, 5) : ""
                };
                modeloTabla.addRow(fila);
            }
        }
    }
    
    /**
     * Carga los datos del registro seleccionado en la tabla.
     */
    private void cargarDatosSeleccionados() {
        int filaSeleccionada = tablaLocales.getSelectedRow();
        
        if (filaSeleccionada != -1) {
            txtId.setText(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
            txtNombreComercial.setText(modeloTabla.getValueAt(filaSeleccionada, 1).toString());
            txtDireccion.setText(modeloTabla.getValueAt(filaSeleccionada, 2).toString());
            txtTelefono.setText(modeloTabla.getValueAt(filaSeleccionada, 3).toString());
            txtGerente.setText(modeloTabla.getValueAt(filaSeleccionada, 4).toString());
            txtHoraApertura.setText(modeloTabla.getValueAt(filaSeleccionada, 5).toString());
            txtHoraCierre.setText(modeloTabla.getValueAt(filaSeleccionada, 6).toString());
        }
    }
    
    /**
     * Limpia todos los campos del formulario.
     */
    private void limpiarCampos() {
        txtId.setText("");
        txtNombreComercial.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtGerente.setText("");
        txtHoraApertura.setText("");
        txtHoraCierre.setText("");
        txtBuscar.setText("");
        tablaLocales.clearSelection();
    }
    
    /**
     * Convierte una cadena de texto a objeto Time.
     */
    private Time convertirHora(String horaTexto) {
        if (horaTexto == null || horaTexto.isEmpty()) {
            return null;
        }
        
        try {
            SimpleDateFormat formato = new SimpleDateFormat("HH:mm");
            java.util.Date fecha = formato.parse(horaTexto);
            return new Time(fecha.getTime());
        } catch (ParseException e) {
            throw new IllegalArgumentException("Formato de hora inválido. Use HH:MM (ejemplo: 08:00)");
        }
    }
    
    /**
     * Muestra un mensaje en un cuadro de diálogo.
     */
    private void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }
}
