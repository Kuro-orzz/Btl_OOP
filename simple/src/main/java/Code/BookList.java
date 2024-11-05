package Code;

import java.util.ArrayList;
import java.util.List;

public class BookList {
    private List<Book> bookList = new ArrayList<Book>();

    public BookList() {}

    public void addBook(Book book) {
        bookList.add(book);
    }

    public void removeBook(Book book) {
        bookList.remove(book);
    }

    public void updateBook(Book book) {
        int index = bookList.indexOf(book);
        bookList.set(index, book);
    }
}
