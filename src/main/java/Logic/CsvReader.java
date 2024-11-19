package Logic;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
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

    public void createAccountDataFile(String fileName, List<Account> data) {
        File file = new File(fileName);
        try {
            FileWriter outputfile = new FileWriter(file);

            CSVWriter writer = new CSVWriter(outputfile);

            String[] header = {"Username", "Password", "Isadmin", "Fullname", "age", "gender"};

            writer.writeNext(header);

            for (int i = 0; i < data.size(); i++) {
                Account account = data.get(i);
                UserInfo userInfo = account.getInfo();
                String[] info = {account.getUsername(), account.getPassword(), Boolean.toString(account.isAdmin())
                        , userInfo.getFullName(), Integer.toString(userInfo.getAge()), Boolean.toString(userInfo.getGender())};
                writer.writeNext(info);
            }

            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}