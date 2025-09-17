package paqueteCirculo;
// verde metodos
//azul clases
//azul celeste inicializar el atributo
//naranja paquetes
//rojo palabras reservadas
//azul oscuro valor del atributo
//en los get siempre va el tipo de dato 
//en los setters le decimos que quiero en esat variable
public class circulo {

	private double radio;
	public circulo() {
		radio = 1.0;
	}
	
	public circulo(double radio) {
		this.radio = radio;
	}
	
	public double getRadio() {
		return radio;
	}

	public void setRadio(double radio) {
		this.radio = radio;
	}
	
	public double getArea() {
		double area=Math.PI * Math.pow(radio, 2);
		return area;
		}
	
	public double getPerimetro() {
		double perimetro = 2*Math.PI*radio;
		return perimetro;
	}
	@Override
	public String toString() {
		return "circulo [radio=" + radio + ", con area =" +   getArea() + " , y perimetro ="  + getPerimetro() + "]" ;
	}
	
	
}

