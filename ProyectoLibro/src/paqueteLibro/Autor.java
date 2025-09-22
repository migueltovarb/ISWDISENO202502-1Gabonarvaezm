package paqueteLibro;

public class Autor {
    private String name;
    private String email;
    private char gender;

    public Autor(String name, String email, char gender) {
        this.name = name;
        this.email = email;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public char getGender() {
        return gender;
    }
    
    //añadiendo un setters
    public void setName(String name) {
    	this.name=name;
    }

    @Override
    public String toString() {
        return "Autor[nombre=" + name + ",email=" + email + ",genero=" + gender + "]";
    }
}
