package com.espe.micro_roles.services;

import com.espe.micro_roles.clients.UsuarioClientRest;
import com.espe.micro_roles.model.Usuario;
import com.espe.micro_roles.model.entity.Rol;
import com.espe.micro_roles.model.entity.RolUsuario;
import com.espe.micro_roles.repositories.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository repository;

    @Autowired
    private UsuarioClientRest clientRest;

    @Override
    public List<Rol> findAll() {
        return (List<Rol>) repository.findAll();
    }

    @Override
    public Optional<Rol> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Rol save(Rol rol) {
        return repository.save(rol);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);

    }

//    @Override
//    public Optional<Usuario> asignarUsuario(Usuario usuario, Long id) {
//        Optional<Rol> optional = repository.findById(id);
//        if (optional.isPresent()) {
//            Usuario usuarioTemp = clientRest.buscarporId(usuario.getId());
//
//            Rol rol = optional.get();
//            RolUsuario rolUsuario = new RolUsuario();
//
//            rolUsuario.setUsuario_id(usuarioTemp.getId());
//
//            rol.addRolUsuario(rolUsuario);
//            repository.save(rol);
//            //return Optional.of(estudianteTemp);
//        }
//        return Optional.empty();
//    }

    @Override
    public Optional<Usuario> asignarUsuario(Usuario usuario, Long rolId) {
        Optional<Rol> optional = repository.findById(rolId);
        if (optional.isPresent()) {
            Usuario usuarioTemp = clientRest.buscarporId(usuario.getId());

            Rol rol = optional.get();
            RolUsuario rolUsuario = new RolUsuario();
            rolUsuario.setUsuario_id(usuarioTemp.getId());

            rol.addRolUsuario(rolUsuario);
            repository.save(rol);

            return Optional.of(usuarioTemp);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> desasignarUsuario(Long usuarioId, Long rolId) {
        Optional<Rol> optional = repository.findById(rolId);
        if (optional.isPresent()) {
            Rol rol = optional.get();
            RolUsuario rolUsuario = rol.getRolUsuarios()
                    .stream()
                    .filter(ce -> ce.getUsuario_id().equals(usuarioId))
                    .findFirst()
                    .orElse(null);

            if (rolUsuario != null) {
                rol.removeRolUsuario(rolUsuario);
                repository.save(rol);
                return Optional.of(clientRest.buscarporId(usuarioId));
            }
        }
        return Optional.empty();
    }





}
