/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloEntidad.Usuario;

import EnumOptions.Rol;
import java.time.LocalDate;

/**
 *
 * @author alejandro
 */
public class Usuario {
    
    private int idUsuario;
    private String nombre;
    private String userName;
    private String password;
    private Rol rolUsuario;
    private double dineroCartera;
    private LocalDate fechaRegistro;

    public Usuario(String userName, String password, Rol rolUsuario) {
        this.userName = userName;
        this.password = password;
        this.rolUsuario = rolUsuario;
    }
        
    public Usuario() {
    }
  
    public Usuario(int idUsuario, String nombre, String userName, String password, Rol rolUsuario, double dineroCartera, 
            LocalDate fechaRegistro) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.userName = userName;
        this.password = password;
        this.rolUsuario = rolUsuario;
        this.dineroCartera = dineroCartera;
        this.fechaRegistro = fechaRegistro;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
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
