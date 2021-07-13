import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public enum BookDatabase {
    INSTANCE();

    private ArrayList<Ebook> bookList = new ArrayList<>();
    private HashMap<String, String> publisherList = new HashMap<>();

    BookDatabase() {
        try {
            bookList.add(new Epub("The Art Of War", "Filiquarian", 110));
            bookList.add(new Pdf("Quantum Physics for Hippies", "Independent", 175));
            bookList.add(new Epub("Java For Dummies", "For Dummies", 504));
            bookList.add(new Pdf("Design Patterns: Elements of Reusable Object-Oriented Software", "Addison-Wesley Professional", 416));
            bookList.add(new Epub("The Pragmatic Programmer", "Addison-Wesley Professional", 352));
            bookList.add(new Pdf("Blockchain For Dummies", "For Dummies", 256));

            publisherList.put("Filiquarian", "This is 'Filiquarian' terms of responsibility.");
            publisherList.put("Independent", "This is 'Independent' terms of responsibility.");
            publisherList.put("For Dummies", "This is 'For Dummies' terms of responsibility.");
            publisherList.put("Addison-Wesley Professional", "This is 'Addison-Wesley' Professional terms of responsibility.");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Ebook> getBookList() {
        return bookList;
    }

    public HashMap<String, String> getPublisherList() {
        return publisherList;
    }

    public Ebook getBookByHash(int hash) throws BookDoesntExistException {
        for (Ebook ebook : bookList) {
            if(ebook.getHash() == hash) {
                return ebook;
            }
        }

        throw new BookDoesntExistException();
    }

    public void reset() {
        bookList = new ArrayList<>();
        publisherList = new HashMap<>();
        try {
            bookList.add(new Epub("The Art Of War", "Filiquarian", 110));
            bookList.add(new Pdf("Quantum Physics for Hippies", "Independent", 175));
            bookList.add(new Epub("Java For Dummies", "For Dummies", 504));
            bookList.add(new Pdf("Design Patterns: Elements of Reusable Object-Oriented Software", "Addison-Wesley Professional", 416));
            bookList.add(new Epub("The Pragmatic Programmer", "Addison-Wesley Professional", 352));
            bookList.add(new Pdf("Blockchain For Dummies", "For Dummies", 256));

            publisherList.put("Filiquarian", "This is 'Filiquarian' terms of responsibility.");
            publisherList.put("Independent", "This is 'Independent' terms of responsibility.");
            publisherList.put("For Dummies", "This is 'For Dummies' terms of responsibility.");
            publisherList.put("Addison-Wesley Professional", "This is 'Addison-Wesley' Professional terms of responsibility.");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
