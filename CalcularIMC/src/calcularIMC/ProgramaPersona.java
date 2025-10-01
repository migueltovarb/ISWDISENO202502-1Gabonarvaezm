package calcularIMC;

import java.util.Scanner;

public class ProgramaPersona {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Edad: ");
        int edad = sc.nextInt();

        System.out.print("Sexo ('H' para hombre o 'M' para mujer): ");
        char sexo = sc.next().charAt(0);

        System.out.print("Peso (kg): ");
        double peso = sc.nextDouble();

        System.out.print("Altura (m): ");
        double altura = sc.nextDouble();

        Persona p1 = new Persona(nombre, edad, sexo, peso, altura);
        Persona p2 = new Persona(nombre, edad, sexo);
        Persona p3 = new Persona();

        System.out.println("\nPersona 1");
        System.out.println(p1);
        imprimirDatos(p1);

        System.out.println("\nPersona 2");
        System.out.println(p2);
        imprimirDatos(p2);

        System.out.println("\nPersona 3");
        System.out.println(p3);
        imprimirDatos(p3);

        sc.close();
    }

    private static void imprimirDatos(Persona p) {
        int imc = p.calcularIMC();
        switch (imc) {
            case Persona.BAJO_PESO -> System.out.println("IMC: Bajo peso");
            case Persona.PESO_IDEAL -> System.out.println("IMC: Peso ideal");
            case Persona.SOBREPESO -> System.out.println("IMC: Sobrepeso");
        }
        System.out.println(p.esMayorDeEdad() ? "Es mayor de edad" : "Es menor de edad");
    }
}
