package com.residencial.acceso.controller;

import com.residencial.acceso.dto.VisitanteDTO;
import com.residencial.acceso.model.Visitante;
import com.residencial.acceso.service.IVisitaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visitantes")
@RequiredArgsConstructor
@Tag(name = "Visitantes", description = "Gestión de visitantes")
@SecurityRequirement(name = "bearerAuth")
public class VisitaController {
    private final IVisitaService visitaService;

    @PostMapping
    @Operation(summary = "Crear visitante", description = "Registrar nuevo visitante")
    public Visitante crear(@Valid @RequestBody VisitanteDTO dto) {
        return visitaService.crear(dto);
    }

    @GetMapping
    @Operation(summary = "Listar visitantes", description = "Obtener todos los visitantes")
    public List<Visitante> listar() {
        return visitaService.listar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener visitante", description = "Obtener visitante por ID")
    public Visitante obtenerPorId(@PathVariable String id) {
        return visitaService.obtenerPorId(id);
    }

    @GetMapping("/residente/{residenteId}")
    @Operation(summary = "Listar por residente", description = "Obtener visitantes por residente")
    public List<Visitante> listarPorResidente(@PathVariable String residenteId) {
        return visitaService.listarPorResidente(residenteId);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar visitante", description = "Actualizar información de visitante")
    public Visitante actualizar(@PathVariable String id, @Valid @RequestBody VisitanteDTO dto) {
        return visitaService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar visitante", description = "Eliminar visitante del sistema")
    public void eliminar(@PathVariable String id) {
        visitaService.eliminar(id);
    }
}