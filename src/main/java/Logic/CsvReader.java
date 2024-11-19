package Logic;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
                String tmp = Arrays.toString(line);
                tmp = tmp.substring(1, tmp.length() - 1);
                line = tmp.split("\";\"");
                records.add(line);
            }
            List<Book> books = new ArrayList<>();
            for (int i = 1; i <= 10000; i++) {
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
            List<String[]> records = reader.readAll();
            List<Account> accounts = new ArrayList<>();
            for (String[] record : records) {
                UserInfo userInfo = new UserInfo(record[3], Integer.parseInt(record[4]), Boolean.parseBoolean(record[5]));
                Account account = new Account(record[0], record[1], Boolean.parseBoolean(record[2]), userInfo);
                accounts.add(account);
            }
            return accounts;
        } catch (IOException | CsvException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void appendAccountToFile(Account account, String fileName) {
        String filePath;
        try {
            filePath = CsvReader.class.getClassLoader().getResource(fileName).getPath();
        } catch (NullPointerException e) {
            System.out.println("File not found in resources: " + fileName);
            return;
        }
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath, true))) {
            String[] record = {
                    account.getUsername(),
                    account.getPassword(),
                    String.valueOf(account.isAdmin()),
                    account.getFullName(),
                    String.valueOf(account.getInfo().getAge()),
                    String.valueOf(account.getInfo().getGender())
            };
            writer.writeNext(record);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
