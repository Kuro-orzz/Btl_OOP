package Logic;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Option newOption = new Option();
        newOption.run();
        Login log = new Login();
        for (int i = 0; i < 10; i++)
            log.run();
        BookList bookList = new BookList("books.csv");
        List<Book> data = bookList.searchTitle("");
        for (Book book : data) {
            System.out.println(book.getInfo());
        }
    }
}
