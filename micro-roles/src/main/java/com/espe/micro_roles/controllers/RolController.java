package com.espe.micro_roles.controllers;

import com.espe.micro_roles.model.Usuario;
import com.espe.micro_roles.model.entity.Rol;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.espe.micro_roles.services.RolService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
public class RolController {

    @Autowired
    private RolService service;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Rol rol) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(rol));
    }

    @GetMapping
    public List<Rol> listar() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarporId(@PathVariable Long id) {
        Optional<Rol> rolOptional = service.findById(id);
        if (rolOptional.isPresent()) {
            return ResponseEntity.ok().body(rolOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody Rol rol) {
        Optional<Rol> rolOptional = service.findById(id);
        if (rolOptional.isPresent()) {
            Rol rolDB = rolOptional.get();
            rolDB.setNombre(rol.getNombre());
            rolDB.setDescripcion(rol.getDescripcion());
            //rolDB.setCreditos(rol.getCreditos());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(rolDB));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar (@PathVariable Long id) {
        Optional<Rol> rolOptional = service.findById(id);
        if (rolOptional.isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    //@PutMapping("/asignar-rol/{rolId}")
    //public ResponseEntity<?> asignarUsuario(@RequestBody Usuario usuario, @PathVariable Long rolId) {
    //    Optional<Usuario> o;
    //    try {
    //        o = service.asignarUsuario(usuario, rolId);
    //    } catch (FeignException e) {
    //        return ResponseEntity.status(HttpStatus.NOT_FOUND)
    //                .body(Collections.singletonMap("mensaje", "No existe el usuario por" +
    //                        " el id o error en la comunicacion: " + e.getMessage()));
    //    }
    //    if (o.isPresent()) {
    //        return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
    //    }
    //    return ResponseEntity.notFound().build();
    //}

    @PostMapping("/asignar-rol/{rolId}")
    public ResponseEntity<?> asignarUsuario(@RequestBody Usuario usuario, @PathVariable Long rolId) {
        Optional<Usuario> o;
        try {
            o = service.asignarUsuario(usuario, rolId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "No existe el usuario por" +
                            " el id o error en la comunicación: " + e.getMessage()));
        }
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/desasignar-rol/{rolID}/usuario/{usuarioId}")
    public ResponseEntity<?> desasignarUsuario(@PathVariable Long rolID, @PathVariable Long usuarioId) {
        Optional<Usuario> o;
        try {
            o = service.desasignarUsuario(usuarioId, rolID);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "No existe el usuario por" +
                            " el id o error en la comunicación: " + e.getMessage()));
        }
        if (o.isPresent()) {
            return ResponseEntity.ok(o.get());
        }
        return ResponseEntity.notFound().build();
    }


}
