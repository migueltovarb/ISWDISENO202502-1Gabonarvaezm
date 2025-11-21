package com.residencial.acceso.controller;

import com.residencial.acceso.model.Usuario;
import com.residencial.acceso.model.Rol;
import com.residencial.acceso.repository.UsuarioRepository;
import com.residencial.acceso.service.impl.SeguridadServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/setup")
@RequiredArgsConstructor
@Tag(name = "Configuración", description = "Endpoints para configuración inicial")
public class SetupController {
    
    private final UsuarioRepository usuarioRepository;
    private final SeguridadServiceImpl seguridadService;

    @PostMapping("/crear-usuarios-prueba")
    @Operation(summary = "Crear usuarios de prueba", description = "Crea usuarios administrativos para pruebas")
    public ResponseEntity<String> crearUsuariosPrueba() {
        try {
            // Crear usuario admin
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
            }

            // Crear usuario vigilante
            if (!usuarioRepository.findByEmail("vigilante@residencial.com").isPresent()) {
                Usuario vigilante = new Usuario();
                vigilante.setNombre("Vigilante Principal");
                vigilante.setApellido("Vigilante");
                vigilante.setEmail("vigilante@residencial.com");
                vigilante.setContrasena(seguridadService.encriptarPassword("vigilante123"));
                vigilante.setRol(Rol.VIGILANTE);
                vigilante.setApartamento("VIGILANCIA");
                vigilante.setActivo(true);
                
                usuarioRepository.save(vigilante);
            }

            // Crear usuario vigilante adicional (según README)
            if (!usuarioRepository.findByEmail("vigilante1@residencial.com").isPresent()) {
                Usuario vigilante1 = new Usuario();
                vigilante1.setNombre("Vigilante Secundario");
                vigilante1.setApellido("Vigilante");
                vigilante1.setEmail("vigilante1@residencial.com");
                vigilante1.setContrasena(seguridadService.encriptarPassword("vigilante123"));
                vigilante1.setRol(Rol.VIGILANTE);
                vigilante1.setApartamento("VIGILANCIA");
                vigilante1.setActivo(true);
                
                usuarioRepository.save(vigilante1);
            }

            // Crear usuario residente
            if (!usuarioRepository.findByEmail("residente@residencial.com").isPresent()) {
                Usuario residente = new Usuario();
                residente.setNombre("Residente Ejemplo");
                residente.setApellido("Residente");
                residente.setEmail("residente@residencial.com");
                residente.setContrasena(seguridadService.encriptarPassword("residente123"));
                residente.setRol(Rol.RESIDENTE);
                residente.setApartamento("101");
                residente.setActivo(true);
                
                usuarioRepository.save(residente);
            }

            return ResponseEntity.ok("Usuarios de prueba creados exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear usuarios: " + e.getMessage());
        }
    }
}