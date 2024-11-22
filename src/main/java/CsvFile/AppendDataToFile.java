package CsvFile;

import Logic.UserInfo;
import UI.Sidebar.BorrowBook.Borrow;
import UI.Sidebar.Library.BookData.Book;
import UI.Sidebar.UserManagement.AccountData.Account;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AppendDataToFile {
    public void appendBook(String fileName, Book newBook) {
        String filePath = "src/main/resources/" + fileName;
        File file = new File(filePath);
        try {
            FileWriter outputfile = new FileWriter(file, true);
            CSVWriter writer = new CSVWriter(outputfile);
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

    public void appendAccount(String fileName, Account account) {
        String filePath = "src/main/resources/" + fileName;
        File file = new File(filePath);
        try {
            FileWriter outputfile = new FileWriter(file, true);
            CSVWriter writer = new CSVWriter(outputfile);
            UserInfo userInfo = account.getInfo();
            String[] info = {String.valueOf(account.getId()), account.getUsername(), account.getPassword(), Boolean.toString(account.isAdmin())
                    , userInfo.getFullName(), Integer.toString(userInfo.getAge()), Boolean.toString(userInfo.getGender())};
            writer.writeNext(info);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void appendBorrow(String fileName, Borrow borrow) {
        String filePath = "src/main/resources/" + fileName;
        File file = new File(filePath);
        try {
            FileWriter outputfile = new FileWriter(file, true);
            CSVWriter writer = new CSVWriter(outputfile);
            String[] row = {
                    borrow.getUsername(),
                    borrow.getBookTitle(),
                    borrow.getStatus(),
                    borrow.getBorrowDate()
            };
            writer.writeNext(row);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
