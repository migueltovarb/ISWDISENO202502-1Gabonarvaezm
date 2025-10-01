package calcularIMC;

public class Persona {
    private String nombre;
    private int edad;
    private String DNI;
    private char sexo;
    private double peso;
    private double altura;

    private static final char SEXO_DEFECTO = 'H';
    public static final int BAJO_PESO = -1;
    public static final int PESO_IDEAL = 0;
    public static final int SOBREPESO = 1;

    public Persona() {
        this("", 0, SEXO_DEFECTO, 0, 0);
    }

    public Persona(String nombre, int edad, char sexo) {
        this(nombre, edad, sexo, 0, 0);
    }

    public Persona(String nombre, int edad, char sexo, double peso, double altura) {
        this.nombre = nombre;
        this.edad = edad;
        this.sexo = comprobarSexo(sexo);
        this.peso = peso;
        this.altura = altura;
        this.DNI = generaDNI();
    }

    public int calcularIMC() {
        if (altura <= 0) return PESO_IDEAL; 
        double imc = peso / (altura * altura);
        if (imc < 20) return BAJO_PESO;
        else if (imc <= 25) return PESO_IDEAL;
        else return SOBREPESO;
    }

    public boolean esMayorDeEdad() {
        return edad >= 18;
    }

    private char comprobarSexo(char sexo) {
        return (sexo == 'H' || sexo == 'M') ? sexo : SEXO_DEFECTO;
    }


    private static int contadorDNI = 0; 
    private String generaDNI() {
        contadorDNI++;
        int numero = 10000000 + contadorDNI; 
        char letra = "TRWAGMYFPDXBNJZSQVHLCKE".charAt(numero % 23);
        return String.format("%08d%c", numero, letra);
    }

    @Override
    public String toString() {
        return "Persona [nombre=" + nombre + ", edad=" + edad + ", DNI=" + DNI + 
               ", sexo=" + sexo + ", peso=" + peso + ", altura=" + altura + "]";
    }

    public void setNombre(String nombre) { 
    	this.nombre = nombre; 
    	}
    
    public void setEdad(int edad) { 
    	this.edad = edad; 
    	}
    
    public void setSexo(char sexo) { 
    	this.sexo = comprobarSexo(sexo); 
    	}
    
    public void setPeso(double peso) { 
    	this.peso = peso; 
    	}
    
    public void setAltura(double altura) { 
    	this.altura = altura; 
    	}
}
