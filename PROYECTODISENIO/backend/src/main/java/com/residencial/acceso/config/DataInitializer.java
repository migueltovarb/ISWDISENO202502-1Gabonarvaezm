package com.residencial.acceso.config;

import com.residencial.acceso.model.Usuario;
import com.residencial.acceso.model.Rol;
import com.residencial.acceso.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initData() {
        return args -> {
            // Verificar si ya existe un usuario admin
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
                System.out.println("Usuario administrador creado: admin@residencial.com / admin123");
            }

            // Crear usuario vigilante de prueba
            if (!usuarioRepository.findByEmail("vigilante@residencial.com").isPresent()) {
                Usuario vigilante = new Usuario();
                vigilante.setNombre("Vigilante Principal");
                vigilante.setApellido("Vigilante");
                vigilante.setEmail("vigilante@residencial.com");
                vigilante.setContrasena(passwordEncoder.encode("vigilante123"));
                vigilante.setRol(Rol.VIGILANTE);
                vigilante.setApartamento("VIGILANCIA");
                vigilante.setActivo(true);
                
                usuarioRepository.save(vigilante);
                System.out.println("Usuario vigilante creado: vigilante@residencial.com / vigilante123");
            }

            // Crear usuario vigilante adicional (según README)
            if (!usuarioRepository.findByEmail("vigilante1@residencial.com").isPresent()) {
                Usuario vigilante1 = new Usuario();
                vigilante1.setNombre("Vigilante Secundario");
                vigilante1.setApellido("Vigilante");
                vigilante1.setEmail("vigilante1@residencial.com");
                vigilante1.setContrasena(passwordEncoder.encode("vigilante123"));
                vigilante1.setRol(Rol.VIGILANTE);
                vigilante1.setApartamento("VIGILANCIA");
                vigilante1.setActivo(true);

                usuarioRepository.save(vigilante1);
                System.out.println("Usuario vigilante creado: vigilante1@residencial.com / vigilante123");
            }

            // Crear usuario residente de prueba
            if (!usuarioRepository.findByEmail("residente@residencial.com").isPresent()) {
                Usuario residente = new Usuario();
                residente.setNombre("Residente Ejemplo");
                residente.setApellido("Residente");
                residente.setEmail("residente@residencial.com");
                residente.setContrasena(passwordEncoder.encode("residente123"));
                residente.setRol(Rol.RESIDENTE);
                residente.setApartamento("101");
                residente.setActivo(true);
                
                usuarioRepository.save(residente);
                System.out.println("Usuario residente creado: residente@residencial.com / residente123");
            }
        };
    }
}