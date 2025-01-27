package com.espe.micro_roles.services;

import com.espe.micro_roles.model.Usuario;
import com.espe.micro_roles.model.entity.Rol;

import java.util.List;
import java.util.Optional;

public interface RolService {
    List<Rol> findAll();
    Optional<Rol> findById(Long id);
    Rol save(Rol rol);
    void deleteById(Long id);

//    Optional<Usuario> asignarUsuario(Usuario usuario, Long id);
    Optional<Usuario> asignarUsuario(Usuario usuario, Long rolId);
    Optional<Usuario> desasignarUsuario(Long usuarioId, Long rolId);

}
