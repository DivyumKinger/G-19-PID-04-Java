# Library Management System

A simple console-based Library Management System in Java using custom linked lists for storing users and books. The system supports admin and user roles, book issuing/returning, and persistent storage via text files.

---

## Features

- **Admin Menu**

  - View all books
  - Add a new book
  - Remove a book
  - Update book details
  - Add a new user (admin or user role)
  - View all users
  - Update user details
  - Remove a user
  - Logout / Exit

- **User Menu**

  - View all books
  - Issue a book
  - Return a book
  - Logout / Exit

- **Authentication**

  - Separate login for admin and user roles
  - Password and role validation

- **Persistent Storage**
  - Books and users are saved in `books.txt` and `users.txt`
  - Data is loaded at startup and saved after every change

---

## File Structure

```
LibManagement/
├── Book.java
├── BookLinkedList.java
├── BookNode.java
├── Identifiable.java
├── Library.java
├── Main.java
├── User.java
├── UserLinkedList.java
├── UserNode.java
├── books.txt
├── users.txt
```

---

## How to Run

1. **Compile the project:**

   ```sh
   javac Main.java
   ```

2. **Run the program:**

   ```sh
   java Main
   ```

3. **Follow the on-screen menu instructions.**

---

## Data Files

- **books.txt**  
  Stores all books in the format:

  ```
  id,title,author,isIssued[,issuedToUserId]
  ```

  Example:

  ```
  1,Dune,Frank Herbert,false
  2,Harry Potter,JK Rowling,false
  ```

- **users.txt**  
  Stores all users in the format:
  ```
  id,username,password,role
  ```
  Example:
  ```
  1,admin,admin123,admin
  2,user1,pass1,user
  ```

---

## Notes

- The system uses custom singly linked lists (`BookLinkedList`, `UserLinkedList`) for storage in memory.
- All menu actions provide clear feedback and require pressing Enter to continue.
- Only admins can add, remove, or update books and users.
- Users can only issue or return books to themselves.
- The console is cleared between menus for better readability (may not work in all terminals).

---

## Author

- Developed by [Your Name or Team]
- For educational/demo purposes

---

## License

This project is open source and free to use.
# G-19-PID-04-Java
