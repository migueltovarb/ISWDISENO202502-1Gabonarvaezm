package com.residencial.acceso.service.impl;

import com.residencial.acceso.dto.SalidaRequest;
import com.residencial.acceso.exception.BusinessException;
import com.residencial.acceso.exception.NotFoundException;
import com.residencial.acceso.model.Entrada;
import com.residencial.acceso.model.Salida;
import com.residencial.acceso.repository.EntradaRepository;
import com.residencial.acceso.repository.SalidaRepository;
import com.residencial.acceso.repository.UsuarioRepository;
import com.residencial.acceso.service.ISalidaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalidaServiceImpl implements ISalidaService {
    private final SalidaRepository salidaRepository;
    private final EntradaRepository entradaRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public Salida registrarSalida(SalidaRequest request) {
        Entrada entrada = entradaRepository.findById(request.getEntradaId())
                .orElseThrow(() -> new NotFoundException("Entrada no encontrada"));
        
        if (salidaRepository.existsByEntradaId(request.getEntradaId())) {
            throw new BusinessException("Ya existe una salida registrada para esta entrada");
        }

        com.residencial.acceso.model.Usuario vigilante = usuarioRepository.findById(request.getVigilanteId())
                .orElseThrow(() -> new NotFoundException("Vigilante no encontrado"));

        if (vigilante.getRol() != com.residencial.acceso.model.Rol.VIGILANTE) {
            throw new BusinessException("Solo un vigilante puede registrar salidas");
        }

        Salida salida = new Salida();
        salida.setEntradaId(entrada.getId());
        salida.setRegistradoPor(vigilante.getNombre() + " " + vigilante.getApellido());
        
        return salidaRepository.save(salida);
    }

    @Override
    public List<Salida> listarTodas() {
        return salidaRepository.findAll();
    }

    @Override
    public void eliminar(String id) {
        Salida salida = salidaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Salida no encontrada"));
        salidaRepository.delete(salida);
    }
}