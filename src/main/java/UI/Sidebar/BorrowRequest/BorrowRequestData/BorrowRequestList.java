package UI.Sidebar.BorrowRequest.BorrowRequestData;

import CsvFile.AppendDataToFile;
import CsvFile.GetDataFromFile;
import CsvFile.UpdateDataFromListToFile;
import Optimize.HashingMultipleBase;

import java.util.ArrayList;
import java.util.List;

public class BorrowRequestList {
    private List<BorrowRequest> borrowRequestList = new ArrayList<>();

    public BorrowRequestList() {}

    public BorrowRequestList(String fileName) {
        borrowRequestList = new GetDataFromFile().getBorrowRequestFromFile(fileName);
    }

    public List<BorrowRequest> getBorrowRequestList() {
        return borrowRequestList;
    }

    public void setBorrowRequestList(List<BorrowRequest> borrowRequestsList) {
        this.borrowRequestList = borrowRequestsList;
    }

    public void addBorrowRequest(BorrowRequest borrowRequest) {
        borrowRequestList.add(borrowRequest);
        AppendDataToFile append = new AppendDataToFile();
        append.appendBorrowRequest("borrowRequest.csv", borrowRequest);
    }

    public void removeBorrowRequest(BorrowRequest borrowRequest) {
        borrowRequestList.remove(borrowRequest);
        UpdateDataFromListToFile update = new UpdateDataFromListToFile();
        update.updateBorrowRequest("borrowRequest.csv", borrowRequestList);
    }

    /**
     * Search borrow request in borrow request list using rolling hash.
     * @param type type of keyword (id / full name / isbn / status)
     * @param keyword keyword
     * @return List of Book that contains keywords
     */
    public List<BorrowRequest> search(String type, String keyword) {
        String a = keyword.toLowerCase();
        long hash1 = new HashingMultipleBase().hashInfo(a, 1);
        long hash2 = new HashingMultipleBase().hashInfo(a, 2);
        List<BorrowRequest> searchResult = new ArrayList<>();
        for (BorrowRequest borrowRequest : borrowRequestList) {
            int length = 0;
            switch (type) {
                case "Id" -> length = borrowRequest.getId().length();
                case "Full name" -> length = borrowRequest.getFullName().length();
                case "Isbn" -> length = borrowRequest.getIsbn().length();
                case "Request date" -> length = borrowRequest.getRequestDate().length();
                case "Status" -> length = borrowRequest.getStatus().length();
                default -> System.out.println("Not match any search type");
            }
            try {
                for (int j = 1; j <= length - keyword.length() + 1; j++) {
                    HashingMultipleBase hash = new HashingMultipleBase();
                    switch (type) {
                        case "Id" -> hash = borrowRequest.hashId;
                        case "Full name" -> hash = borrowRequest.hashFullName;
                        case "Isbn" -> hash = borrowRequest.hashIsbn;
                        case "Request date" -> hash = borrowRequest.hashRequestDate;
                        case "Status" -> hash = borrowRequest.hashStatus;
                        default -> System.out.println("Not match any search type");
                    }
                    boolean checkHash1 = (hash.getHash(j, keyword.length() + j - 1, 1) == hash1);
                    boolean checkHash2 = (hash.getHash(j, keyword.length() + j - 1, 2) == hash2);
                    if (checkHash1 && checkHash2) {
                        searchResult.add(borrowRequest);
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return searchResult;
    }
}
