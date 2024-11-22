package CsvFile;

import Logic.UserInfo;
import UI.Sidebar.BorrowBook.Borrow;
import UI.Sidebar.Library.BookData.Book;
import UI.Sidebar.UserManagement.AccountData.Account;
import com.opencsv.CSVWriter;

import java.io.IOException;
import java.util.List;

public class UpdateDataFromListToFile extends InitCsvFile {
    public UpdateDataFromListToFile() {}

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
            e.printStackTrace();
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
                UserInfo userInfo = account.getInfo();
                String[] info = {
                        String.valueOf(account.getId()),
                        account.getUsername(),
                        account.getPassword(),
                        Boolean.toString(account.isAdmin()),
                        userInfo.getFullName(),
                        Integer.toString(userInfo.getAge()),
                        Boolean.toString(userInfo.getGender())
                };
                writer.writeNext(info);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Use when delete borrow and need to rewrite all data.
     * @param fileName database of borrows
     * @param borrowsList list of borrows in library
     */
    public void updateBorrows(String fileName, List<Borrow> borrowsList) {
        try {
            CSVWriter writer = initCsvWriter(fileName, false);
            writer.writeNext(AccountInfo);
            for (Borrow borrow : borrowsList) {
                String[] info = {
                        borrow.getUsername(),
                        borrow.getBookTitle(),
                        borrow.getStatus(),
                        borrow.getBorrowDate()
                };
                writer.writeNext(info);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
