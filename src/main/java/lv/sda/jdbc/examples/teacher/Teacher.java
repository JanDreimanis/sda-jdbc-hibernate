package lv.sda.jdbc.examples.teacher;

public class Teacher {

	private int id;
	private String firstName;
	private String lastName;

	public Teacher() {
	}

	Teacher(int id,
			String firstName,
			String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	int getId() {
		return id;
	}

	String getFirstName() {
		return firstName;
	}

	String getLastName() {
		return lastName;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return firstName + " " + lastName;
	}
}
