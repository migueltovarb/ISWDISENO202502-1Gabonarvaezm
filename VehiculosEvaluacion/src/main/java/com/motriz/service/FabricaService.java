package com.motriz.service;

import com.motriz.dto.FabricaDTO;
import com.motriz.exception.InvalidOperationException;
import com.motriz.exception.ResourceNotFoundException;
import com.motriz.model.Fabrica;
import com.motriz.repository.FabricaRepository;
import com.motriz.repository.PlantaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FabricaService {
    
    private final FabricaRepository fabricaRepository;
    private final PlantaRepository plantaRepository;
    
    public Fabrica crearFabrica(FabricaDTO dto) {
        log.info("Creando nueva fábrica: {}", dto.getNombre());
        
        // Verificar que el nombre no exista
        Optional<Fabrica> existente = fabricaRepository.findByNombre(dto.getNombre());
        if (existente.isPresent()) {
            throw new InvalidOperationException("Ya existe una fábrica con el nombre: " + dto.getNombre());
        }
        
        Fabrica operador = new Fabrica(dto.getNombre(), dto.getPais());
        operador.setPlantasIds(new ArrayList<>());
        
        Fabrica registrada = fabricaRepository.save(operador);
        log.info("Fábrica creada exitosamente con ID: {}", registrada.getId());
        return registrada;
    }
    
    public List<Fabrica> obtenerTodasLasFabricas() {
        log.info("Obteniendo todas las fábricas");
        return fabricaRepository.findAll();
    }
    
    public Fabrica obtenerFabricaPorId(String id) {
        log.info("Obteniendo fábrica con ID: {}", id);
        return fabricaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fábrica no encontrada con ID: " + id));
    }
    
    public Fabrica actualizarFabrica(String id, FabricaDTO dto) {
        log.info("Actualizando fábrica con ID: {}", id);
        
        Fabrica operador = obtenerFabricaPorId(id);
        
        if (dto.getNombre() != null) {
            operador.setNombre(dto.getNombre());
        }
        if (dto.getPais() != null) {
            operador.setPais(dto.getPais());
        }
        
        Fabrica modificada = fabricaRepository.save(operador);
        log.info("Fábrica actualizada exitosamente");
        return modificada;
    }
    
    public void eliminarFabrica(String id) {
        log.info("Eliminando fábrica con ID: {}", id);
        
        Fabrica operador = obtenerFabricaPorId(id);
        
        // Verificar si tiene plantas asociadas
        List<String> plantasIds = operador.getPlantasIds();
        if (plantasIds != null && !plantasIds.isEmpty()) {
            throw new InvalidOperationException("No se puede eliminar la fábrica porque tiene plantas asociadas. " +
                    "Primero debe eliminar todas las plantas.");
        }
        
        fabricaRepository.deleteById(id);
        log.info("Fábrica eliminada exitosamente");
    }
    
    public void agregarPlantaAFabrica(String fabricaId, String plantaId) {
        log.info("Agregando planta {} a fábrica {}", plantaId, fabricaId);
        
        Fabrica operador = obtenerFabricaPorId(fabricaId);
        if (!operador.getPlantasIds().contains(plantaId)) {
            operador.getPlantasIds().add(plantaId);
            fabricaRepository.save(operador);
        }
    }
    
    public void removerPlantaDeFabrica(String fabricaId, String plantaId) {
        log.info("Removiendo planta {} de fábrica {}", plantaId, fabricaId);
        
        Fabrica operador = obtenerFabricaPorId(fabricaId);
        operador.getPlantasIds().remove(plantaId);
        fabricaRepository.save(operador);
    }
}
