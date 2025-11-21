package com.motriz.controller;

import com.motriz.dto.FabricaDTO;
import com.motriz.model.Fabrica;
import com.motriz.service.FabricaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/fabricas")
@RequiredArgsConstructor
@Slf4j
public class FabricaController {
    
    private final FabricaService fabricaService;
    
    @PostMapping
    public ResponseEntity<Fabrica> crearFabrica(@Valid @RequestBody FabricaDTO dto) {
        log.info("POST - Crear nueva fábrica");
        Fabrica operador = fabricaService.crearFabrica(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(operador);
    }
    
    @GetMapping
    public ResponseEntity<List<Fabrica>> obtenerTodasLasFabricas() {
        log.info("GET - Obtener todas las fábricas");
        List<Fabrica> operadores = fabricaService.obtenerTodasLasFabricas();
        return ResponseEntity.ok(operadores);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Fabrica> obtenerFabricaPorId(@PathVariable String id) {
        log.info("GET - Obtener fábrica por ID: {}", id);
        Fabrica operador = fabricaService.obtenerFabricaPorId(id);
        return ResponseEntity.ok(operador);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Fabrica> actualizarFabrica(
            @PathVariable String id,
            @Valid @RequestBody FabricaDTO dto) {
        log.info("PUT - Actualizar fábrica con ID: {}", id);
        Fabrica operador = fabricaService.actualizarFabrica(id, dto);
        return ResponseEntity.ok(operador);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFabrica(@PathVariable String id) {
        log.info("DELETE - Eliminar fábrica con ID: {}", id);
        fabricaService.eliminarFabrica(id);
        return ResponseEntity.noContent().build();
    }
}
