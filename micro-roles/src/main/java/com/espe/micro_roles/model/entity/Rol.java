package com.espe.micro_roles.model.entity;

import com.espe.micro_roles.model.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="roles")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    //@Column(name="name")
    @Column(nullable = true)
    private String nombre;

    @Column(nullable = true)
    private String descripcion;

//    @Column(nullable = true)
//    private int creditos;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "creado_en", nullable = false, updatable = false)
    private LocalDateTime creadoEn;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private List<RolUsuario> rolUsuarios;

    @Transient
    private List<Usuario> usuarios;

    public Rol() {
        rolUsuarios = new ArrayList<>();
        usuarios = new ArrayList<>();
    }

    public void addRolUsuario(RolUsuario rolUsuario) {
        rolUsuarios.add(rolUsuario);
    }

    public void removeRolUsuario(RolUsuario rolUsuario) {
        rolUsuarios.remove(rolUsuario);
    }

    //GETTERS & SETTERS

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

//    public int getCreditos() {
//        return creditos;
//    }
//
//    public void setCreditos(int creditos) {
//        this.creditos = creditos;
//    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(LocalDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }

    public List<RolUsuario> getRolUsuarios() {
        return rolUsuarios;
    }

    public void setRolUsuarios(List<RolUsuario> rolUsuarios) {
        this.rolUsuarios = rolUsuarios;
    }

    //METODO QUE INICIALIZA creadoEn AUTOMATICAMENTE
    @PrePersist
    public void prePersist() {
        this.creadoEn = LocalDateTime.now();
    }
}

