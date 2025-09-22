package paqueteLibro;

public class Libro {
    private String name;
    private Autor autor;
    private double price;
    private int qty = 0;

    public Libro(String name, Autor author, double price) {
        this.name = name;
        this.autor = author;
        this.price = price;
    }

    public Libro(String name, Autor author, double price, int qty) {
        this.name = name;
        this.autor = author;
        this.price = price;
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public Autor getAuthor() {
        return autor;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
    
  //añadiendo un setter
    public void setName(String name) {
    	this.name=name;
    }

    @Override
    public String toString() {
        return "Libro[nombre=" + name + "," + autor.toString() +
               ",precio=" + price + ",cantidad=" + qty + "]";
    }
}
