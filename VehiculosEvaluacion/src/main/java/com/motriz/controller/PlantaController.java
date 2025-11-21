package com.motriz.controller;

import com.motriz.dto.PlantaDTO;
import com.motriz.model.Planta;
import com.motriz.service.PlantaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/plantas")
@RequiredArgsConstructor
@Slf4j
public class PlantaController {
    
    private final PlantaService plantaService;
    
    @PostMapping
    public ResponseEntity<Planta> crearPlanta(@Valid @RequestBody PlantaDTO dto) {
        log.info("POST - Crear nueva planta");
        Planta nodo = plantaService.crearPlanta(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nodo);
    }
    
    @GetMapping
    public ResponseEntity<List<Planta>> obtenerTodasLasPlantas() {
        log.info("GET - Obtener todas las plantas");
        List<Planta> nodos = plantaService.obtenerTodasLasPlantas();
        return ResponseEntity.ok(nodos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Planta> obtenerPlantaPorId(@PathVariable String id) {
        log.info("GET - Obtener planta por ID: {}", id);
        Planta nodo = plantaService.obtenerPlantaPorId(id);
        return ResponseEntity.ok(nodo);
    }
    
    @GetMapping("/fabrica/{fabricaId}")
    public ResponseEntity<List<Planta>> obtenerPlantasPorFabrica(@PathVariable String fabricaId) {
        log.info("GET - Obtener plantas de la fábrica: {}", fabricaId);
        List<Planta> nodos = plantaService.obtenerPlantasPorFabrica(fabricaId);
        return ResponseEntity.ok(nodos);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Planta> actualizarPlanta(
            @PathVariable String id,
            @Valid @RequestBody PlantaDTO dto) {
        log.info("PUT - Actualizar planta con ID: {}", id);
        Planta nodo = plantaService.actualizarPlanta(id, dto);
        return ResponseEntity.ok(nodo);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPlanta(@PathVariable String id) {
        log.info("DELETE - Eliminar planta con ID: {}", id);
        plantaService.eliminarPlanta(id);
        return ResponseEntity.noContent().build();
    }
}
