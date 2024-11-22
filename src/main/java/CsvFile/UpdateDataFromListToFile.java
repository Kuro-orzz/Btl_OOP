package CsvFile;

import UI.Sidebar.Library.BookData.Book;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class UpdateDataFromListToFile {
    /**
     * Use when delete book and need to rewrite all data
     * @param fileName csv file
     * @param bookList list of books in library
     */
    public void updateBooks(String fileName, List<Book> bookList) {
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
}
