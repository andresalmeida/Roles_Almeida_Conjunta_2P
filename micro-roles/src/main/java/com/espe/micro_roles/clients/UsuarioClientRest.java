package com.espe.micro_roles.clients;

import com.espe.micro_roles.model.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@FeignClient(name = "micro-usuarios", url="localhost:8002/api/usuarios")
public interface UsuarioClientRest {
    @GetMapping("/{id}")
    Usuario buscarporId(@PathVariable Long id);
}
