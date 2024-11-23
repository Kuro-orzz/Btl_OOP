package UI.Sidebar.BorrowRequest.BorrowRequestData;

import CsvFile.AppendDataToFile;
import CsvFile.GetDataFromFile;
import CsvFile.UpdateDataFromListToFile;

import java.util.ArrayList;
import java.util.List;

public class BorrowRequestList {
    private List<BorrowRequest> borrowRequestsList = new ArrayList<>();

    public BorrowRequestList() {}

    public BorrowRequestList(String fileName) {
        borrowRequestsList = new GetDataFromFile().getBorrowRequestFromFile(fileName);
    }

    public List<BorrowRequest> getBorrowRequestsList() {
        return borrowRequestsList;
    }

    public void setBorrowRequestsList(List<BorrowRequest> borrowRequestsList) {
        this.borrowRequestsList = borrowRequestsList;
    }

    public void addBorrowRequest(BorrowRequest borrowRequest) {
        borrowRequestsList.add(borrowRequest);
        AppendDataToFile append = new AppendDataToFile();
        append.appendBorrowRequest("borrowRequest.csv", borrowRequest);
    }

    public void removeBorrowRequest(BorrowRequest borrowRequest) {
        borrowRequestsList.remove(borrowRequest);
        UpdateDataFromListToFile update = new UpdateDataFromListToFile();
        update.updateBorrowRequest("borrowRequest.csv", borrowRequestsList);
    }
}
