package primerExamen;

import java.util.Scanner;

public class Evaluacion1 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        final int diaSemana = 5;
        final int numEstudiantes = 4;
        String[][] asistencia = new String[numEstudiantes][diaSemana]; // P o A
        int opcion;

        
        for (int i = 0; i < numEstudiantes; i++) {
            System.out.println("\n  Registro de asistencia del estudiante " + (i + 1));
            for (int j = 0; j < diaSemana; j++) {
                String valor;
                while (true) {
                    System.out.print("Día " + (j + 1) + " (P = presente, A = ausente): ");
                    valor = sc.next().toUpperCase();
                    if (valor.equals("P") || valor.equals("A")) {
                        asistencia[i][j] = valor;
                        break;
                    } else {
                        System.out.println(" Valor inválido. Ingrese 'P' o 'A'.");
                    }
                }
            }
        }
 
        while (true) {
            System.out.println("\n Menú de Opciones");
            System.out.println("1. Ver asistencia individual");
            System.out.println("2. Ver resumen general");
            System.out.println("3. Volver a registrar");
            System.out.println("4. Salir");
            System.out.print("Elija una opción: ");

            while (!sc.hasNextInt()) {
                System.out.println(" Opción inválida. Intente de nuevo.");
                sc.next();
            }
            opcion = sc.nextInt();

            switch (opcion) {
                case 1: 
                    System.out.print("Ingrese el número del estudiante (1-4): ");
                    int est = sc.nextInt();
                    if (est >= 1 || est <= numEstudiantes) {
                        System.out.print("Asistencia del estudiante " + est);
                        for (int j = 0; j < diaSemana; j++) {
                            System.out.print(asistencia[est - 1][j]);
                        }
                        
                    } else {
                        System.out.println("Número inválido.");
                    }
                    break;

                case 2: 
                    System.out.println(" Resumen General");

                    
                    for (int i = 0; i < numEstudiantes; i++) {
                        int total = 0;
                        for (int j = 0; j < diaSemana; j++) {
                            if (asistencia[i][j].equals("P")) {
                                total++;
                            }
                        }
                        System.out.println("Estudiante " + (i + 1 )+ ": " + total  + " asistencias.");
                    }

                   
                    System.out.println("\n Estudiantes con asistencia completa:");
                    boolean algunoCompleto = false;
                    for (int i = 0; i < numEstudiantes; i++) {
                        int total = 0;
                        for (int j = 0; j < diaSemana; j++) {
                            if (asistencia[i][j].equals("P")) {
                                total++;
                            }
                        }
                        if (total == diaSemana) {
                            System.out.println("Estudiante " + (i + 1));
                            algunoCompleto = true;
                        }
                    }
                    if (!algunoCompleto) {
                        System.out.println("Ninguno");
                    }

                    
                    int[] ausenciasDias = new int[diaSemana];
                    for (int j = 0; j < diaSemana; j++) {
                        int ausencias = 0;
                        for (int i = 0; i < numEstudiantes; i++) {
                            if (asistencia[i][j].equals("A")) {
                                ausencias++;
                            }
                        }
                        ausenciasDias[j] = ausencias;
                    }

                    int maxAusencias = ausenciasDias[0];
                    for (int j = 1; j < diaSemana; j++) {
                        if (ausenciasDias[j] > maxAusencias) {
                            maxAusencias = ausenciasDias[j];
                        }
                    }

                    System.out.println(" Día(s) con más ausencias: ");
                    for (int j = 0; j < diaSemana; j++) {
                        if (ausenciasDias[j] == maxAusencias) {
                            System.out.print( "Dia "+ (j + 1) + " ");
                        }
                    }
                    System.out.println("con " + maxAusencias + " ausencias.");
                    break;

                case 3: 
                    for (int i = 0; i < numEstudiantes; i++) {
                        System.out.println("\n Registro de asistencia del estudiante " + (i + 1));
                        for (int j = 0; j < diaSemana; j++) {
                            String valor;
                            while (true) {
                                System.out.print("Día " + (j + 1) + " (P = presente, A = ausente): ");
                                valor = sc.next().toUpperCase();
                                if (valor.equals("P") || valor.equals("A")) {
                                    asistencia[i][j] = valor;
                                    break;
                                } else {
                                    System.out.println(" Valor inválido. Ingrese 'P' o 'A'.");
                                }
                            }
                        }
                    }
                    break;

                case 4:
                    System.out.println(" Saliendo del sistema...");
                    sc.close();
                    return;

                default:
                    System.out.println(" Opción inválida.");
            }
        }
    }
}
