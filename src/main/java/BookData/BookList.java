package BookData;

import Logic.CsvReader;

import java.util.ArrayList;
import java.util.List;

public class BookList extends CsvReader {
    private List<Book> bookList = new ArrayList<Book>();

    public BookList() {}

    public BookList(String fileName) {
        bookList = new CsvReader().getDataFromFile(fileName);
    }

    public void addBook(Book book) {
        bookList.add(book);
    }

    public void removeBook(Book book) {
        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getIsbn() == book.getIsbn()) {
                bookList.remove(i);
                return;
            }
        }
    }

//    public void updateBook(Book book) {
//        int index = bookList.indexOf(book);
//        bookList.set(index, book);
//    }
    // thiếu modify book

    public List<Book> getBookList() {
        return bookList;
    }


}