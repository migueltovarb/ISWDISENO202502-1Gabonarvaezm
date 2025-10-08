package paqueteVehiculos;

import java.util.Date;

public class Servicio {

	private TipoServicio servicio;
	private Vehiculo vehiculo;
	private Date fecha;
	private double costo;
	public Servicio(TipoServicio servicio, Vehiculo vehiculo, Date fecha, double costo) {
		super();
		this.servicio = servicio;
		this.vehiculo = vehiculo;
		this.fecha = fecha;
		this.costo = costo;
	}
	public TipoServicio getServicio() {
		return servicio;
	}
	public void setServicio(TipoServicio servicio) {
		this.servicio = servicio;
	}
	public Vehiculo getVehiculo() {
		return vehiculo;
	}
	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public double getCosto() {
		return costo;
	}
	public void setCosto(double costo) {
		this.costo = costo;
	}
	@Override
	public String toString() {
		return "Propietario [servicio=" + servicio + ", vehiculo=" + vehiculo + ", fecha=" + fecha + ", costo=" + costo
				+ "]";
	}

	//LOGICA
	
	public double totalVehiculo() {
		return costo;
	}


	}

