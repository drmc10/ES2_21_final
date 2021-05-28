import java.util.Date;

public interface Ebook {
    int getSize();
    String getHash();
    String getIsbn();
    String getTitle();
    String getAuthor();
    String getLanguage();
    String getPublisher();
    Date getReleaseDate();
}
