package paqueteCuenta;

public class ProgramaCuenta {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Cuenta cuenta1=new Cuenta("908226", "pepito perez", 1000);
		Cuenta cuenta2= new Cuenta("907423", "paquita gomez", 1500 );	
		//mostramos la cuenta 
		System.out.println(cuenta1);
		System.out.println(cuenta2);
	
		
		//mostramos todos los datos por separado 
		System.out.println("\nEstos son los datos de la cuenta 1");
		System.out.println("este es su id "+ cuenta1.getId());
		System.out.println("este es su nombre "+ cuenta1.getName());
		System.out.println("este es su balance "+ cuenta1.getBalance());
		
		
		System.out.println("\nEstos son los datos de la cuenta 2");
		System.out.println("este es su id "+ cuenta2.getId());
		System.out.println("este es su nombre "+ cuenta2.getName());
		System.out.println("este es su balance "+ cuenta2.getBalance());
		
		
		//para tranferencias
		System.out.println("\n pasando $270 desde cuenta 1 a cuenta 2");
		cuenta1.toTransfer(cuenta2, 270);
		System.out.println( "el nuevo balance de la cuenta 2 es de: "+ cuenta2.getBalance());
	
		//miramos si funciona el else de transferir
		
		System.out.println("\n pasando $2000 desde cuenta 1 a cuenta 2");
		cuenta1.toTransfer(cuenta2, 2000);
		
		
		//probando el metodo credit
		System.out.println("\n añadiendo $200 a la cuenta 1 ");
		cuenta1.Credit(200);
		System.out.println("se añadieron los $200 a su cuenta, ahora tiene " + cuenta1.getBalance());
		
		
		//probando el metodo debit
		
		
		System.out.println("\n retirando de la cuenta 1");
		cuenta1.debit(600);
		System.out.println( "Se retiraron $600 de la cuenta 1 ahora su balance es de: "+ cuenta1.getBalance());

		
		//probando el  si funciona el else 
		
		System.out.println("\n retirando de la cuenta 1");
		cuenta1.debit(800);
		
		
		System.out.println("\nEsta es la cuenta actualizada del numero 1" + cuenta1);
		System.out.println("\nEsta es la cuenta actualizada del numero 2"+ cuenta2);

		
		
	}

}
