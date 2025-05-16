public class User implements Identifiable {
	private int id;
	private String name;
	private String password;
	private String role;

	// public User(int id, String name) {
	// 	this.id = id;
	// 	this.name = name;
	// }

	public User(int id, String name, String password, String role) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getRole() {
		return role;
	}

	@Override
	public String toString() {
		return id + ": " + name + " (" + role + ")";
	}
}