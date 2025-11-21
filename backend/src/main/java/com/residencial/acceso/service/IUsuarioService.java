package com.residencial.acceso.service;

import com.residencial.acceso.dto.UsuarioDTO;
import com.residencial.acceso.model.Usuario;

import java.util.List;

public interface IUsuarioService {
    Usuario crear(UsuarioDTO dto);
    Usuario actualizar(String id, UsuarioDTO dto);
    void eliminar(String id);
    Usuario obtenerPorId(String id);
    List<Usuario> listar();
    Usuario activarDesactivar(String id, boolean activo);
}