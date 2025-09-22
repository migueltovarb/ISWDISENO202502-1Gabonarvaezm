package paqueteAutor;

public class Autor {

	private String name;
	private String email;
	private char gender;
	
	public Autor(String name, String email, char gender) {
		this.name=name;
		this.email=email;
		this.gender=gender;
	}
	
	//getters
	public String getName() {
		return name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public char getGender() {
		return gender;
	}
	
	public void setEmail(String email) {
		this.email=email;
	}

	@Override
	public String toString() {
		return "Autor [nombre= "
				+ name + ", email= " + email +" genero= " + gender
				+ "]";
		
	}
	

}
