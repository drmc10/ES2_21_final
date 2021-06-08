public class Epub implements Ebook {
    private int size;
    private int hash;
    private int pages;
    private String title;
    private String publisher;

    public Epub(String title, String publisher, int pages) {
        this.title = title;
        this.publisher = publisher;
        this.pages = pages;
        this.hash = title.hashCode();
        this.size = title.getBytes().length;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int getHash() {
        return hash;
    }

    @Override
    public int getPagesNumber() {
        return pages;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getPublisher() {
        return publisher;
    }
}
