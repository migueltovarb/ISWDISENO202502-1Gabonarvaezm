package paqueteAutor;

public class ProgramaAutor {

    public static void main(String[] args) {
        
        // Crear objetos Autor
        Autor autor1 = new Autor("Gabriel García Márquez", "gabo@correo.com", 'm');
        Autor autor2 = new Autor("Isabel Allende", "isabel@correo.com", 'f');

        // Mostrar autores completos
        System.out.println(autor1);
        System.out.println(autor2);

        // Mostrar datos por separado
        System.out.println("Nombre: " + autor1.getName());
        System.out.println("Email: " + autor1.getEmail());
        System.out.println("Género: " + autor1.getGender());

        // Cambiar el email del primer autor
        autor1.setEmail("nuevoCorreo@correo.com");
        System.out.println("Email actualizado de " + autor1.getName() + ": " + autor1.getEmail());

        // Volver a mostrar autor1 después del cambio
        System.out.println("Autor actualizado -> " + autor1);
    }
}
