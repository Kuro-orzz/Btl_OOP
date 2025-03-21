package Testing;

import CsvFile.GetDataFromFile;
import CsvFile.InitCsvFile;
import CsvFile.UpdateDataFromListToFile;
import UI.Sidebar.BorrowBook.BorrowedData.Borrowed;
import UI.Sidebar.BorrowRequest.BorrowRequestData.BorrowRequest;
import UI.Sidebar.Library.BookData.Book;
import UI.Sidebar.UserManagement.AccountData.Account;

import java.util.List;

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
        List<Book> bookList = new GetDataFromFile().getBooksFromFile("books.csv");
        List<Account> accountList = new GetDataFromFile().getAccountsFromFile("accounts.csv");
        List<Borrowed> borrowedList = new GetDataFromFile().getBorrowedFromFile("borrowed.csv");
        List<BorrowRequest> borrowRequestList = new GetDataFromFile().getBorrowRequestFromFile("borrowRequests.csv");

        InitCsvFile initCsvFile = new InitCsvFile();
        initCsvFile.createBookDataFile("books.csv");
        initCsvFile.createAccountDataFile("accounts.csv");
        initCsvFile.createBorrowedDataFile("borrowed.csv");
        initCsvFile.createBorrowRequestDataFile("borrowRequests.csv");

        UpdateDataFromListToFile updateDataFromListToFile = new UpdateDataFromListToFile();
        updateDataFromListToFile.updateBooks("books.csv", bookList);
        updateDataFromListToFile.updateAccounts("accounts.csv", accountList);
        updateDataFromListToFile.updateBorrowed("borrowed.csv", borrowedList);
        updateDataFromListToFile.updateBorrowRequest("borrowRequests.csv", borrowRequestList);
    }
}