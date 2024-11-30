package UI.Sidebar.BorrowBook.BorrowedData;

import CsvFile.AppendDataToFile;
import CsvFile.GetDataFromFile;
import CsvFile.UpdateDataFromListToFile;
import Optimize.HashingMultipleBase;

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

    /**
     * Search borrowed in borrowed list using rolling hash.
     * @param type type of keyword (id / full name / isbn / status)
     * @param keyword keyword
     * @return List of Book that contains keywords
     */
    public List<Borrowed> search(String type, String keyword) {
        String a = keyword.toLowerCase();
        long hash1 = new HashingMultipleBase().hashInfo(a, 1);
        long hash2 = new HashingMultipleBase().hashInfo(a, 2);
        List<Borrowed> searchResult = new ArrayList<>();
        for (Borrowed borrowed : borrowedList) {
            int length = 0;
            switch (type) {
                case "Id" -> length = borrowed.getId().length();
                case "Full name" -> length = borrowed.getFullName().length();
                case "Isbn" -> length = borrowed.getIsbn().length();
                case "Status" -> length = borrowed.getStatus().length();
                default -> System.out.println("Not match any search type");
            }
            try {
                for (int j = 1; j <= length - keyword.length() + 1; j++) {
                    HashingMultipleBase hash = new HashingMultipleBase();
                    switch (type) {
                        case "Id" -> hash = borrowed.hashId;
                        case "Full name" -> hash = borrowed.hashFullName;
                        case "Isbn" -> hash = borrowed.hashIsbn;
                        case "Status" -> hash = borrowed.hashStatus;
                        default -> System.out.println("Not match any search type");
                    }
                    boolean checkHash1 = (hash.getHash(j, keyword.length() + j - 1, 1) == hash1);
                    boolean checkHash2 = (hash.getHash(j, keyword.length() + j - 1, 2) == hash2);
                    if (checkHash1 && checkHash2) {
                        searchResult.add(borrowed);
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return searchResult;
    }

    public int getTotalBorrow() {
        return borrowedList.size();
    }
}