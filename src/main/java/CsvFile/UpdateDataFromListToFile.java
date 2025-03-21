package CsvFile;

import UI.Sidebar.BorrowBook.BorrowedData.Borrowed;
import UI.Sidebar.BorrowRequest.BorrowRequestData.BorrowRequest;
import UI.Sidebar.Library.BookData.Book;
import UI.Sidebar.UserManagement.AccountData.Account;
import com.opencsv.CSVWriter;

import java.io.IOException;
import java.util.List;

public class UpdateDataFromListToFile extends InitCsvFile {
    public UpdateDataFromListToFile() {}

    /**
     * Update book quantity.
     * @param fileName file name
     * @param isbn isbn
     * @param data data in file
     */
    public void updateBookQuantityWithGetData(String fileName, String isbn, GetDataFromFile data) {
        List<Book> books = data.getBooksFromFile(fileName);
        if (books == null) return; // If unable to fetch books, exit.

        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                int currentQuantity = Integer.parseInt(book.getQuantity());
                if (currentQuantity > 0) {
                    book.setQuantity(String.valueOf(currentQuantity - 1));
                } else {
                    System.out.println("Book with ISBN " + isbn + " has no available quantity.");
                }
                break;
            }
        }
        updateBooks(fileName, books);
    }

    /**
     * Use when delete book and need to rewrite all data.
     * @param fileName database of books
     * @param booksList list of books in library
     */
    public void updateBooks(String fileName, List<Book> booksList) {
        try {
            CSVWriter writer = initCsvWriter(fileName, false);
            writer.writeNext(BookInfo);
            for (Book book : booksList) {
                String[] info = {
                        book.getIsbn(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getYearOfPublication(),
                        book.getPublisher(),
                        book.getQuantity()
                };
                writer.writeNext(info);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Use when delete account and need to rewrite all data.
     * @param fileName database of accounts
     * @param accountsList list of accounts in library
     */
    public void updateAccounts(String fileName, List<Account> accountsList) {
        try {
            CSVWriter writer = initCsvWriter(fileName, false);
            writer.writeNext(AccountInfo);
            for (Account account : accountsList) {
                String[] info = getAccountInfo(account);
                writer.writeNext(info);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Use when delete borrowed and need to rewrite all data.
     * @param fileName database of borrowed
     * @param borrowedList list of borrowed in library
     */
    public void updateBorrowed(String fileName, List<Borrowed> borrowedList) {
        try {
            CSVWriter writer = initCsvWriter(fileName, false);
            writer.writeNext(BorrowedInfo);
            for (Borrowed borrowed : borrowedList) {
                String[] info = {
                        String.valueOf(borrowed.getId()),
                        borrowed.getFullName(),
                        borrowed.getIsbn(),
                        borrowed.getBorrowedDate(),
                        borrowed.getDueDate(),
                        borrowed.getStatus()
                };
                writer.writeNext(info);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Use when delete borrow request and need to rewrite all data.
     * @param fileName database of borrow request
     * @param borrowRequestList list of borrow request in library
     */
    public void updateBorrowRequest(String fileName, List<BorrowRequest> borrowRequestList) {
        try {
            CSVWriter writer = initCsvWriter(fileName, false);
            writer.writeNext(BorrowRequestInfo);
            for (BorrowRequest borrowRequest : borrowRequestList) {
                String[] info = {
                        String.valueOf(borrowRequest.getId()),
                        borrowRequest.getFullName(),
                        borrowRequest.getIsbn(),
                        borrowRequest.getRequestDate(),
                        borrowRequest.getStatus()
                };
                writer.writeNext(info);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
