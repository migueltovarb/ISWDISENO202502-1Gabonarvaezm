package paqueteVehiculos;

import java.util.Date;
import java.util.Scanner;

public class ProgramaVehiculo{

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Administrador admin = new Administrador("Administrador del Taller");

        int opcion;
        do {
            System.out.println("\n===== SISTEMA DE GESTIÓN DE SERVICIOS VEHICULARES =====");
            System.out.println("1. Registrar propietario");
            System.out.println("2. Registrar vehículo");
            System.out.println("3. Registrar servicio");
            System.out.println("4. Mostrar historial de servicios por vehículo");
            System.out.println("5. Calcular total gastado en servicios por vehículo");
            System.out.println("6. Mostrar todos los servicios registrados");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese nombre del propietario: ");
                    String nombreProp = sc.nextLine();
                    System.out.print("Ingrese número de contacto: ");
                    String numero = sc.nextLine();
                    Propietario propietario = new Propietario(nombreProp, numero);
                    admin.registrarPropietario(propietario);
                    break;

                case 2:
                    System.out.print("Ingrese la placa del vehículo: ");
                    String placa = sc.nextLine();
                    admin.verificarPlaca(placa);

                    // Validar que la placa no esté repetida
                    boolean placaExistente = false;
                    for (Vehiculo v : admin.buscarVehiculos()) {
                        if (v.getPlaca().equalsIgnoreCase(placa)) {
                            placaExistente = true;
                            break;
                        }
                    }
                    if (placaExistente) {
                        System.out.println("⚠️ No se puede registrar. La placa ya existe.");
                        break;
                    }

                    System.out.print("Ingrese nombre del propietario del vehículo: ");
                    String nombrePropVeh = sc.nextLine();
                    Propietario propEncontrado = null;
                    for (Propietario p : admin.buscarPropietarios()) {
                        if (p.getNombre().equalsIgnoreCase(nombrePropVeh)) {
                            propEncontrado = p;
                            break;
                        }
                    }

                    if (propEncontrado == null) {
                        System.out.println("⚠️ Propietario no encontrado. Regístrelo primero.");
                        break;
                    }

                    System.out.print("Ingrese marca: ");
                    String marca = sc.nextLine();
                    System.out.print("Ingrese modelo: ");
                    String modelo = sc.nextLine();

                    Vehiculo vehiculo = new Vehiculo(placa, propEncontrado, marca, modelo);
                    admin.registrarVehiculo(vehiculo);
                    break;

                case 3:
                    System.out.print("Ingrese la placa del vehículo: ");
                    String placaServ = sc.nextLine();

                    Vehiculo vehiculoServ = null;
                    for (Vehiculo v : admin.buscarVehiculos()) {
                        if (v.getPlaca().equalsIgnoreCase(placaServ)) {
                            vehiculoServ = v;
                            break;
                        }
                    }

                    if (vehiculoServ == null) {
                        System.out.println("⚠️ No se puede registrar el servicio. Vehículo no encontrado.");
                        break;
                    }

                    System.out.println("Seleccione tipo de servicio:");
                    System.out.println("1. CAMBIO DE ACEITE");
                    System.out.println("2. CAMBIO DE FRENOS");
                    System.out.println("3. REVISIÓN GENERAL");
                    int tipo = sc.nextInt();
                    sc.nextLine();

                    TipoServicio tipoServicio = null;
                    switch (tipo) {
                        case 1:
                            tipoServicio = TipoServicio.CAMBIOACEITE;
                            break;
                        case 2:
                            tipoServicio = TipoServicio.CAMBIOFRENOS;
                            break;
                        case 3:
                            tipoServicio = TipoServicio.REVISIONGENERAL;
                            break;
                        default:
                            System.out.println("Opción inválida.");
                            continue;
                    }

                    System.out.print("Ingrese el costo del servicio: ");
                    double costo = sc.nextDouble();
                    sc.nextLine();

                    if (costo <= 0) {
                        System.out.println("⚠️ El costo debe ser mayor a 0.");
                       break;
                    }

                    Servicio servicio = new Servicio(tipoServicio, vehiculoServ, new Date(), costo);
                    admin.registrarServicio(servicio);
                    break;

                case 4:
                    System.out.print("Ingrese la placa del vehículo: ");
                    String placaHist = sc.nextLine();
                    boolean encontrado = false;
                    for (Servicio s : admin.buscarServicios()) {
                        if (s.getVehiculo().getPlaca().equalsIgnoreCase(placaHist)) {
                            System.out.println(s);
                            encontrado = true;
                        }
                    }
                    if (!encontrado)
                        System.out.println("No hay servicios registrados para esa placa.");
                    break;

                case 5:
                    System.out.print("Ingrese la placa del vehículo: ");
                    String placaTotal = sc.nextLine();
                    double total = 0;
                    for (Servicio s : admin.buscarServicios()) {
                        if (s.getVehiculo().getPlaca().equalsIgnoreCase(placaTotal)) {
                            total += s.totalVehiculo();
                        }
                    }
                    if (total == 0)
                        System.out.println("No hay servicios registrados para esa placa.");
                    else
                        System.out.println("💰 Total gastado en servicios para " + placaTotal + ": $" + total);
                    break;

                case 6:
                    admin.mostrarServicio();
                    break;

                case 7:
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }

        } while (opcion != 7);

        sc.close();
    }
}
