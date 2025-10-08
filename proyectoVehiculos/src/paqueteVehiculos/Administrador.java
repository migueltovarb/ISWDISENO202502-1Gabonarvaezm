package paqueteVehiculos;

import java.util.ArrayList;
import java.util.List;

public class Administrador {
    private String nombre;
    private List<Vehiculo> vehiculos;
    private List<Propietario> propietarios;
    private List<Servicio> servicios;

    // Constructor
    public Administrador(String nombre) {
        this.nombre = nombre;
        this.vehiculos = new ArrayList<>();
        this.propietarios = new ArrayList<>();
        this.servicios = new ArrayList<>();
    }

    // Método para registrar un vehículo
    public void registrarVehiculo(Vehiculo vehiculo) {
        vehiculos.add(vehiculo);
        System.out.println("Vehículo registrado correctamente: " + vehiculo.getPlaca());
    }

    // Método para registrar un propietario
    public void registrarPropietario(Propietario propietario) {
        propietarios.add(propietario);
        System.out.println("Propietario registrado correctamente: " + propietario.getNombre());
    }

    // Método para registrar un servicio
    public void registrarServicio(Servicio servicio) {
        servicios.add(servicio);
        System.out.println("Servicio registrado: " + servicio.getServicio());
    }

    // Método para verificar una placa
    public void verificarPlaca(String placa) {
        for (Vehiculo v : vehiculos) {
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                System.out.println("La placa " + placa + " ya está registrada.");
                return;
            }
        }
        System.out.println("La placa " + placa + " no está registrada.");
    }

    // Método para mostrar los servicios registrados
    public void mostrarServicio() {
        System.out.println("\n=== SERVICIOS REGISTRADOS ===");
        for (Servicio s : servicios) {
            System.out.println(s.toString());
        }
    }

    // Método para buscar un vehículo
    public Vehiculo buscarVehiculo(Vehiculo vehiculo) {
        for (Vehiculo v : vehiculos) {
            if (v.getPlaca().equalsIgnoreCase(vehiculo.getPlaca())) {
                System.out.println("Vehículo encontrado: " + v.toString());
                return v;
            }
        }
        System.out.println("Vehículo no encontrado.");
        return null;
    }

    // Getter y Setter del nombre
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
        public List<Vehiculo> buscarVehiculos() {
            return vehiculos;
        }

        public List<Propietario> buscarPropietarios() {
            return propietarios;
        }

        public List<Servicio> buscarServicios() {
            return servicios;
        }

}

