package Logic;

import java.util.Scanner;

public class Option {
    public void showOption() {
        System.out.println("Option");
        System.out.println("[0] Exit");
        System.out.println("[1] Add Document");
        System.out.println("[2] Remove Document");
        System.out.println("[3] Update Document");
        System.out.println("[4] Find Document");
        System.out.println("[5] Display Document");
        System.out.println("[6] Add User");
        System.out.println("[7] Borrow Document");
        System.out.println("[8] Return Document");
        System.out.println("[9] Display User Info");
    }

    public int readOption() {
        System.out.print("Select option: ");
        Scanner sc1 = new Scanner(System.in);
        String select = sc1.nextLine();
        try {
            int number = Integer.parseInt(select);
            if (number < 0 || number > 9) {
                System.out.println("Action is not supported");
                return -1;
            }
            return number;
        } catch (NumberFormatException e) {
            System.out.println("Action is not supported");
        }
        return -1;
    }

    public void run() {
        showOption();
        int select = readOption();
        if (select == 0) {
            return;
        } else if (select == 1) {

        }
    }
}