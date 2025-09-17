package paqueteCirculo;

public class programaCirculo {

	public static void main(String[] args) {
	circulo miCirculo=new circulo();
	double area = miCirculo.getArea(); //inicial
	System.out.println("El area del circulo es: " + area);
	miCirculo.setRadio(300);
	area=miCirculo.getArea();//despues de cambiar el radio 
	System.out.println("El area del circulo es: " + area);
	
	circulo miSegundoCirculo=new circulo(400);
	area = miSegundoCirculo.getArea();
	System.out.println("El area del segundo circulo es: " + area);
	
	double perimetro= miSegundoCirculo.getPerimetro();
	System.out.println("El perimetro del segundo circulo es: " + perimetro);
	
	System.out.println(miSegundoCirculo);
}
}
