package com.residencial.acceso.controller;

import com.residencial.acceso.dto.AuthRequest;
import com.residencial.acceso.dto.AuthResponse;
import com.residencial.acceso.exception.UnauthorizedException;
import com.residencial.acceso.model.Usuario;
import com.residencial.acceso.repository.UsuarioRepository;
import com.residencial.acceso.service.impl.SeguridadServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Endpoints de autenticación")
public class AuthController {
    private final SeguridadServiceImpl seguridadService;
    private final UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autenticar usuario y obtener token JWT")
    public AuthResponse login(@Valid @RequestBody AuthRequest request) {
        try {
            // Buscar el usuario primero
            Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UnauthorizedException("Usuario no encontrado"));
            
            // Verificar la contraseña manualmente
            if (!seguridadService.verificarPassword(request.getPassword(), usuario.getContrasena())) {
                throw new UnauthorizedException("Credenciales inválidas");
            }
            
            // Generar token
            String token = seguridadService.generarToken(usuario);
            
            return new AuthResponse(token, usuario.getId(), usuario.getNombre(), usuario.getRol());
        } catch (UnauthorizedException e) {
            throw e;
        } catch (Exception e) {
            throw new UnauthorizedException("Error en la autenticación");
        }
    }
}