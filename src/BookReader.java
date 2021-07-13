import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class BookReader {
    public BookReader(Ebook ebook) throws BookDoesntExistException, IOException {
        if(ebook == null)
            throw new BookDoesntExistException();

        Scanner scanner = new Scanner(System.in);
        int currentPage = 1;
        System.out.println("You are now reading '" + ebook.getTitle() + "'.");
        while(true) {
            System.out.println("You are reading page number " + currentPage + " of " + ebook.getPagesNumber());
            System.out.println("1. Next page");
            System.out.println("2. Previous page");
            System.out.println("3. Go to certain page");
            System.out.println("0. Exit reader");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    if(currentPage + 1 <= ebook.getPagesNumber())
                        currentPage++;
                    else
                        System.out.println("You are already on the last page of the book");

                    break;

                case "2":
                    if(currentPage - 1 >= 1)
                        currentPage--;
                    else
                        System.out.println("You are already on the first page of the book");

                    break;

                case "3":
                    System.out.println("Please enter the page number to go to");
                    String goToPageS = scanner.nextLine();
                    int goToPage = Integer.parseInt(goToPageS);
                    if(goToPage != currentPage)
                        if(goToPage > 0 && goToPage <= ebook.getPagesNumber())
                            currentPage = goToPage;
                        else
                            System.out.println("Please enter a page number between 1 and " + ebook.getPagesNumber());
                    else
                        System.out.println("You are already on the selected page");

                    break;

                case "0":
                    return;

                default:
                    System.out.println("Please enter a valid option");
            }
        }
    }
}
