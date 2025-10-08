package paqueteVehiculos;

public class Vehiculo {
private String placa;
private Propietario propietario;
private String marca;
private String modelo;
public Vehiculo(String placa, Propietario propietario, String marca, String modelo) {
	super();
	this.placa = placa;
	this.propietario = propietario;
	this.marca = marca;
	this.modelo = modelo;
}
public String getPlaca() {
	return placa;
}
public void setPlaca(String placa) {
	this.placa = placa;
}
public Propietario getPropietario() {
	return propietario;
}
public void setPropietario(Propietario propietario) {
	this.propietario = propietario;
}
public String getMarca() {
	return marca;
}
public void setMarca(String marca) {
	this.marca = marca;
}
public String getModelo() {
	return modelo;
}
public void setModelo(String modelo) {
	this.modelo = modelo;
}
@Override
public String toString() {
	return "Vehiculo [placa=" + placa + ", propietario=" + propietario + ", marca=" + marca + ", modelo=" + modelo
			+ "]";
}

}
