package CsvFile;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class InitCsvFile {
    private final String[] BookInfo = {"ISBN", "Book-Title", "Book-Author", "Year-of-Publication", "Publisher",
            "Quantity", "Image-URL-S", "Image-URL-M", "Image-URL-L"};
    private final String[] AccountInfo = {"Id", "Username", "Password", "Isadmin", "Fullname", "age", "gender"};
    private final String[] BorrowInfo = {"Username", "Title", "Status", "Borrowed Date"};

    public void createBookDataFile(String fileName) {
        String filePath = "src/main/resources/" + fileName;
        File file = new File(filePath);
        try {
            FileWriter outputfile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputfile);
            writer.writeNext(BookInfo);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createAccountDataFile(String fileName) {
        String filePath = "src/main/resources/" + fileName;
        File file = new File(filePath);
        try {
            FileWriter outputfile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputfile);
            writer.writeNext(AccountInfo);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createBorrowDataFile(String fileName) {
        String filePath = "src/main/resources/" + fileName;
        File file = new File(filePath);
        try {
            FileWriter outputfile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputfile);
            writer.writeNext(BorrowInfo);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
