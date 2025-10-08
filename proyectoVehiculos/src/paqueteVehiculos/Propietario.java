package paqueteVehiculos;


public class Propietario {
private String nombre;
private String numeroContacto;

public Propietario(String nombre, String numeroContacto) {
	super();
	this.nombre = nombre;
	this.numeroContacto = numeroContacto;
}

public String getNombre() {
	return nombre;
}

public void setNombre(String nombre) {
	this.nombre = nombre;
}

public String getNumeroContacto() {
	return numeroContacto;
}

public void setNumeroContacto(String numeroContacto) {
	this.numeroContacto = numeroContacto;
}

@Override
public String toString() {
	return "Propietario [nombre=" + nombre + ", numeroContacto=" + numeroContacto + "]";
} 


}