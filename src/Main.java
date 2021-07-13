import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        API server = null;
        try {
            server = Server.INSTANCE;
        }catch (Exception e) {
            System.out.println("Error with book database");
        }

        User currentUser;

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
            } catch(UserIsNotActiveException e) {
                System.out.println("Your account has been canceled due to illegal activity.");
            } catch (EmptyUsernameException e) {
                System.out.println("Empty username, please try again");
            } catch (EmptyPasswordException e) {
                System.out.println("Empty password, please try again");
            }
        }

        //Menu
        while(true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Request new book");
            System.out.println("2. Read book");
            System.out.println("0. Exit");
            String choice = scanner.next();

            switch (choice) {
                //Request new book
                case "1": {
                    ArrayList<Ebook> ebooks = BookDatabase.INSTANCE.getBookList();

                    while(true) {
                        ArrayList<Loan> loans = currentUser.getLoanList();

                        System.out.println("\nPlease enter the number of the book you want to request");
                        int bookId = 1;
                        for (Ebook ebook : ebooks) {
                            boolean continueToNextIt = false;
                            for (Loan loan : loans) {
                                //If the book has already been loaned in the past and is available to be renewed
                                if (loan.getBookHash() == ebook.getHash() && new Date().compareTo(loan.getDateToReturn()) > 0) {
                                    System.out.println(bookId + " : " + ebook.getTitle() + " <Available to renew loan>");
                                    bookId++;
                                    continueToNextIt = true;
                                    //If the book is already in loaned
                                } else if (loan.getBookHash() == ebook.getHash()) {
                                    System.out.println(bookId + " : " + ebook.getTitle() + " <Already in your list of available books>");
                                    bookId++;
                                    continueToNextIt = true;
                                }
                            }

                            if(continueToNextIt)
                                continue;

                            System.out.println(bookId + " : " + ebook.getTitle());
                            bookId++;
                        }
                        System.out.println("0. Go back to main menu");

                        String bookChosen = scanner.next();

                        if(bookChosen.equals("0"))
                            break;
                        else if(Integer.parseInt(bookChosen) < 0 || Integer.parseInt(bookChosen) > bookId) {
                            System.out.println("Please enter a valid option");
                            continue;
                        }

                        try {
                            server.requestBook(BookDatabase.INSTANCE.getBookList().get(Integer.parseInt(bookChosen) - 1).getHash(),
                                    currentUser.getId());
                            System.out.println("The book has been haded to your list.");
                            break;
                        } catch (BookAlreadyLoanedException e) {
                            System.out.println("Book already in your list");
                        } catch (BookDoesntExistException e) {
                            System.out.println("Book doesn't exist");
                        } catch (UserDoesntExistException ignored) {}
                    }

                    break;
                }

                //Read book
                case "2": {
                    if (currentUser.getLoanList().isEmpty()) {
                        System.out.println("Your book list is empty, request a new book");
                        break;
                    }

                    System.out.println("\nPlease enter the number of the book you want to read");

                    //Get all books loaned by user
                    Date currentDate = new Date();
                    ArrayList<Ebook> availableBooks = new ArrayList<>();
                    ArrayList<Ebook> nonAvailableBooks = new ArrayList<>();
                    ArrayList<Loan> loans = currentUser.getLoanList();
                    HashMap<Integer, Date> tempHash = new HashMap<>();
                    for (Loan loan : loans) {
                        if(currentDate.compareTo(loan.getDateToReturn()) <= 0)
                            try {
                                availableBooks.add(BookDatabase.INSTANCE.getBookByHash(loan.getBookHash()));
                                tempHash.put(loan.getBookHash(), loan.getDateToReturn());
                            } catch(BookDoesntExistException ignored) {}

                        else
                            try {
                                nonAvailableBooks.add(BookDatabase.INSTANCE.getBookByHash(loan.getBookHash()));
                            } catch(BookDoesntExistException ignored) {}
                    }

                    while(true) {
                        //Print all available books if there are any
                        int bookId = 1;
                        if(!availableBooks.isEmpty()) {
                            System.out.println("Books available to read:");
                            for (Ebook ebook : availableBooks) {
                                System.out.println("    " + bookId + " : " + ebook.getTitle() + " <Available until " + tempHash.get(ebook.getHash())
                                        + ">");
                                bookId++;
                            }
                            System.out.println();
                        }

                        //Print all unavailable books if there are any
                        if(!nonAvailableBooks.isEmpty()) {
                            System.out.println("Books unavailable to read:");
                            for (Ebook ebook : nonAvailableBooks) {
                                System.out.println("     " + ebook.getTitle());
                            }
                            System.out.println();
                        }

                        if(availableBooks.isEmpty())
                            break;

                        System.out.println("0. Go back to main menu");

                        String bookChosen = scanner.next();
                        if(bookChosen.equals("0"))
                            break;
                        else if((Integer.parseInt(bookChosen) < 0 || Integer.parseInt(bookChosen) < bookId)) {
                            for (Loan loan : loans) {
                                if(loan.getBookHash() == availableBooks.get(Integer.parseInt(bookChosen) - 1).getHash()) {
                                    try {
                                        server.readBook(currentUser.getId(), loan);
                                    } catch (UserDoesntExistException ignored) {}
                                    break;
                                }
                            }
                        }else
                            System.out.println("Please enter a valid option");

                    }

                    break;
                }

                case "0":
                    System.exit(0);

                default:
                    System.out.println("\n<Please choose a valid option>");
            }
        }

    }
}