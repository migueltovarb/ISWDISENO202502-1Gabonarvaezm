package paqueteLibro;

public class PrgramaLibro {
    public static void main(String[] args) {
        Autor autor = new Autor("Gabriel García Márquez", "gabo@gmail.com", 'M');
        Libro libro = new Libro("Cien Años de Soledad", autor, 45000, 5);

        System.out.println(autor);
        System.out.println(libro);
        
        //setters en funcion 
        
        libro.setPrice(120.599);
        System.out.println("\nEl precio del libro se a actualizado ahora es de: " + libro.getPrice());
        
        libro.setQty(3);
        System.out.println("\nEl cliente prefirio reducir la cantidad de libros, ahors su nueva cantidad es de: "+ libro.getQty());
        
        
       
        autor.setName("Will Smith");
        System.out.println("\nEl autor del libro ahora es:" + autor.getName());
        
        
        System.out.println("\n se ah actualizado toda la informacion... ");
        System.out.println(autor);
        System.out.println(libro);
        
    }
}
