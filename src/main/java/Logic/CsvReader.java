package Logic;

import AccountData.Account;
import BookData.Book;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import Code.Borrow;

import java.time.LocalDate;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            for (int i = 1; i < records.size(); i++) {
                String[] record = records.get(i);
                try {
                    if (record.length < 4) {
                        System.out.println("Insufficient data at line " + (i + 1));
                        continue;
                    }
                    Account userName = new Account(record[0]);
                    Book book = new Book(record[1]);
                    String status = record[2];
                    LocalDate borrowedDate = LocalDate.parse(record[3], formatter);
                    Borrow borrow = new Borrow(userName, book, status, borrowedDate);
                    listBorrow.add(borrow);
                } catch (ArrayIndexOutOfBoundsException | DateTimeParseException e) {
                    System.out.println("Invalid data format at line " + (i + 1) + ": " + e.getMessage());
                }
            }
            return listBorrow;
        } catch (IOException | CsvException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
        return null;
    }
//}

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
            String[] header = {"ISBN", "Book-Title", "Book-Author", "Year-of-Publication", "Publisher",
                    "Quantity", "Image-URL-S", "Image-URL-M", "Image-URL-L"};
            writer.writeNext(header);
            for (int i = 0; i < bookList.size(); i++) {
                Book book = bookList.get(i);
                String[] info = {book.getIsbn(), book.getTitle(), book.getAuthor(), book.getYearOfPublication(),
                    book.getPublisher(), book.getQuantity(),
                    book.getIMG_PATH_SIZE_S(), book.getIMG_PATH_SIZE_M(), book.getIMG_PATH_SIZE_L()};
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

//    public void appendListBorrowToFile(Borrow borrow, String fileName) {
//        String filePath = "src/main/resources/" + fileName;
//        File file = new File(filePath);
//        try {
//            FileWriter outputfile = new FileWriter(file, true);
//            CSVWriter writer = new CSVWriter(outputfile);
//            String[] row = {
//                    borrow.getusername(),
//                    borrow.getTitle(),
//                    borrow.getStatus(),
//                    borrow.getborrowedDate().toString()
//            };
//            writer.writeNext(row);
//            writer.close();
//        } catch (IOException e) {
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
