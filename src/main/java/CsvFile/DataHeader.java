package CsvFile;

public class DataHeader {
    public final String[] BookInfo = {
            "ISBN",
            "Book-Title",
            "Book-Author",
            "Year-of-Publication",
            "Publisher",
            "Quantity"
    };
    public final String[] AccountInfo = {
            "Id",
            "Username",
            "Password",
            "Isadmin",
            "Fullname",
            "age",
            "gender",
            "phone",
            "email",
            "address",
            "numberOfBorrowed",
            "numberOfReturned"
    };
    public final String[] BorrowRequestInfo = {
            "Id",
            "Full name",
            "ISBN",
            "Request date",
            "Status"
    };
    public final String[] BorrowedInfo = {
            "Id",
            "Full name",
            "ISBN",
            "Borrowed date",
            "Due date",
            "Status"
    };
}
