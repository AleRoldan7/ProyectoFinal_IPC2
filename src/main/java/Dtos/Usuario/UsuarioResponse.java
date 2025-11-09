/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dtos.Usuario;

import ConexionDBA.UsuarioDBA;
import EnumOptions.Rol;
import ModeloEntidad.Usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.time.LocalDate;

/**
 *
 * @author alejandro
 */
public class UsuarioResponse {

    private Integer idUsuario;
    private String nombre;
    private String userName;
    private Rol rolUsuario;
    private double dineroCartera;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate fechaRegistro;
    private Integer idCine;
    private String nombreCine;

    public UsuarioResponse(Usuario usuario) {
        this.idUsuario = usuario.getIdUsuario();
        this.nombre = usuario.getNombre();
        this.userName = usuario.getUserName();
        this.rolUsuario = usuario.getRolUsuario();
        this.dineroCartera = usuario.getDineroCartera();
        this.fechaRegistro = usuario.getFechaRegistro();

        if (usuario.getRolUsuario() == Rol.ADMIN_CINE) {
            UsuarioDBA usuarioDBA = new UsuarioDBA();
            Integer cineAsociando = usuarioDBA.obtenerCineUsuario(usuario.getIdUsuario());
            this.idCine = cineAsociando;
        }
    }

    public UsuarioResponse() {
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
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

    public Integer getIdCine() {
        return idCine;
    }

    public void setIdCine(Integer idCine) {
        this.idCine = idCine;
    }

    public String getNombreCine() {
        return nombreCine;
    }

    public void setNombreCine(String nombreCine) {
        this.nombreCine = nombreCine;
    }

}
