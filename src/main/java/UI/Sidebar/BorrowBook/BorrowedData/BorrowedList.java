package UI.Sidebar.BorrowBook.BorrowedData;

import CsvFile.AppendDataToFile;
import CsvFile.GetDataFromFile;
import CsvFile.UpdateDataFromListToFile;

import java.util.ArrayList;
import java.util.List;


public class BorrowedList {
    private List<Borrowed> borrowedList = new ArrayList<>();

    public BorrowedList() {}

    public BorrowedList(String fileName) {
        borrowedList = new GetDataFromFile().getBorrowedFromFile(fileName);
    }

    public List<Borrowed> getBorrowedList() {
        return borrowedList;
    }

    public void setBorrowedList(List<Borrowed> borrowedList) {
        this.borrowedList = borrowedList;
    }

    public void addBorrowed(Borrowed borrowed) {
        borrowedList.add(borrowed);
        AppendDataToFile append = new AppendDataToFile();
        append.appendBorrowed("borrowed.csv", borrowed);
    }

    public void removeBorrowed(Borrowed borrowed) {
        borrowedList.remove(borrowed);
        UpdateDataFromListToFile update = new UpdateDataFromListToFile();
        update.updateBorrowed("borrowed.csv", borrowedList);
    }
}