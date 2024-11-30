package UI.Sidebar.Library.BookData;

import CsvFile.AppendDataToFile;
import CsvFile.GetDataFromFile;
import CsvFile.UpdateDataFromListToFile;
import Optimize.HashingMultipleBase;

import java.util.ArrayList;
import java.util.List;

public class BookList {
    private List<Book> bookList = new ArrayList<>();

    public BookList() {}

    public BookList(String fileName) {
        bookList = new GetDataFromFile().getBooksFromFile(fileName);
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public void addBook(Book book) {
        bookList.add(book);
        AppendDataToFile append = new AppendDataToFile();
        append.appendBook("books.csv", book);
    }

    public void removeBook(Book book) {
        bookList.remove(book);
        UpdateDataFromListToFile update = new UpdateDataFromListToFile();
        update.updateBooks("books.csv", bookList);
    }

    /**
     * Search book in book list using rolling hash.
     * @param type type of keyword (isbn/title/author/publisher/yearOfPublication)
     * @param keyword keyword
     * @return list book that have substring keyword
     */
    public List<Book> search(String type, String keyword) {
        String a = keyword.toLowerCase();
        long hash1 = new HashingMultipleBase().hashInfo(a, 1);
        long hash2 = new HashingMultipleBase().hashInfo(a, 2);
        List<Book> searchResult = new ArrayList<>();
        for (Book book : bookList) {
            int length = 0;
            switch (type) {
                case "isbn" -> length = book.getIsbn().length();
                case "title" -> length = book.getTitle().length();
                case "author" -> length = book.getAuthor().length();
                case "yearOfPublication" -> length = book.getYearOfPublication().length();
                case "publisher" -> length = book.getPublisher().length();
                default -> System.out.println("Not match any search type");
            }
            try {
                for (int j = 1; j <= length - keyword.length() + 1; j++) {
                    HashingMultipleBase hash = new HashingMultipleBase();
                    switch (type) {
                        case "isbn" -> hash = book.hashIsbn;
                        case "title" -> hash = book.hashTitle;
                        case "author" -> hash = book.hashAuthor;
                        case "yearOfPublication" -> hash = book.hashYearOfPublication;
                        case "publisher" -> hash = book.hashPublisher;
                        default -> System.out.println("Not match any search type");
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

    public long getTotalBooks() {
        return bookList.size();
    }
}
