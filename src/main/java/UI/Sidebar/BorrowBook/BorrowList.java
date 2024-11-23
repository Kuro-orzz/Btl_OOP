package UI.Sidebar.BorrowBook;

import Logic.CsvReader;
import UI.Sidebar.Library.BookData.HashingMultipleBase;
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

    public List<Borrow> search(String type, String keyword) {
        String a = keyword.toLowerCase();
        long hash1 = new HashingMultipleBase().hashInfo(a, 1);
        long hash2 = new HashingMultipleBase().hashInfo(a, 2);
        List<Borrow> searchResult = new ArrayList<>();
        for (int i = 0; i < borrowList.size(); i++) {
            Borrow borrow = borrowList.get(i);
            int length = 0;
            if (type.equals("isbn")) {
                length = borrow.getIsbn().length();
            } else if (type.equals("title")) {
                length = borrow.getBookTitle().length();
            } else if (type.equals("username")) {
                length = borrow.getUsername().length();
            } else {
                System.out.println("Not match any search type");
            }
            try {
                for (int j = 1; j <= length - keyword.length() + 1; j++) {
                    HashingMultipleBase hash = new HashingMultipleBase();
                    if (type.equals("isbn")) {
                        hash = borrow.hashIsbn;
                    } else if (type.equals("title")) {
                        hash = borrow.hashTitle;
                    } else if (type.equals("username")) {
                        hash = borrow.hashUsername;
                    } else {
                        System.out.println("Not match any search type");
                    }
                    boolean checkHash1 = (hash.getHash(j, keyword.length() + j - 1, 1) == hash1);
                    boolean checkHash2 = (hash.getHash(j, keyword.length() + j - 1, 2) == hash2);
                    if (checkHash1 && checkHash2) {
                        searchResult.add(borrow);
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