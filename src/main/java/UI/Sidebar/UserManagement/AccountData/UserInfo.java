package UI.Sidebar.UserManagement.AccountData;

public class UserInfo {
    private String fullName;
    private int age;
    private boolean gender;
    private String phoneNumber;
    private String email;
    private String address;
    private String numberOfBorrowed;
    private String numberOfReturned;

    public UserInfo(String name, int age, boolean gender) {
        this.fullName = name;
        this.age = age;
        this.gender = gender;
        this.phoneNumber = "";
        this.email = "";
        this.address = "";
        this.numberOfBorrowed = "0";
        this.numberOfReturned = "0";
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumberOfBorrowed() {
        return numberOfBorrowed;
    }

    public void setNumberOfBorrowed(String numberOfBorrowed) {
        this.numberOfBorrowed = numberOfBorrowed;
    }

    public String getNumberOfReturned() {
        return numberOfReturned;
    }

    public void setNumberOfReturned(String numberOfReturned) {
        this.numberOfReturned = numberOfReturned;
    }
}