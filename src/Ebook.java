public class Ebook {
    private final int size;
    private final String hash;
    private final int pages;
    private final String title;
    private final String publisher;
    private final String extension;

    public Ebook(String title, String publisher, int pages, String hash, int size, String extension)
            throws MissingTitleException, MissingPublisherException, IncorrectPageNumber,
            InvalidHashException, InvalidFileSizeException, InvalidFileExtension, NullParameterException,
            TooFewCharsException, TooManyCharsException {
        if(title == null || publisher == null || hash == null || extension == null) {
            throw new NullParameterException();
        }
        if(title.isEmpty())
            throw new MissingTitleException();
        if(publisher.isEmpty())
            throw new MissingPublisherException();
        if(pages <= 0 || pages > 2000)
            throw new IncorrectPageNumber();
        if(hash.length() != 64)
            throw new InvalidHashException();
        if(size <= 0 || size > 100000)
            throw new InvalidFileSizeException();
        if(title.length() < 5 || publisher.length() < 5)
            throw new TooFewCharsException();
        if(title.length() > 50 || publisher.length() > 50)
            throw new TooManyCharsException();
        if(extension.length() < 2)
            throw new TooFewCharsException();
        if(extension.length() > 5)
            throw new TooManyCharsException();

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
