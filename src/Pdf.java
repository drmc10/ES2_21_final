public class Pdf implements Ebook {
    private int size;
    private int hash;
    private String title;
    private String publisher;

    public Pdf(String title, String publisher) {
        this.title = title;
        this.publisher = publisher;
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
    public String getTitle() {
        return title;
    }

    @Override
    public String getPublisher() {
        return publisher;
    }
}
