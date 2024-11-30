package CsvFile;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class InitCsvFile extends DataHeader {
    public InitCsvFile() {}

    /**
     * Init Csv writer file by filename and status append or not.
     * @param fileName name of file want to init
     * @param isAppend status append or not
     * @return File Csv writer
     */
    public CSVWriter initCsvWriter(String fileName, boolean isAppend) {
        try {
            String filePath = "src/main/resources/" + fileName;
            File file = new File(filePath);
            FileWriter outfile = new FileWriter(file, isAppend);
            return new CSVWriter(outfile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Create file database of books.
     * @param fileName books database
     */
    public void createBookDataFile(String fileName) {
        try {
            CSVWriter writer = initCsvWriter(fileName, false);
            writer.writeNext(BookInfo);
            writer.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Create file database of accounts.
     * @param fileName accounts database
     */
    public void createAccountDataFile(String fileName) {
        try {
            CSVWriter writer = initCsvWriter(fileName, false);
            writer.writeNext(AccountInfo);
            writer.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Create file database of borrowed.
     * @param fileName borrowed database
     */
    public void createBorrowedDataFile(String fileName) {
        try {
            CSVWriter writer = initCsvWriter(fileName, false);
            writer.writeNext(BorrowedInfo);
            writer.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Create file database of borrows request.
     * @param fileName borrows request database
     */
    public void createBorrowRequestDataFile(String fileName) {
        try {
            CSVWriter writer = initCsvWriter(fileName, false);
            writer.writeNext(BorrowRequestInfo);
            writer.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
