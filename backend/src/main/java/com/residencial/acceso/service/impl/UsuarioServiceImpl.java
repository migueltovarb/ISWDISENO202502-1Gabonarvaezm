package com.residencial.acceso.service.impl;

import com.residencial.acceso.dto.UsuarioDTO;
import com.residencial.acceso.exception.BusinessException;
import com.residencial.acceso.exception.NotFoundException;
import com.residencial.acceso.model.Usuario;
import com.residencial.acceso.repository.UsuarioRepository;
import com.residencial.acceso.service.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements IUsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Usuario crear(UsuarioDTO dto) {
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new BusinessException("El email ya está registrado");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setRol(dto.getRol());
        usuario.setEmail(dto.getEmail());
        usuario.setContrasena(passwordEncoder.encode(dto.getContrasena()));
        usuario.setApartamento(dto.getApartamento());
        usuario.setActivo(dto.getActivo() != null ? dto.getActivo() : true);
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario actualizar(String id, UsuarioDTO dto) {
        Usuario usuario = obtenerPorId(id);
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setRol(dto.getRol());
        usuario.setEmail(dto.getEmail());
        if (dto.getContrasena() != null && !dto.getContrasena().isEmpty()) {
            usuario.setContrasena(passwordEncoder.encode(dto.getContrasena()));
        }
        usuario.setApartamento(dto.getApartamento());
        usuario.setActivo(dto.getActivo() != null ? dto.getActivo() : usuario.isActivo());
        return usuarioRepository.save(usuario);
    }

    @Override
    public void eliminar(String id) {
        java.util.Objects.requireNonNull(id);
        Usuario usuario = obtenerPorId(id);
        usuarioRepository.delete(usuario);
    }

    @Override
    public Usuario obtenerPorId(String id) {
        java.util.Objects.requireNonNull(id);
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
    }

    @Override
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario activarDesactivar(String id, boolean activo) {
        java.util.Objects.requireNonNull(id);
        Usuario usuario = obtenerPorId(id);
        usuario.setActivo(activo);
        return usuarioRepository.save(usuario);
    }
}