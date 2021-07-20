import java.util.ArrayList;
import java.util.HashMap;

public enum BookDatabase {
    INSTANCE();

    private ArrayList<Ebook> bookList;
    private HashMap<String, String> publisherList;

    BookDatabase() {
        bookList = new ArrayList<>();
        publisherList = new HashMap<>();
        try {
            bookList.add(new Ebook("The Art Of War", "Filiquarian", 110,
                    "1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364", 1000, ".pdf"));
            bookList.add(new Ebook("Quantum Physics for Hippies", "Independent", 175,
                    "2A72ED4386065E4ABE91AF5682A7D310BF280798B06DE1DC18D457AC80A931C4", 2000, ".epub"));
            bookList.add(new Ebook("Java For Dummies", "For Dummies", 504,
                    "16BC9EF7327955D701A1EBF7E8A1CA413E92A22A0C6F4F865CFF9D46380B5F7C", 3000, ".pdf"));
            bookList.add(new Ebook("Design Patterns: Elements of Reusable Object-Oriented Software",
                    "Addison-Wesley Professional", 416,
                    "EB1BBEAEDCB50107A55AC049B62384B6A0BCFBF5E2988BEF9536FC5C27404D5A", 6000, ".epub"));
            bookList.add(new Ebook("The Pragmatic Programmer", "Addison-Wesley Professional",
                    352,
                    "14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38", 4000, ".pdf"));
            bookList.add(new Ebook("Blockchain For Dummies", "For Dummies", 256,
                    "54C2495510FAE68DE0196C365C1D3626041C5BDDBA70CEF16885F5EC2FC66883", 5000, ".epub"));

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

    public Ebook getBookByHash(String hash) throws BookDoesntExistException {
        for (Ebook ebook : bookList) {
            if(ebook.getHash().equals(hash)) {
                return ebook;
            }
        }

        throw new BookDoesntExistException();
    }

    public void reset() {
        bookList = new ArrayList<>();
        publisherList = new HashMap<>();
        try {
            bookList.add(new Ebook("The Art Of War", "Filiquarian", 110,
                    "1661041B159552D2C5CEF61974D1A652513D99700F52C9C22CA446D084587364", 1000, ".pdf"));
            bookList.add(new Ebook("Quantum Physics for Hippies", "Independent", 175,
                    "2A72ED4386065E4ABE91AF5682A7D310BF280798B06DE1DC18D457AC80A931C4", 2000, ".epub"));
            bookList.add(new Ebook("Java For Dummies", "For Dummies", 504,
                    "16BC9EF7327955D701A1EBF7E8A1CA413E92A22A0C6F4F865CFF9D46380B5F7C", 3000, ".pdf"));
            bookList.add(new Ebook("Design Patterns: Elements of Reusable Object-Oriented Software",
                    "Addison-Wesley Professional", 416,
                    "EB1BBEAEDCB50107A55AC049B62384B6A0BCFBF5E2988BEF9536FC5C27404D5A", 6000, ".epub"));
            bookList.add(new Ebook("The Pragmatic Programmer", "Addison-Wesley Professional",
                    352,
                    "14D740E5C2F9D24616CDE373A5C80245778E53B9D0E9ACA05A9F3C7C328D3D38", 4000, ".pdf"));
            bookList.add(new Ebook("Blockchain For Dummies", "For Dummies", 256,
                    "54C2495510FAE68DE0196C365C1D3626041C5BDDBA70CEF16885F5EC2FC66883", 5000, ".epub"));

            publisherList.put("Filiquarian", "This is 'Filiquarian' terms of responsibility.");
            publisherList.put("Independent", "This is 'Independent' terms of responsibility.");
            publisherList.put("For Dummies", "This is 'For Dummies' terms of responsibility.");
            publisherList.put("Addison-Wesley Professional", "This is 'Addison-Wesley' Professional terms of responsibility.");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
