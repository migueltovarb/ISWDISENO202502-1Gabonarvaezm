package com.residencial.acceso.controller;

import com.residencial.acceso.dto.AuthResponse;
import com.residencial.acceso.model.Usuario;
import com.residencial.acceso.model.Rol;
import com.residencial.acceso.repository.UsuarioRepository;
import com.residencial.acceso.service.impl.SeguridadServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/debug")
@RequiredArgsConstructor
@Tag(name = "Depuración", description = "Endpoints para depuración y pruebas")
public class DebugController {
    
    private final UsuarioRepository usuarioRepository;
    private final SeguridadServiceImpl seguridadService;

    @GetMapping("/usuarios")
    @Operation(summary = "Listar todos los usuarios", description = "Obtiene todos los usuarios sin autenticación para depuración")
    public ResponseEntity<List<UsuarioDebugDTO>> listarUsuarios() {
        List<UsuarioDebugDTO> usuarios = usuarioRepository.findAll().stream()
            .map(usuario -> new UsuarioDebugDTO(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getNombre(),
                usuario.getRol(),
                usuario.getContrasena()
            ))
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping("/login-directo")
    @Operation(summary = "Login directo para pruebas", description = "Autenticación directa sin validación de Spring Security")
    public ResponseEntity<AuthResponse> loginDirecto(@RequestBody LoginDirectoRequest request) {
        try {
            Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            // Verificar contraseña directamente
            if (!seguridadService.verificarPassword(request.getPassword(), usuario.getContrasena())) {
                throw new RuntimeException("Contraseña incorrecta");
            }
            
            String token = seguridadService.generarToken(usuario);
            
            return ResponseEntity.ok(new AuthResponse(token, usuario.getId(), usuario.getNombre(), usuario.getRol()));
        } catch (Exception e) {
            throw new RuntimeException("Error en login: " + e.getMessage());
        }
    }

    @PostMapping("/crear-admin-rapido")
    @Operation(summary = "Crear admin rápido", description = "Crea un usuario administrador rápidamente")
    public ResponseEntity<String> crearAdminRapido() {
        try {
            if (!usuarioRepository.findByEmail("admin@residencial.com").isPresent()) {
                Usuario admin = new Usuario();
                admin.setNombre("Administrador del Sistema");
                admin.setApellido("Admin");
                admin.setEmail("admin@residencial.com");
                admin.setContrasena(seguridadService.encriptarPassword("admin123"));
                admin.setRol(Rol.ADMIN);
                admin.setApartamento("ADMIN");
                admin.setActivo(true);
                
                usuarioRepository.save(admin);
                return ResponseEntity.ok("Admin creado: admin@residencial.com / admin123");
            } else {
                return ResponseEntity.ok("Admin ya existe");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // DTO interno
    static class UsuarioDebugDTO {
        private String id;
        private String email;
        private String nombre;
        private Rol rol;
        private String password; // Encriptado
        
        public UsuarioDebugDTO(String id, String email, String nombre, Rol rol, String password) {
            this.id = id;
            this.email = email;
            this.nombre = nombre;
            this.rol = rol;
            this.password = password;
        }
        
        // Getters
        public String getId() { return id; }
        public String getEmail() { return email; }
        public String getNombre() { return nombre; }
        public Rol getRol() { return rol; }
        public String getPassword() { return password; }
    }
    
    static class LoginDirectoRequest {
        private String email;
        private String password;
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}