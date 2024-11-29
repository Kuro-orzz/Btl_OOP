package CsvFile;

import UI.Sidebar.BorrowBook.BorrowedData.Borrowed;
import UI.Sidebar.BorrowRequest.BorrowRequestData.BorrowRequest;
import UI.Sidebar.UserManagement.AccountData.UserInfo;
import UI.Sidebar.Library.BookData.Book;
import UI.Sidebar.UserManagement.AccountData.Account;
import com.opencsv.CSVWriter;

import java.io.IOException;

public class AppendDataToFile extends InitCsvFile {
    public AppendDataToFile() {}

    /**
     * Append book to the end of file.
     * @param fileName file that book will be appended
     * @param newBook book append to file
     */
    public void appendBook(String fileName, Book newBook) {
        try {
            CSVWriter writer = initCsvWriter(fileName, true);
            String[] info = {
                    newBook.getIsbn(),
                    newBook.getTitle(),
                    newBook.getAuthor(),
                    newBook.getYearOfPublication(),
                    newBook.getPublisher(),
                    newBook.getQuantity()
            };
            writer.writeNext(info);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Append account to the end of file.
     * @param fileName file that account will be appended
     * @param account account append to file
     */
    public void appendAccount(String fileName, Account account) {
        try {
            CSVWriter writer = initCsvWriter(fileName, true);
            UserInfo userInfo = account.getInfo();
            String[] info = {
                    String.valueOf(account.getId()),
                    account.getUsername(),
                    account.getPassword(),
                    Boolean.toString(account.isAdmin()),
                    userInfo.getFullName(),
                    Integer.toString(userInfo.getAge()),
                    Boolean.toString(userInfo.getGender()),
                    userInfo.getPhoneNumber(),
                    userInfo.getEmail(),
                    userInfo.getAddress(),
                    userInfo.getNumberOfBorrowed(),
                    userInfo.getNumberOfReturned()
            };
            writer.writeNext(info);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Append borrowed to the end of file.
     * @param fileName file that borrowed will be appended
     * @param borrowed borrowed append to file
     */
    public void appendBorrowed(String fileName, Borrowed borrowed) {
        try {
            CSVWriter writer = initCsvWriter(fileName, true);
            String[] info = {
                    String.valueOf(borrowed.getId()),
                    borrowed.getFullName(),
                    borrowed.getIsbn(),
                    borrowed.getBorrowedDate(),
                    borrowed.getDueDate(),
                    borrowed.getStatus()
            };
            writer.writeNext(info);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Append borrow request to the end of file.
     * @param fileName file that borrow request will be appended
     * @param borrowRequest borrow request append to file
     */
    public void appendBorrowRequest(String fileName, BorrowRequest borrowRequest) {
        try {
            CSVWriter writer = initCsvWriter(fileName, true);
            String[] info = {
                    String.valueOf(borrowRequest.getId()),
                    borrowRequest.getFullName(),
                    borrowRequest.getIsbn(),
                    borrowRequest.getRequestDate(),
                    borrowRequest.getStatus()
            };
            writer.writeNext(info);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
