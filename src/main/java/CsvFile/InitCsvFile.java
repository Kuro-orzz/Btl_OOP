package CsvFile;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class InitCsvFile extends DataHeader {
    public InitCsvFile() {}

    public CSVWriter initCsvWriter(String fileName, boolean isAppend) {
        try {
            String filePath = "src/main/resources/" + fileName;
            File file = new File(filePath);
            FileWriter outfile = new FileWriter(file, isAppend);
            return new CSVWriter(outfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createBookDataFile(String fileName) {
        try {
            CSVWriter writer = initCsvWriter(fileName, false);
            writer.writeNext(BookInfo);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createAccountDataFile(String fileName) {
        try {
            CSVWriter writer = initCsvWriter(fileName, false);
            writer.writeNext(AccountInfo);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createBorrowDataFile(String fileName) {
        try {
            CSVWriter writer = initCsvWriter(fileName, false);
            writer.writeNext(BorrowInfo);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
