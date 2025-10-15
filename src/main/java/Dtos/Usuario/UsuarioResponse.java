/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dtos.Usuario;

import EnumOptions.Rol;
import ModeloEntidad.Usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.time.LocalDate;

/**
 *
 * @author alejandro
 */
public class UsuarioResponse {
    
    private String nombre;
    private String userName;
    private String password;
    private Rol rolUsuario;
    private double dineroCartera;
    @JsonFormat (pattern = "yyyy-MM-dd")
    @JsonSerialize (using = LocalDateSerializer.class)
    private LocalDate fechaRegistro;

    public UsuarioResponse(Usuario usuario) {
        this.nombre = nombre;
        this.userName = userName;
        this.password = password;
        this.rolUsuario = rolUsuario;
        this.dineroCartera = dineroCartera;
        this.fechaRegistro = fechaRegistro;
    }

    
    
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRolUsuario() {
        return rolUsuario;
    }

    public void setRolUsuario(Rol rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    public double getDineroCartera() {
        return dineroCartera;
    }

    public void setDineroCartera(double dineroCartera) {
        this.dineroCartera = dineroCartera;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    
    
}
