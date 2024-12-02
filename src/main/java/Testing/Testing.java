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