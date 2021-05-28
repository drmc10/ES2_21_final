import java.util.Date;

public class Epub implements Ebook {
    private int size;
    private String hash;
    private String isbn;
    private String title;
    private String author;
    private String language;
    private String publisher;
    private Date releaseDate;

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public String getHash() {
        return hash;
    }

    @Override
    public String getIsbn() {
        return isbn;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public String getLanguage() {
        return language;
    }

    @Override
    public String getPublisher() {
        return publisher;
    }

    @Override
    public Date getReleaseDate() {
        return releaseDate;
    }
}
