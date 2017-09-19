package dbapi;

public class Client {
	private int id_client;
	private String name;
	private String lastName;
	private String patronymic;
	
	
	public Client(int id_client, String name, String lastName, String patronymic) {
		this.id_client = id_client;
		this.name = name;
		this.lastName = lastName;
		this.patronymic = patronymic;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPatronymic() {
		return patronymic;
	}

	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}
	public int getId_client() {
		return id_client;
	}
	public void setId_client(int id_client) {
		this.id_client = id_client;
	}

	@Override
	public String toString(){
		return "["+id_client+"]["+name+"]["+lastName+"]["+patronymic+"]\n";
	}
	

}
