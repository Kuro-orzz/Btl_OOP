package Logic;

public class Book extends HashingMultipleBase {
    private String isbn;
    private String title;
    private String author;
    private int yearOfPublication;
    private String publisher;
    private String IMG_PATH_SIZE_S;
    private String IMG_PATH_SIZE_M;
    private String IMG_PATH_SIZE_L;
    public HashingMultipleBase hashIsbn, hashTitle, hashAuthor, hashPublisher;

    public Book() {}

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        hashTitle = hashInit(title);
        hashAuthor = hashInit(author);
    }

    public Book(String[] data) {
        this.isbn = data[0];
        this.title = data[1];
        this.author = data[2];
        this.yearOfPublication = Integer.parseInt(data[3]);
        this.publisher = data[4];
        this.IMG_PATH_SIZE_S = data[5];
        this.IMG_PATH_SIZE_M = data[6];
        this.IMG_PATH_SIZE_L = data[7];
        hashIsbn = hashInit(isbn);
        hashTitle = hashInit(title);
        hashAuthor = hashInit(author);
        hashPublisher = hashInit(publisher);
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getInfo() {
        return isbn + "; " + title + "; " + author + "; " + yearOfPublication + "; " + publisher;
    }

    public HashingMultipleBase hashInit(String info) {
        HashingMultipleBase hash = new HashingMultipleBase(info.length());
        hash.hash(info, 1);
        hash.hash(info, 2);
        return hash;
    }
}
