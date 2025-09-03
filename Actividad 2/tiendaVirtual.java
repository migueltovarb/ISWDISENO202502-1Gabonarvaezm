package PracticaJavaDiaEvaluacion;

import java.util.Scanner;

public class tiendaVirtual {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
			final double descRopa=0.10;
			   final double descTecno=0.20;
			   final double descAlim=0.05;
			   final double descExtra=0.10;
			   
			   int compra=0;
			   while(compra<1) {
				   System.out.print("ingrese el numero de productos (tiene que ser al menos 1)");
				   compra=sc.nextInt();
				   
			   }
			   
			   int[] precios=new int[compra];
			   
			   double totalSinDescuento=0;
			   double totalConDescuento=0;
			   
			   int i=0;
			   while (i< compra) {
				   System.out.println("producto numero "+ ( i+1));
				  			   
				   System.out.print("ingrese el precio del producto");
				   precios[i]	=sc.nextInt();
				   
				   System.out.print("que tipo de producto es? 1. ropa  2. tecnologia 3. alimentos");
				   int tipo=sc.nextInt();
				   
				   totalSinDescuento +=precios[i];
				   double precioFinal = precios[i];
				   
	
				   
				   
				   switch(tipo) {
				   case 1: 
					   precioFinal -= precios[i]*descRopa;
					   break;
					   
				   case 2:
					   precioFinal -= precios[i]*descTecno;
					   break;
					   
				   case 3: precioFinal-= precios[i]*descAlim;
				   break;
				   default:
					   System.out.println("Ocion invalida, imposible de escoger, seleccione otro");
					   
				   }
				   totalConDescuento += precioFinal;
				     i++;
			   }
			    
			   if(totalConDescuento > 500000) {
				   totalConDescuento -= totalConDescuento*descExtra;
			   }
			   System.out.println("\n Su factura final es");
			   System.out.println("Su compra sin descuento es de:  "+ totalSinDescuento);
			   System.out.println("Su total con descuento es de: "+ totalConDescuento);
			   System.out.println("Su ahorro fue de: "+ (totalSinDescuento - totalConDescuento));
		}
       
    }
}