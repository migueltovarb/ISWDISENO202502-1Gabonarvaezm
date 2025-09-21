package paqueteFactura;

public class Factura {

	private String id;
	private String desc;
	private int qty;
	private double unitPrice;
	
	//constructor
	public Factura(String id, String desc, int qty, double unitPrice) {
		this.id=id;
		this.desc=desc;
		this.qty=qty;
		this.unitPrice=unitPrice;
		}

	//metodos getter
	public String getId() {
		return id;
	}
	 
	public String getDesc() {
		return desc;
	}
	
	public int getQty() {
		return qty;
	}
	public double unitPrice() {
		return unitPrice;
	}

	//metods setters 
	public void setQty(int qty) {
		this.qty=qty;
	}
	
	public void setUnitPrice(double unitPrice) {
		this.unitPrice=unitPrice;
	
	}
	
	//adicionales
	
	public double getTotal() {
		return unitPrice*qty;
	}
	
	public String toString() {
		return "Factura [id=" + id + ", desc= " + desc + ", qty= "+ qty + ", unitPrice= " + unitPrice + "]";
				}

}

