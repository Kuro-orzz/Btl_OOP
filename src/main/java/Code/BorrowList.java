package Code;

import Logic.CsvReader;

import java.util.ArrayList;
import java.util.List;


public class BorrowList {
    private List<Borrow> borrowList = new ArrayList<>();

    public BorrowList() {}

    public BorrowList(String fileName) {
        borrowList = new CsvReader().getListBorrowFromFile(fileName);
    }

    public List<Borrow> getBorrowList() {
        return borrowList;
    }
}