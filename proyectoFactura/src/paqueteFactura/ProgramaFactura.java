package paqueteFactura;

public class ProgramaFactura {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Factura  item1 =new Factura("A102", "celular", 2, 1200.50 );
		
		//mostrar factura
		System.out.print(item1);
		
		//mostrar cantidad y precio unico
		System.out.println("Cantidad= "+ item1.getQty());
		System.out.println("precio= " + item1.unitPrice());
		
		//Mostramos el total 
		System.out.println("precio total es de: " + item1.getTotal());
		
		item1.setQty(4);
		System.out.println("su nueva cantidad es de: " + item1.getQty());
		System.out.println("su total ahora es de: " + item1.getTotal());
		
		//modificamos el precio 
		item1.setUnitPrice(1000.10);
		System.out.println("su  nuevo precio es de: "+ item1.unitPrice());
		System.out.println("su total nuevo es de " + item1.getTotal());
		
		System.out.println("Su factura actualizada es de: " + item1);

	}

}
