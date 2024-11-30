package UI.Sidebar.Library;

import CsvFile.AppendDataToFile;
import UI.Sidebar.BorrowRequest.BorrowRequestData.BorrowRequest;
import UI.Sidebar.Library.BookData.Book;
import UI.Sidebar.UserManagement.AccountData.Account;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BorrowBook {
    public void sendRequest(Book selectedBook, Account account) {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = date.format(formatter);
        BorrowRequest borrowRequest = new BorrowRequest(
                String.valueOf(account.getId()),
                account.getInfo().getFullName(), // Use account.getUsername() if needed
                selectedBook.getIsbn(),
                formattedDate,
                "Pending"
        );

        appendRequestToFile(borrowRequest);
    }

    private void appendRequestToFile(BorrowRequest borrowRequest) {
        AppendDataToFile appendDataToFile = new AppendDataToFile();
        appendDataToFile.appendBorrowRequest("borrowRequest.csv", borrowRequest);
    }
}
