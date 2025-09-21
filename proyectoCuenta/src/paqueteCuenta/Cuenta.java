package paqueteCuenta;

public class Cuenta {

private String id;
private String name; 
private int balance =0;

public Cuenta(String id, String name) {
 this.id=id;
 this.name=name;
}

public Cuenta(String id, String name, int balance) {
	 this.id=id;
	 this.name=name;
	 this.balance=balance;
	}
//GETTERS
public String getId() {
	return id;
}
public String getName() {
	return name;
}
public int getBalance() {
	return balance;
}

//otros metodos 
public int Credit( int amount) {
	balance += amount;
	return balance;
	}

public int debit(int amount) {
	if (amount<=balance) {
		balance-=amount;
	} else {
		System.out.println("su cantidad excedio el balance");
	}
	return balance;
}

public int toTransfer(Cuenta another, int amount) {
	if (amount<=balance) {
		balance-=amount;
		another.Credit(amount);
		}else {
			System.out.println("La cantidad excedio el balance de su cuenta");
		}
	return balance;
}

@Override
public String toString() {
    return "Employee[id=" + id + ", name=" + getName() + ", balance=" + balance + "]";
}

}

