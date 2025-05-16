import java.io.*;

public class Library {
	private final BookLinkedList books = new BookLinkedList();
	private final UserLinkedList users = new UserLinkedList();
	private int nextBookId = 1;
	private int nextUserId = 1;
	private static final String BOOKS_FILE = "books.txt";
	private static final String USERS_FILE = "users.txt";

	public Library() {
		loadBooks();
		loadUsers();
	}

	// Book CRUD
	public void addBook(String title, String author) {
		// Find the smallest unused positive integer for book ID
		int id = 1;
		while (findBookById(id) != null) {
			id++;
		}
		books.add(new Book(id, title, author));
		saveBooks();
	}

	public void showBooks() {
		// Count books
		int count = 0;
		BookNode curr = books.getHead();
		while (curr != null) {
			count++;
			curr = curr.next;
		}
		// Selection sort using only BookNode references
		boolean[] printed = new boolean[count];
		for (int i = 0; i < count; i++) {
			BookNode minNode = null;
			int minId = Integer.MAX_VALUE;
			int minIdx = -1;
			BookNode node = books.getHead();
			for (int j = 0; j < count; j++) {
				if (!printed[j] && node.data.getId() < minId) {
					minId = node.data.getId();
					minNode = node;
					minIdx = j;
				}
				node = node.next;
			}
			if (minNode != null) {
				Book b = minNode.data;
				if (b.isIssued()) {
					User u = users.findById(b.getIssuedToUserId());
					String userName = (u != null) ? u.getName() : ("User ID " + b.getIssuedToUserId());
					System.out.println(
							b.getId() + ": " + b.getTitle() + " by " + b.getAuthor() + " [Issued to " + userName + "]");
				} else {
					System.out.println(b.getId() + ": " + b.getTitle() + " by " + b.getAuthor() + " [Available]");
				}
				printed[minIdx] = true;
			}
		}
	}

	public Book findBookById(int id) {
		return books.findById(id);
	}

	public boolean removeBook(int id) {
		boolean removed = books.removeById(id);
		if (removed)
			saveBooks();
		return removed;
	}

	// Update book by ID
	public boolean updateBook(int id, String newTitle, String newAuthor) {
		Book book = findBookById(id);
		if (book == null)
			return false;
		if (newTitle != null && !newTitle.isEmpty()) {
			// Reflection or direct field access needed since fields are final; recreate
			// book
			boolean issued = book.isIssued();
			Integer issuedTo = book.getIssuedToUserId();
			Book updated = new Book(id, newTitle, newAuthor);
			if (issued && issuedTo != null)
				updated.issueTo(issuedTo);
			// Remove old and add updated
			books.removeById(id);
			books.add(updated);
		} else if (newAuthor != null && !newAuthor.isEmpty()) {
			// Only author changed
			boolean issued = book.isIssued();
			Integer issuedTo = book.getIssuedToUserId();
			Book updated = new Book(id, book.getTitle(), newAuthor);
			if (issued && issuedTo != null)
				updated.issueTo(issuedTo);
			books.removeById(id);
			books.add(updated);
		} else {
			return false;
		}
		saveBooks();
		return true;
	}

	// User CRUD
	public void addUser(String name, String password, String role) {
		// Find the smallest unused positive integer for user ID
		int id = 1;
		while (findUserById(id) != null) {
			id++;
		}
		users.add(new User(id, name, password, role));
		saveUsers();
	}

	public void showUsers() {
		UserNode curr = users.getHead();
		while (curr != null) {
			User u = curr.data;
			System.out.println(u.getId() + ": " + u.getName() + " (" + u.getRole() + ")");
			curr = curr.next;
		}
	}

	public User findUserById(int id) {
		return users.findById(id);
	}

	public boolean removeUser(int id) {
		boolean removed = users.removeById(id);
		if (removed)
			saveUsers();
		return removed;
	}

	// Add this method for username lookup
	public User findUserByName(String name) {
		UserNode curr = users.getHead();
		while (curr != null) {
			User u = curr.data;
			if (u.getName().equalsIgnoreCase(name))
				return u;
			curr = (UserNode) curr.next;
		}
		return null;
	}

