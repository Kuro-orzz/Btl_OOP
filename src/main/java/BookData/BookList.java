package BookData;

import Logic.CsvReader;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
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
                updateDataFromList("books.csv", bookList);
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

    public List<Book> search(String type, String keyword) {
        String a = keyword.toLowerCase();
        long hash1 = new HashingMultipleBase().hashInfo(a, 1);
        long hash2 = new HashingMultipleBase().hashInfo(a, 2);
        List<Book> searchResult = new ArrayList<>();
        for (int i = 0; i < bookList.size(); i++) {
            Book book = bookList.get(i);
            int length = 0;
            if (type.equals("isbn")) {
                length = book.getIsbn().length();
            } else if (type.equals("title")) {
                length = book.getTitle().length();
            } else if (type.equals("author")) {
                length = book.getAuthor().length();
            } else if (type.equals("yearOfPublication")) {
                length = book.getYearOfPublication().length();
            } else if (type.equals("publisher")) {
                length = book.getPublisher().length();
            } else {
                System.out.println("Not match any search type");
            }
            try {
                for (int j = 1; j <= length - keyword.length() + 1; j++) {
                    HashingMultipleBase hash = new HashingMultipleBase();
                    if (type.equals("isbn")) {
                        hash = book.hashIsbn;
                    } else if (type.equals("title")) {
                        hash = book.hashTitle;
                    } else if (type.equals("author")) {
                        hash = book.hashAuthor;
                    } else if (type.equals("yearOfPublication")) {
                        hash = book.hashYearOfPublication;
                    } else if (type.equals("publisher")) {
                        hash = book.hashPublisher;
                    } else {
                        System.out.println("Not match any search type");
                    }
                    boolean checkHash1 = (hash.getHash(j, keyword.length() + j - 1, 1) == hash1);
                    boolean checkHash2 = (hash.getHash(j, keyword.length() + j - 1, 2) == hash2);
                    if (checkHash1 && checkHash2) {
                        searchResult.add(book);
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return searchResult;
    }
}
