package com.residencial.acceso.controller;

import com.residencial.acceso.model.Usuario;
import com.residencial.acceso.model.Rol;
import com.residencial.acceso.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth2")
@Tag(name = "Autenticación Alternativa", description = "Endpoints de autenticación alternativos para pruebas")
public class Auth2Controller {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private com.residencial.acceso.service.impl.SeguridadServiceImpl seguridadService;

    @PostMapping("/login-simple")
    @Operation(summary = "Login simple para pruebas", description = "Autenticación básica sin Spring Security")
    public ResponseEntity<?> loginSimple(@RequestBody Map<String, String> credentials) {
        try {
            String email = credentials.get("email");
            String password = credentials.get("password");
            
            if (email == null || password == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Email y contraseña son requeridos"));
            }
            
            // Buscar usuario
            Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            // Verificar contraseña
            if (!passwordEncoder.matches(password, usuario.getContrasena())) {
                return ResponseEntity.status(401).body(Map.of("error", "Contraseña incorrecta"));
            }
            
            // Generar JWT real para usar en llamadas protegidas
            String token = seguridadService.generarToken(usuario);
            
            // Retornar respuesta exitosa
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("usuario", Map.of(
                "id", usuario.getId(),
                "nombre", usuario.getNombre(),
                "email", usuario.getEmail(),
                "rol", usuario.getRol()
            ));
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", "Error de autenticación: " + e.getMessage()));
        }
    }

    @PostMapping("/crear-usuario-prueba")
    @Operation(summary = "Crear usuario de prueba", description = "Crea un usuario rápidamente para pruebas")
    public ResponseEntity<?> crearUsuarioPrueba() {
        try {
            // Crear usuario admin si no existe
            if (!usuarioRepository.findByEmail("admin@residencial.com").isPresent()) {
                Usuario admin = new Usuario();
                admin.setNombre("Administrador del Sistema");
                admin.setApellido("Admin");
                admin.setEmail("admin@residencial.com");
                admin.setContrasena(passwordEncoder.encode("admin123"));
                admin.setRol(Rol.ADMIN);
                admin.setApartamento("ADMIN");
                admin.setActivo(true);
                
                usuarioRepository.save(admin);
                
                return ResponseEntity.ok(Map.of(
                    "mensaje", "Usuario admin creado exitosamente",
                    "email", "admin@residencial.com",
                    "password", "admin123",
                    "rol", "ADMIN"
                ));
            } else {
                Usuario admin = usuarioRepository.findByEmail("admin@residencial.com").get();
                return ResponseEntity.ok(Map.of(
                    "mensaje", "Usuario admin ya existe",
                    "email", admin.getEmail(),
                    "rol", admin.getRol()
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al crear usuario: " + e.getMessage()));
        }
    }

    @GetMapping("/usuarios")
    @Operation(summary = "Listar usuarios", description = "Obtiene todos los usuarios para depuración")
    public ResponseEntity<?> listarUsuarios() {
        try {
            return ResponseEntity.ok(usuarioRepository.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al listar usuarios: " + e.getMessage()));
        }
    }
}