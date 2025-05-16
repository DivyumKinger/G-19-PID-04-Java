import java.util.*;
// import java.io.*;

public class Main {
    private static final String RESET = "\u001B[0m";
    private static final String YELLOW = "\u001B[33m";
    private static final String CYAN = "\u001B[36m";
    private static final String GREEN = "\u001B[32m";

    // Utility to clear the console (works in most terminals)
    private static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args) {
        Library library = new Library();
        Scanner sc = new Scanner(System.in);
        while (true) {
            clearConsole(); // Clear before welcome menu
            // Welcome menu
            System.out.println(YELLOW + "+--------------------------------------------+" + RESET);
            System.out.println(YELLOW + "|         Welcome to the Library System      |" + RESET);
            System.out.println(YELLOW + "+--------------------------------------------+" + RESET);
            System.out.println(CYAN + "| [1]. Admin                                 |" + RESET);
            System.out.println(CYAN + "| [2]. User                                  |" + RESET);
            System.out.println(CYAN + "| [0]. Exit                                  |" + RESET);
            System.out.println(YELLOW + "+--------------------------------------------+" + RESET);
            System.out.print(GREEN + "Choose: " + RESET);
            int welcomeChoice = -1;
            try { // EXCEPTION HANDLING
                welcomeChoice = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) { // EXCEPTION HANDLING (runtime)
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
                continue;
            }
            if (welcomeChoice == 0) {
                System.out.println("Goodbye!");
                break;
            }
            boolean isAuthenticated = false;
            String currentUser = null;
            String currentRole = null;
            // Login prompt for selected role
            if (welcomeChoice == 1) { // Admin
                clearConsole(); // Clear before admin login menu
                System.out.println(YELLOW + "\n+-------------------+" + RESET);
                System.out.println(YELLOW + "|   Admin Login     |" + RESET);
                System.out.println(YELLOW + "+-------------------+" + RESET);
                System.out.print(GREEN + "Enter username: " + RESET);
                String loginUser = sc.nextLine();
                System.out.print(GREEN + "Enter password: " + RESET);
                String loginPass = sc.nextLine();
                User user = library.findUserByName(loginUser);
                if (user == null) {
                    System.out.println("User does not exist.");
                    continue;
                }
                if (user.getPassword().equals(loginPass) && "admin".equals(user.getRole())) {
                    isAuthenticated = true;
                    currentUser = loginUser;
                    currentRole = "admin";
                } else {
                    System.out.println("Invalid admin credentials.");
                    continue;
                }
            } else if (welcomeChoice == 2) { // User
                clearConsole(); // Clear before user login menu
                System.out.println(YELLOW + "\n+-------------------+" + RESET);
                System.out.println(YELLOW + "|    User Login     |" + RESET);
                System.out.println(YELLOW + "+-------------------+" + RESET);
                System.out.print(GREEN + "Enter username: " + RESET);
                String loginUser = sc.nextLine();
                System.out.print(GREEN + "Enter password: " + RESET);
                String loginPass = sc.nextLine();
                User user = library.findUserByName(loginUser);
                if (user == null) {
                    System.out.println("User does not exist.");
                    continue;
                }
                if (user.getPassword().equals(loginPass) && "user".equals(user.getRole())) {
                    isAuthenticated = true;
                    currentUser = loginUser;
                    currentRole = "user";
                } else {
                    System.out.println("Invalid user credentials.");
                    continue;
                }
            } else {
                System.out.println("Invalid option. Please enter 1, 2, or 0.");
                continue;
            }
            // Main menu for role
            while (isAuthenticated) {
                if ("admin".equals(currentRole)) {
                    clearConsole(); // Clear before admin menu
                    System.out.println(YELLOW + "\n+--------------------------------------------+" + RESET);
                    System.out.println(YELLOW + "|               Admin Menu                   |" + RESET);
                    System.out.println(YELLOW + "+--------------------------------------------+" + RESET);
                    System.out.println(CYAN + "| [1]. View Books                            |" + RESET);
                    System.out.println(CYAN + "| [2]. Add Book                              |" + RESET);
                    System.out.println(CYAN + "| [3]. Remove Book                           |" + RESET);
                    System.out.println(CYAN + "| [4]. Update Book                           |" + RESET);
                    System.out.println(CYAN + "| [5]. Add User                              |" + RESET);
                    System.out.println(CYAN + "| [6]. View Users                            |" + RESET);
                    System.out.println(CYAN + "| [7]. Update User                           |" + RESET);
                    System.out.println(CYAN + "| [8]. Remove User                           |" + RESET);
                    System.out.println(CYAN + "| [9]. Logout                                |" + RESET);
                    System.out.println(CYAN + "| [0]. Exit                                  |" + RESET);
                    System.out.println(YELLOW + "+--------------------------------------------+" + RESET);
                    System.out.print(GREEN + "Choose: " + RESET);
                    int choice = -1;
                    try { // EXCEPTION HANDLING
                        choice = sc.nextInt();
                        sc.nextLine();
                    } catch (InputMismatchException e) { // EXCEPTION HANDLING //Runtime
                        System.out.println("Invalid input. Please enter a number.");
                        sc.nextLine();
                        continue;
                    }
                    switch (choice) {
                        case 1:
                            System.out.println("Books:\n");
                            library.showBooks();
                            System.out.println();
                            System.out.print("Press Enter to return to menu...");
                            sc.nextLine();
                            break;
                        case 2:
                            System.out.println("Books:\n");
                            library.showBooks();
                            System.out.println();
                            System.out.print("Book Title: ");
                            String title = sc.nextLine();
                            System.out.print("Author: ");
                            String author = sc.nextLine();
                            library.addBook(title, author);
                            System.out.println("Book added.");
                            System.out.print("Press Enter to return to menu...");
                            sc.nextLine();
                            break;
                        case 3:
                            System.out.println("Books:\n");
                            library.showBooks();
                            System.out.println();
                            System.out.print("Book ID to remove: ");
                            int bookId = sc.nextInt();
                            sc.nextLine(); // consume newline
                            if (library.removeBook(bookId)) {
                                System.out.println("Book removed succesfully.");
                            } else {
                                System.out.println("Book not found.");
                            }
                            System.out.print("Press Enter to return to menu...");
                            sc.nextLine();
                            break;
                        case 4:
                            System.out.println("Books:\n");
                            library.showBooks();
                            System.out.println();
                            System.out.print("Book ID to update: ");
                            int updateBookId = sc.nextInt();
                            sc.nextLine();
                            Book bookToUpdate = library.findBookById(updateBookId);
                            if (bookToUpdate == null) {
                                System.out.println("Book not found.");
                                System.out.print("Press Enter to return to menu...");
                                sc.nextLine();
                                break;
                            }
                            System.out.print("New title (leave blank to keep '" + bookToUpdate.getTitle() + "'): ");
                            String newTitle = sc.nextLine();
                            if (newTitle.isEmpty())
                                newTitle = bookToUpdate.getTitle();
                            System.out.print("New author (leave blank to keep '" + bookToUpdate.getAuthor() + "'): ");
                            String newAuthor = sc.nextLine();
                            if (newAuthor.isEmpty())
                                newAuthor = bookToUpdate.getAuthor();
                            if (library.updateBook(updateBookId, newTitle, newAuthor)) {
                                System.out.println("Book updated.");
                            } else {
                                System.out.println("Update failed.");
                            }
                            System.out.print("Press Enter to return to menu...");
                            sc.nextLine();
                            break;
                        case 5:
                            System.out.print("New username: ");
                            String newUser = sc.nextLine();
                            if (library.findUserByName(newUser) != null) {
                                System.out.println("Username already exists.");
                                System.out.print("Press Enter to return to menu...");
                                sc.nextLine();
                                break;
                            }
                            System.out.print("New password: ");
                            String newPass = sc.nextLine();
                            System.out.print("Role (admin/user): ");
                            String newRole = sc.nextLine().trim().toLowerCase();
                            if (!newRole.equals("admin") && !newRole.equals("user")) {
                                System.out.println("Invalid role. Please enter 'admin' or 'user'.");
                                System.out.print("Press Enter to return to menu...");
                                sc.nextLine();
                                break;
                            }
                            library.addUser(newUser, newPass, newRole);
                            System.out.println("User added.");
                            System.out.print("Press Enter to return to menu...");
                            sc.nextLine();
                            break;
                        case 6:
                            System.out.println("Users:");
                            library.showUsers();
                            System.out.print("Press Enter to return to menu...");
                            sc.nextLine();
                            break;
                        case 7:
                            System.out.println("Users:");
                            library.showUsers();
                            System.out.print("Username to update: ");
                            String updateUser = sc.nextLine();
                            User userToUpdate = library.findUserByName(updateUser);
                            if (userToUpdate == null) {
                                System.out.println("User not found.");
                                System.out.print("Press Enter to return to menu...");
                                sc.nextLine();
                                break;
                            }
                            System.out.print("New password (leave blank to keep current): ");
                            String newPassword = sc.nextLine();
                            if (newPassword.isEmpty())
                                newPassword = userToUpdate.getPassword();
                            System.out.print("New role (leave blank to keep '" + userToUpdate.getRole() + "'): ");
                            String newRoleUpdate = sc.nextLine();
                            if (newRoleUpdate.isEmpty())
                                newRoleUpdate = userToUpdate.getRole();
                            if (library.updateUser(updateUser, newPassword, newRoleUpdate)) {
                                System.out.println("User updated.");
                            } else {
                                System.out.println("Update failed.");
                            }
                            System.out.print("Press Enter to return to menu...");
                            sc.nextLine();
                            break;
                        case 8:
                            System.out.print("Username to remove: ");
                            String delUser = sc.nextLine();
                            User userToRemove = library.findUserByName(delUser);
                            if (userToRemove != null) {
                                library.removeUser(userToRemove.getId());
                                System.out.println("User removed.");
                            } else {
                                System.out.println("User not found.");
                            }
                            System.out.print("Press Enter to return to menu...");
                            sc.nextLine();
                            break;
                        case 9:
                            isAuthenticated = false; // Logout
                            break;
                        case 0:
                            System.out.println("Goodbye!");
                            System.exit(0);
                        default:
                            System.out.println("Invalid choice. Please choose between 0 and 9.");
                    }
                } else if ("user".equals(currentRole)) {
                    clearConsole(); // Clear before user menu
                    System.out.println(YELLOW + "\n+--------------------------------------------+" + RESET);
                    System.out.println(YELLOW + "|                User Menu                   |" + RESET);
                    System.out.println(YELLOW + "+--------------------------------------------+" + RESET);
                    System.out.println(CYAN + "| [1]. View Books                            |" + RESET);
                    System.out.println(CYAN + "| [2]. Issue Book                            |" + RESET);
                    System.out.println(CYAN + "| [3]. Return Book                           |" + RESET);
                    System.out.println(CYAN + "| [4]. Logout                                |" + RESET);
                    System.out.println(CYAN + "| [0]. Exit                                  |" + RESET);
                    System.out.println(YELLOW + "+--------------------------------------------+" + RESET);
                    System.out.print(GREEN + "Choose: " + RESET);
                    int choice = -1;
                    try { // EXCEPTION HANDLING
                        choice = sc.nextInt();
                        sc.nextLine();
                    } catch (InputMismatchException e) { // EXCEPTION HANDLING also at Runtime
                        System.out.println("Invalid input. Please enter a number.");
                        sc.nextLine();
                        continue;
                    }
                    switch (choice) {
                        case 1:
                            System.out.println("Books:\n");
                            library.showBooks();
                            System.out.println();
                            System.out.print("Press Enter to return to menu...");
                            sc.nextLine();
                            break;
                        case 2:
                            System.out.println("Books:\n");
                            library.showBooks();
                            System.out.println();
                            System.out.print("Book ID to issue: ");
                            int issueBookId = sc.nextInt();
                            sc.nextLine(); // consume newline
                            Book bookToIssue = library.findBookById(issueBookId);
                            if (bookToIssue == null) {
                                System.out.println("Invalid Book ID. Book does not exist.");
                                System.out.print("Press Enter to return to menu...");
                                sc.nextLine();
                                break;
                            }
                            User current = library.findUserByName(currentUser);
                            if (current == null) {
                                System.out.println("User record not found. Contact admin.");
                                System.out.print("Press Enter to return to menu...");
                                sc.nextLine();
                                break;
                            }
                            if (library.issueBook(issueBookId, current.getId())) {
                                System.out.println("Book issued successfully.");
                            } else {
                                System.out.println(
                                        "Error: Book could not be issued. It may already be issued, does not exist, or you do not have permission.");
                            }
                            System.out.print("Press Enter to return to menu...");
                            sc.nextLine();
                            break;
                        case 3:
                            System.out.println("Books:\n");
                            library.showBooks();
                            System.out.println();
                            System.out.print("Book ID to return: ");
                            int returnBookId = sc.nextInt();
                            sc.nextLine(); // consume newline
                            User currentU = library.findUserByName(currentUser);
                            if (currentU == null) {
                                System.out.println("User record not found. Contact admin.");
                                System.out.print("Press Enter to return to menu...");
                                sc.nextLine();
                                break;
                            }
                            if (library.returnBook(returnBookId, currentU.getId())) {
                                System.out.println("Returned book successfully.");
                            } else {
                                System.out.println(
                                        "Return failed: Either the book is not issued to you, does not exist, or is not currently issued.");
                            }
                            System.out.print("Press Enter to return to menu...");
                            sc.nextLine();
                            break;
                        case 4:
                            isAuthenticated = false; // Logout
                            break;
                        case 0:
                            System.out.println("Goodbye!");
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Invalid choice. Please choose between 0 and 4.");
                            System.out.print("Press Enter to return to menu...");
                            sc.nextLine();
                            break;
                    }
                }
            }
        }
    }
}