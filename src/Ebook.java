public class Ebook {
    private final int size;
    private final String hash;
    private final int pages;
    private final String title;
    private final String publisher;
    private final String extension;

    public Ebook(String title, String publisher, int pages, String hash, int size, String extension)
            throws MissingTitleException, MissingPublisherException, IncorrectPageNumber,
            InvalidHashException, InvalidFileSizeException, InvalidFileExtension {
        if(title.isEmpty())
            throw new MissingTitleException();
        if(publisher.isEmpty())
            throw new MissingPublisherException();
        if(pages <= 0)
            throw new IncorrectPageNumber();
        if(hash.length() != 64)
            throw new InvalidHashException();
        if(size <= 0 || size > 100000)
            throw new InvalidFileSizeException();

        switch(extension) {
            case ".pdf":
                this.extension = ".pdf";
                break;

            case ".epub":
                this.extension = ".epub";
                break;

            default:
                throw new InvalidFileExtension();
        }

        this.title = title;
        this.publisher = publisher;
        this.pages = pages;
        this.hash = hash;
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public String getHash() {
        return hash;
    }

    public int getPagesNumber() {
        return pages;
    }

    public String getTitle() {
        return title;
    }

    public String getPublisher() {
        return publisher;
    }
}
