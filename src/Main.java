import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        API server = new Server();
        User currentUser = null;

        //Login
        while(true) {
            try {
                System.out.println("Enter username");
                String username = scanner.next();
                System.out.println("Enter password");
                String password = scanner.next();

                currentUser = server.login(username, password);

                break;
            } catch(UserDoesntExistException e) {
                System.out.println("User doesn't exit. Please retry.");
            } catch(IncorrectPasswordException e) {
                System.out.println("Incorrect password. Please retry.");
            }
        }

        //Menu
        while(true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Request new book");
            System.out.print("2. Read book");
            String choice = scanner.next();

            switch (choice) {
                case "1":
                    ArrayList<Ebook> ebooks = server.getBookList();

                    System.out.println("\nPlease enter the number of the book you want to request");
                    int bookId1 = 1;
                    for (Ebook ebook : ebooks) {
                         System.out.println(bookId1 + " : " + ebook.getTitle());
                         bookId1++;
                    }

                    String bookChosen = scanner.next();
                    try {
                        server.requestBook(server.getBookList().get(Integer.parseInt(bookChosen)).getHash(), currentUser);
                    } catch (BookAlreadyLoanedException e) {
                        System.out.println("Book already in your list");
                    }

                    break;

                case "2":
                    ArrayList<Loan> tempLoan = currentUser.getLoanList();
                    if(tempLoan.isEmpty()) {
                        System.out.println("Your book list is empty, request a new book");
                        break;
                    }

                    System.out.println("\nPlease enter the number of the book you want to read");

                    ArrayList<Loan> loans = currentUser.getLoanList();
                    for (Loan loan : loans) {
                        System.out.println(loan.getBookHash());
                        System.out.println(loan.getIssuedDate());
                        System.out.println(loan.getDateToReturn());
                    }

                    //switch ()

                    break;

                default:
                    System.out.println("Please choose a valid option");
            }
        }

    }
}
