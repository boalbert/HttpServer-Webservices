package se.iths.plugin.model;
import javax.persistence.*;

@Entity
@Table(name="Contact")
public class Contact {

	@Id
	int id;
	private String firstName;
	private String lastName;

	public Contact() {
	}

	public String getFirstName() {
		return firstName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Contact(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
}
