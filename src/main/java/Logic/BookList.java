package Logic;

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

    public void updateBook(Book book) {
        int index = bookList.indexOf(book);
        bookList.set(index, book);
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public List<Book> searchIsbn(String keyword) {
        String a = keyword.toLowerCase();
        long hash1 = new HashingMultipleBase().hashInfo(a, 1);
        long hash2 = new HashingMultipleBase().hashInfo(a, 2);
        List<Book> searchResult = new ArrayList<>();
        for (int i = 0; i < bookList.size(); i++) {
            Book book = bookList.get(i);
            int titleLength = book.getIsbn().length();
            for (int j = 1; j <= titleLength - keyword.length() + 1; j++) {
                boolean checkHash1 = (book.hashIsbn.getHash(j, keyword.length() + j - 1, 1) == hash1);
                boolean checkHash2 = (book.hashIsbn.getHash(j, keyword.length() + j - 1, 2) == hash2);
                if (checkHash1 && checkHash2) {
                    searchResult.add(book);
                    break;
                }
            }
        }
        return searchResult;
    }

    public List<Book> searchTitle(String keyword) {
        String a = keyword.toLowerCase();
        long hash1 = new HashingMultipleBase().hashInfo(a, 1);
        long hash2 = new HashingMultipleBase().hashInfo(a, 2);
        List<Book> searchResult = new ArrayList<>();
        for (int i = 0; i < bookList.size(); i++) {
            Book book = bookList.get(i);
            int titleLength = book.getTitle().length();
            for (int j = 1; j <= titleLength - keyword.length() + 1; j++) {
                boolean checkHash1 = (book.hashTitle.getHash(j, keyword.length() + j - 1, 1) == hash1);
                boolean checkHash2 = (book.hashTitle.getHash(j, keyword.length() + j - 1, 2) == hash2);
                if (checkHash1 && checkHash2) {
                    searchResult.add(book);
                    break;
                }
            }
        }
        return searchResult;
    }

    public List<Book> searchAuthor(String keyword) {
        String a = keyword.toLowerCase();
        long hash1 = new HashingMultipleBase().hashInfo(a, 1);
        long hash2 = new HashingMultipleBase().hashInfo(a, 2);
        List<Book> searchResult = new ArrayList<>();
        for (int i = 0; i < bookList.size(); i++) {
            Book book = bookList.get(i);
            int titleLength = book.getAuthor().length();
            for (int j = 1; j <= titleLength - keyword.length() + 1; j++) {
                boolean checkHash1 = (book.hashAuthor.getHash(j, keyword.length() + j - 1, 1) == hash1);
                boolean checkHash2 = (book.hashAuthor.getHash(j, keyword.length() + j - 1, 2) == hash2);
                if (checkHash1 && checkHash2) {
                    searchResult.add(book);
                    break;
                }
            }
        }
        return searchResult;
    }

    public List<Book> searchPublisher(String keyword) {
        String a = keyword.toLowerCase();
        long hash1 = new HashingMultipleBase().hashInfo(a, 1);
        long hash2 = new HashingMultipleBase().hashInfo(a, 2);
        List<Book> searchResult = new ArrayList<>();
        for (int i = 0; i < bookList.size(); i++) {
            Book book = bookList.get(i);
            int titleLength = book.getPublisher().length();
            for (int j = 1; j <= titleLength - keyword.length() + 1; j++) {
                boolean checkHash1 = (book.hashPublisher.getHash(j, keyword.length() + j - 1, 1) == hash1);
                boolean checkHash2 = (book.hashPublisher.getHash(j, keyword.length() + j - 1, 2) == hash2);
                if (checkHash1 && checkHash2) {
                    searchResult.add(book);
                    break;
                }
            }
        }
        return searchResult;
    }
}
