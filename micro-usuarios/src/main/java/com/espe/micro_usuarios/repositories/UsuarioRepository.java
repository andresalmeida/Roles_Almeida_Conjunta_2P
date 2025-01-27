package com.espe.micro_usuarios.repositories;

import com.espe.micro_usuarios.model.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
}
