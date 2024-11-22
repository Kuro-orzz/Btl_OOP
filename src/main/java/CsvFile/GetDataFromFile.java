package CsvFile;

import UI.Sidebar.BorrowBook.Borrow;
import UI.Sidebar.Library.BookData.Book;
import UI.Sidebar.UserManagement.AccountData.Account;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetDataFromFile {
    public GetDataFromFile() {}

    /**
     * Init Csv reader file by fileName.
     * @param fileName name of file will be init
     * @return Csv reader file
     */
    public CSVReader initCsvReader(String fileName) {
        try {
            String filePath = "src/main/resources/" + fileName;
            return new CSVReader(new FileReader(filePath));
        } catch (IOException e) {
            System.out.println("File not found in resources: " + fileName);
            return null;
        }
    }

    /**
     * Read data from file line by line.
     * @param reader file csv reader
     * @return list of data
     */
    public List<String[]> readData(CSVReader reader) {
        List<String[]> data = new ArrayList<>();
        try {
            String[] line;
            while ((line = reader.readNext()) != null) {
                data.add(line);
            }
        } catch (IOException | CsvValidationException e) {
            System.out.println("Error reading csv file: " + e.getMessage());
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Get all books data from file.
     * @param fileName name of file having book data
     * @return list of books data
     */
    public List<Book> getBooksFromFile(String fileName) {
        try (CSVReader reader = initCsvReader(fileName)) {
            List<String[]> records = readData(reader);
            List<Book> books = new ArrayList<>();
            for (int i = 1; i < records.size(); i++) {
                books.add(new Book(records.get(i)));
            }
            return books;
        } catch (IOException e) {
            System.out.println("File not found in resources: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get all accounts data from file.
     * @param fileName name of file having accounts data
     * @return list of accounts data
     */
    public List<Account> getAccountsFromFile(String fileName) {
        try (CSVReader reader = initCsvReader(fileName)) {
            List<String[]> records = readData(reader);
            List<Account> accounts = new ArrayList<>();
            for (int i = 1; i < records.size(); i++) {
                accounts.add(new Account(records.get(i)));
            }
            return accounts;
        } catch (IOException e) {
            System.out.println("File not found in resources: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get all borrows data from file.
     * @param fileName name of file having borrows data
     * @return list of borrows data
     */
    public List<Borrow> getBorrowsFromFile(String fileName) {
        try (CSVReader reader = initCsvReader(fileName)) {
            List<String[]> records = readData(reader);
            List<Borrow> borrows = new ArrayList<>();
            for (int i = 1; i < records.size(); i++) {
                borrows.add(new Borrow(records.get(i)));
            }
            return borrows;
        } catch (IOException e) {
            System.out.println("File not found in resources: " + e.getMessage());
        }
        return null;
    }
}
