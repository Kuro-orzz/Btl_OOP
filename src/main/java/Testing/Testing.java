package Testing;

import CsvFile.AppendDataToFile;
import CsvFile.InitCsvFile;
import UI.Sidebar.BorrowRequest.BorrowRequestData.BorrowRequest;

public class Testing {
    public static void main(String[] args) {
//        Option newOption = new Option();
//        newOption.run();
//        Login log = new Login();
//        for (int i = 0; i < 10; i++)
//            log.run();
//        BookList bookList = new BookList("books.csv");
//        List<Book> data = bookList.searchTitle("");
//        for (Book book : data) {
//            System.out.println(book.getInfo());
//        }
//        CsvReader csvReader = new CsvReader();
//        InitCsvFile csv = new InitCsvFile();
//        csv.createBorrowRequestDataFile("borrowsRequest.csv");
//        csv.createBorrowedDataFile("borrowed.csv");
//        List<Book> data = csvReader.getDataFromFile("books.csv");
//        csvReader.updateDataFromList("books.csv", data);
//        csvReader.createAccountDataFile("accounts.csv", new AccountList("accounts.csv").getAccountList());
//        BookList bookList = new BookList("books.csv");
//        csvReader.updateDataFromList("books.csv", bookList.getBookList());
//        csvReader.createAccountDataFile("accounts.csv", new AccountList().getAccountList());
//        csvReader.appendAccountToFile(new Account("abc", "bcd", true, new UserInfo("abc", 12, true)), "accounts.csv");
//        String[] header = {"ISBN", "Book-Title", "Book-Author", "Year-of-Publication", "Publisher",
//                "Quantity", "Image-URL-S", "Image-URL-M", "Image-URL-L"};
//        String s = Arrays.toString(header);
//        System.out.println(s);
//        s = s.replaceAll("\\[|\\]", "\"");
//        s = s.replaceAll(", ", "\";\"");
//        System.out.println(s);
        InitCsvFile initCsvFile = new InitCsvFile();
        initCsvFile.createBorrowRequestDataFile("borrowRequest.csv");
        AppendDataToFile appendDataToFile = new AppendDataToFile();
        appendDataToFile.appendBorrowRequest("borrowRequest.csv", new BorrowRequest("12", "DTH", "0123", "24/11/2024", "Pending"));
    }
}