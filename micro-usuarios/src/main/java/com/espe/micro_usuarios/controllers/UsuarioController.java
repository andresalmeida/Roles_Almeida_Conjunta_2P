package com.espe.micro_usuarios.controllers;

import com.espe.micro_usuarios.model.entity.Usuario;
import com.espe.micro_usuarios.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario, BindingResult result) {
        // Map para almacenar los errores de validación
        Map<String, String> errores = new HashMap<>();

        // Verificar si hay errores de validación
        if (result.hasErrors()) {
            result.getFieldErrors().forEach(err -> {
                errores.put(err.getField(), err.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errores); // Retornar errores como respuesta
        }

        // Si no hay errores, guardar el estudiante
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(usuario));
    }


    @GetMapping
    public List<Usuario> listar() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Usuario> usuarioOptional = service.findById(id);
        if (usuarioOptional.isPresent()) {
            return ResponseEntity.ok().body(usuarioOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody Estudiante estudiante) {
//        Optional<Estudiante> estudianteOptional = service.findById(id);
//        if (estudianteOptional.isPresent()) {
//            Estudiante estudianteDB = estudianteOptional.get();
//            estudianteDB.setNombre(estudiante.getNombre());
//            estudianteDB.setApellido(estudiante.getApellido());
//            estudianteDB.setEmail(estudiante.getEmail());
//            estudianteDB.setFechaNacimiento(estudiante.getFechaNacimiento());
//            estudianteDB.setTelefono(estudiante.getTelefono());
//            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(estudianteDB));
//        }
//        return ResponseEntity.notFound().build();
//    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @Valid @RequestBody Usuario usuario, BindingResult result) {
        // Map para almacenar los errores de validación
        Map<String, String> errores = new HashMap<>();

        // Verificar si hay errores de validación
        if (result.hasErrors()) {
            result.getFieldErrors().forEach(err -> {
                errores.put(err.getField(), err.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errores); // Retornar errores como respuesta
        }

        // Buscar si existe el estudiante por ID
        Optional<Usuario> usuarioOptional = service.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuarioDB = usuarioOptional.get();
            // Actualizar los campos del estudiante existente
            usuarioDB.setNombre(usuario.getNombre());
            usuarioDB.setApellido(usuario.getApellido());
            usuarioDB.setEmail(usuario.getEmail());
            usuarioDB.setFechaNacimiento(usuario.getFechaNacimiento());
            usuarioDB.setTelefono(usuario.getTelefono());
            // Guardar y retornar el estudiante actualizado
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(usuarioDB));
        }

        // Si no se encuentra el estudiante, retornar 404
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Usuario> usuarioOptional = service.findById(id);
        if (usuarioOptional.isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}


