package com.residencial.acceso.service;

import com.residencial.acceso.model.Usuario;

public interface ISeguridadService {
    String generarToken(Usuario usuario);
    String extraerEmail(String token);
    boolean esTokenValido(String token, Usuario usuario);
    boolean verificarPassword(String rawPassword, String encodedPassword);
    String encriptarPassword(String rawPassword);
}