package com.residencial.acceso.controller;

import com.residencial.acceso.dto.SalidaRequest;
import com.residencial.acceso.model.Salida;
import com.residencial.acceso.service.ISalidaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salidas")
@RequiredArgsConstructor
@Tag(name = "Salidas", description = "Gestión de salidas del conjunto")
@SecurityRequirement(name = "bearerAuth")
public class SalidaController {
    private final ISalidaService salidaService;

    @PostMapping
    @Operation(summary = "Registrar salida", description = "Registrar salida de visitante o residente")
    public Salida registrarSalida(@Valid @RequestBody SalidaRequest request) {
        return salidaService.registrarSalida(request);
    }

    @GetMapping
    @Operation(summary = "Listar salidas", description = "Obtener todas las salidas registradas")
    public List<Salida> listarTodas() {
        return salidaService.listarTodas();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar salida", description = "Eliminar salida del sistema")
    public void eliminar(@PathVariable String id) {
        salidaService.eliminar(id);
    }
}