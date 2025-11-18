package com.residencial.acceso.controller;

import com.residencial.acceso.dto.UsuarioDTO;
import com.residencial.acceso.model.Usuario;
import com.residencial.acceso.service.IUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Gestión de usuarios del sistema")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {
    private final IUsuarioService usuarioService;

    @PostMapping
    @Operation(summary = "Crear usuario", description = "Crear un nuevo usuario en el sistema")
    public Usuario crear(@Valid @RequestBody UsuarioDTO dto) {
        return usuarioService.crear(dto);
    }

    @GetMapping
    @Operation(summary = "Listar usuarios", description = "Obtener lista de todos los usuarios")
    public List<Usuario> listar() {
        return usuarioService.listar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario", description = "Obtener usuario por ID")
    public Usuario obtenerPorId(@PathVariable String id) {
        return usuarioService.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario", description = "Actualizar información de usuario")
    public Usuario actualizar(@PathVariable String id, @Valid @RequestBody UsuarioDTO dto) {
        return usuarioService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario", description = "Eliminar usuario del sistema")
    public void eliminar(@PathVariable String id) {
        usuarioService.eliminar(id);
    }

    @PatchMapping("/{id}/activar")
    @Operation(summary = "Activar/desactivar usuario", description = "Cambiar estado activo de usuario")
    public Usuario activarDesactivar(@PathVariable String id, @RequestParam boolean activo) {
        return usuarioService.activarDesactivar(id, activo);
    }
}