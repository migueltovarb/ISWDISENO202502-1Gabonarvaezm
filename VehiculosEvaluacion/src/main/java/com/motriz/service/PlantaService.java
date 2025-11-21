package com.motriz.service;

import com.motriz.dto.PlantaDTO;
import com.motriz.exception.InvalidOperationException;
import com.motriz.exception.ResourceNotFoundException;
import com.motriz.model.Planta;
import com.motriz.repository.PlantaRepository;
import com.motriz.repository.VehiculoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlantaService {
    
    private final PlantaRepository plantaRepository;
    private final VehiculoRepository vehiculoRepository;
    private final FabricaService fabricaService;
    
    public Planta crearPlanta(PlantaDTO dto) {
        log.info("Creando nueva planta: {}", dto.getNombre());
        
        // Validar que fabricaId no sea nulo o vacío
        if (dto.getFabricaId() == null || dto.getFabricaId().isBlank()) {
            throw new InvalidOperationException("El ID de la fábrica es requerido");
        }
        
        // Verificar que la fábrica existe
        fabricaService.obtenerFabricaPorId(dto.getFabricaId());
        
        // Verificar que el nombre no exista
        Optional<Planta> existente = plantaRepository.findByNombre(dto.getNombre());
        if (existente.isPresent()) {
            throw new InvalidOperationException("Ya existe una planta con el nombre: " + dto.getNombre());
        }
        
        Planta nodo = new Planta(dto.getNombre(), dto.getUbicacion(), dto.getFabricaId());
        Planta registrada = plantaRepository.save(nodo);
        
        // Agregar el ID de la planta a la fábrica
        fabricaService.agregarPlantaAFabrica(dto.getFabricaId(), registrada.getId());
        
        log.info("Planta creada exitosamente con ID: {}", registrada.getId());
        return registrada;
    }
    
    public List<Planta> obtenerTodasLasPlantas() {
        log.info("Obteniendo todas las plantas");
        return plantaRepository.findAll();
    }
    
    public Planta obtenerPlantaPorId(String id) {
        log.info("Obteniendo planta con ID: {}", id);
        return plantaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Planta no encontrada con ID: " + id));
    }
    
    public List<Planta> obtenerPlantasPorFabrica(String fabricaId) {
        log.info("Obteniendo plantas de la fábrica: {}", fabricaId);
        
        // Verificar que la fábrica existe
        fabricaService.obtenerFabricaPorId(fabricaId);
        
        return plantaRepository.findByFabricaId(fabricaId);
    }
    
    public Planta actualizarPlanta(String id, PlantaDTO dto) {
        log.info("Actualizando planta con ID: {}", id);
        
        Planta nodo = obtenerPlantaPorId(id);
        
        if (dto.getNombre() != null) {
            nodo.setNombre(dto.getNombre());
        }
        if (dto.getUbicacion() != null) {
            nodo.setUbicacion(dto.getUbicacion());
        }
        
        Planta ajustada = plantaRepository.save(nodo);
        log.info("Planta actualizada exitosamente");
        return ajustada;
    }
    
    public void eliminarPlanta(String id) {
        log.info("Eliminando planta con ID: {}", id);
        
        Planta nodo = obtenerPlantaPorId(id);
        
        // Verificar si tiene vehículos asociados
        long vehiculosCount = vehiculoRepository.findByPlantaId(id).size();
        if (vehiculosCount > 0) {
            throw new InvalidOperationException("No se puede eliminar la planta porque tiene vehículos asociados. " +
                    "Primero debe eliminar todos los vehículos.");
        }
        
        // Remover de la fábrica
        fabricaService.removerPlantaDeFabrica(nodo.getFabricaId(), id);
        
        plantaRepository.deleteById(id);
        log.info("Planta eliminada exitosamente");
    }
}
