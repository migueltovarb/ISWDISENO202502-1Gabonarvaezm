package com.residencial.acceso.controller;

import com.residencial.acceso.dto.EntradaResidenteRequest;
import com.residencial.acceso.dto.EntradaVisitanteRequest;
import com.residencial.acceso.model.Entrada;
import com.residencial.acceso.service.IEntradaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entradas")
@RequiredArgsConstructor
@Tag(name = "Entradas", description = "Gestión de entradas al conjunto")
@SecurityRequirement(name = "bearerAuth")
public class EntradaController {
    private final IEntradaService entradaService;

    @PostMapping("/visitante")
    @Operation(summary = "Registrar entrada visitante", description = "Registrar entrada de visitante al conjunto")
    public Entrada registrarEntradaVisitante(@Valid @RequestBody EntradaVisitanteRequest request) {
        return entradaService.registrarEntradaVisitante(request);
    }

    @PostMapping("/residente")
    @Operation(summary = "Registrar entrada residente", description = "Registrar entrada de residente al conjunto")
    public Entrada registrarEntradaResidente(@Valid @RequestBody EntradaResidenteRequest request) {
        return entradaService.registrarEntradaResidente(request);
    }

    @GetMapping
    @Operation(summary = "Listar entradas", description = "Obtener todas las entradas registradas")
    public List<Entrada> listarTodas() {
        return entradaService.listarTodas();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar entrada", description = "Eliminar entrada del sistema")
    public void eliminar(@PathVariable String id) {
        entradaService.eliminar(id);
    }
}