	// Update user by username
	public boolean updateUser(String username, String newPassword, String newRole) {
		UserNode curr = users.getHead();
		while (curr != null) {
			if (curr.data.getName().equalsIgnoreCase(username)) {
				if (newPassword != null && !newPassword.isEmpty()) {
					curr.data = new User(curr.data.getId(), curr.data.getName(), newPassword, curr.data.getRole());
				}
				if (newRole != null && !newRole.isEmpty()) {
					curr.data = new User(curr.data.getId(), curr.data.getName(), curr.data.getPassword(), newRole);
				}
				saveUsers();
				return true;
			}
			curr = curr.next;
		}
		return false;
	}

	// Issue and Return
	public boolean issueBook(int bookId, int userId) {
		Book book = findBookById(bookId);
		User user = findUserById(userId);
		if (book != null && user != null && !book.isIssued()) {
			book.issueTo(userId);
			saveBooks();
			return true;
		}
		return false;
	}

	public boolean returnBook(int bookId, int userId) {
		Book book = findBookById(bookId);
		if (book != null && book.isIssued() && book.getIssuedToUserId() == userId) {
			book.returnBook();
			saveBooks();
			return true;
		}
		return false;
	}

	// File handling for books
	private void loadBooks() {
		File file = new File(BOOKS_FILE);
		if (!file.exists())
			return;
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(",");
				if (parts.length >= 4) {
					int id = Integer.parseInt(parts[0]);
					String title = parts[1];
					String author = parts[2];
					boolean issued = Boolean.parseBoolean(parts[3]);
					Integer issuedTo = null;
					if (issued && parts.length == 5)
						issuedTo = Integer.parseInt(parts[4]);
					Book b = new Book(id, title, author);
					if (issued)
						b.issueTo(issuedTo);
					books.add(b);
					if (id >= nextBookId)
						nextBookId = id + 1;
				}
			}
		} catch (IOException e) { // EXCEPTION HANDLING
			System.out.println("Error loading books from file.");
		}
	}

	private void saveBooks() {
		try (PrintWriter pw = new PrintWriter(new FileWriter(BOOKS_FILE))) {
			BookNode curr = books.getHead();
			while (curr != null) {
				Book b = curr.data;
				StringBuilder sb = new StringBuilder();
				sb.append(b.getId()).append(",").append(b.getTitle()).append(",").append(b.getAuthor()).append(",")
						.append(b.isIssued());
				if (b.isIssued() && b.getIssuedToUserId() != null) {
					sb.append(",").append(b.getIssuedToUserId());
				}
				pw.println(sb.toString());
				curr = curr.next;
			}
		} catch (IOException e) { // EXCEPTION HANDLING
			System.out.println("Error saving books to file.");
		}
	}

	// File handling for users
	private void loadUsers() {
		File file = new File(USERS_FILE);
		if (!file.exists())
			return;
		// Clear the users list instead of reassigning
		UserNode curr = users.getHead();
		while (curr != null) {
			users.removeById(curr.data.getId());
			curr = (UserNode) curr.next;
		}
		nextUserId = 1;
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(",");
				if (parts.length == 4) {
					int id = Integer.parseInt(parts[0]);
					String name = parts[1];
					String password = parts[2];
					String role = parts[3];
					users.add(new User(id, name, password, role));
					if (id >= nextUserId)
						nextUserId = id + 1;
				}
			}
		} catch (IOException e) { // EXCEPTION HANDLING
			System.out.println("Error loading users from file.");
		}
	}

	private void saveUsers() {
		try (PrintWriter pw = new PrintWriter(new FileWriter(USERS_FILE))) {
			UserNode curr = users.getHead();
			while (curr != null) {
				User u = curr.data;
				pw.println(u.getId() + "," + u.getName() + "," + u.getPassword() + "," + u.getRole());
				curr = curr.next;
			}
		} catch (IOException e) { // EXCEPTION HANDLING
			System.out.println("Error saving users to file.");
		}
	}
}
