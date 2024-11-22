package Logic;

import UI.Sidebar.UserManagement.AccountData.Account;
import UI.Sidebar.Library.BookData.Book;
import UI.Sidebar.BorrowBook.Borrow;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {
    public List<Book> getDataFromFile(String fileName) {
        String filePath;
        try {
            filePath = CsvReader.class.getClassLoader().getResource(fileName).getPath();
        } catch (NullPointerException e) {
            System.out.println("File not found in resources: " + fileName);
            return null;
        }
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> records = new ArrayList<>();
            String[] line;
            while ((line = reader.readNext()) != null) {
                records.add(line);
            }
            List<Book> books = new ArrayList<>();
            for (int i = 1; i < records.size(); i++) {
                books.add(new Book(records.get(i)));
            }
            return books;
        } catch (IOException | CsvException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Account> getAccountsFromFile(String fileName) {
        String filePath;
        try {
            filePath = CsvReader.class.getClassLoader().getResource(fileName).getPath();
        } catch (NullPointerException e) {
            System.out.println("File not found in resources: " + fileName);
            return null;
        }
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> records = new ArrayList<>();
            String[] line;
            while ((line = reader.readNext()) != null) {
                records.add(line);
            }
            List<Account> accounts = new ArrayList<>();
            for (int i = 1; i < records.size(); i++) {
                accounts.add(new Account(records.get(i)));
            }
            return accounts;
        } catch (IOException | CsvException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Use when delete book and need to rewrite all data
     * @param fileName csv file
     * @param bookList list of books in library
     */
    public void updateDataFromList(String fileName, List<Book> bookList) {
        String filePath = "src/main/resources/" + fileName;
        File file = new File(filePath);
        try {
            FileWriter outputfile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputfile);
            String[] header = {"ISBN", "Book-Title", "Book-Author", "Year-of-Publication", "Publisher", "Quantity"};
            writer.writeNext(header);
            for (int i = 0; i < bookList.size(); i++) {
                Book book = bookList.get(i);
                String[] info = {book.getIsbn(), book.getTitle(), book.getAuthor(), book.getYearOfPublication(),
                    book.getPublisher(), book.getQuantity()};
                writer.writeNext(info);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void appendAccountToFile(Account account, String fileName) {
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

    public void appendBookToFile(Book newBook, String fileName) {
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

    public List<Borrow> getListBorrowFromFile(String fileName) {
        String filePath;
        try {
            filePath = CsvReader.class.getClassLoader().getResource(fileName).getPath();
        } catch (NullPointerException e) {
            System.out.println("File not found in resources: " + fileName);
            return null;
        }
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> records = new ArrayList<>();
            String[] line;
            while ((line = reader.readNext()) != null) {
                records.add(line);
            }
            List<Borrow> listBorrow = new ArrayList<>();
            for (int i = 1; i < records.size(); i++) {
                listBorrow.add(new Borrow(records.get(i)));
            }
            return listBorrow;
        } catch (IOException | CsvException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
        return null;
    }

    public void appendBorrowToFile(String fileName, Borrow borrow) {
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

//    public void createBorrowDataFile(String fileName) {
//        String filePath = "src/main/resources/" + fileName;
//        File file = new File(filePath);
//        try {
//            FileWriter outputfile = new FileWriter(file);
//            CSVWriter writer = new CSVWriter(outputfile);
//            String[] header = {"Username", "Title", "Status", "Borrowed Date"};
//            writer.writeNext(header);
//            writer.close();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    // add delete data

//    public void createAccountDataFile(String fileName, List<Account> data) {
//        String filePath = "src/main/resources/" + fileName;
//        File file = new File(filePath);
//        try {
//            FileWriter outputfile = new FileWriter(file);
//            CSVWriter writer = new CSVWriter(outputfile);
//            String[] header = {"Id", "Username", "Password", "Isadmin", "Fullname", "age", "gender"};
//            writer.writeNext(header);
//            for (int i = 0; i < data.size(); i++) {
//                Account account = data.get(i);
//                UserInfo userInfo = account.getInfo();
//                String[] info = {String.valueOf(i+1), account.getUsername(), account.getPassword(), Boolean.toString(account.isAdmin())
//                        , userInfo.getFullName(), Integer.toString(userInfo.getAge()), Boolean.toString(userInfo.getGender())};
//                writer.writeNext(info);
//            }
//            writer.close();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
