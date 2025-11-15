package com.saborpaisa.modelo;

import java.sql.Time;

/**
 * Clase modelo que representa un Local de Sabor Paisa.
 * Contiene los atributos y métodos get/set para la entidad LOCAL.
 */
public class Local {
    private int idLocal;
    private String nombreComercial;
    private String direccion;
    private String telefono;
    private String gerente;
    private Time horaApertura;
    private Time horaCierre;

    // Constructor vacío
    public Local() {
    }

    // Constructor con todos los campos
    public Local(int idLocal, String nombreComercial, String direccion, 
                 String telefono, String gerente, Time horaApertura, Time horaCierre) {
        this.idLocal = idLocal;
        this.nombreComercial = nombreComercial;
        this.direccion = direccion;
        this.telefono = telefono;
        this.gerente = gerente;
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
    }

    // Constructor sin ID (para inserciones)
    public Local(String nombreComercial, String direccion, String telefono, 
                 String gerente, Time horaApertura, Time horaCierre) {
        this.nombreComercial = nombreComercial;
        this.direccion = direccion;
        this.telefono = telefono;
        this.gerente = gerente;
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
    }

    // Getters y Setters
    public int getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(int idLocal) {
        this.idLocal = idLocal;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getGerente() {
        return gerente;
    }

    public void setGerente(String gerente) {
        this.gerente = gerente;
    }

    public Time getHoraApertura() {
        return horaApertura;
    }

    public void setHoraApertura(Time horaApertura) {
        this.horaApertura = horaApertura;
    }

    public Time getHoraCierre() {
        return horaCierre;
    }

    public void setHoraCierre(Time horaCierre) {
        this.horaCierre = horaCierre;
    }

    @Override
    public String toString() {
        return "Local{" +
                "idLocal=" + idLocal +
                ", nombreComercial='" + nombreComercial + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", gerente='" + gerente + '\'' +
                ", horaApertura=" + horaApertura +
                ", horaCierre=" + horaCierre +
                '}';
    }
}
