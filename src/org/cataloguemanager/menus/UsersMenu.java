package org.cataloguemanager.menus;

import org.apache.commons.lang3.StringUtils;
import org.cataloguemanager.App;
import org.cataloguemanager.models.Student;
import org.cataloguemanager.models.User;
import org.cataloguemanager.utils.TerminalUtils;

import java.util.Scanner;

public class UsersMenu {
    public static void run(Scanner scanner) {
        // TODO: Implement
        scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            TerminalUtils.displayLogo();
            IO.println(StringUtils.center(" List ", 30, "-")+"\n");
            IO.println("1: List All Users");    // Menu, 0 to return here, or id to veiw information
            IO.println("2: List Students");
            IO.println("3: List Staff\n");
            IO.println(StringUtils.center(" User Management ", 30, "-")+"\n");
            IO.println("4: View User");
            IO.println("5: Add User");
            IO.println("6: Edit User");
            IO.println("7: Remove User\n");
            IO.println(StringUtils.center(" Other ", 30, "-")+"\n");
            IO.println("0: Return\n");

            String input = scanner.nextLine();
            switch (input.toLowerCase()) {
                case "1":
                    listStudents(scanner);
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    IO.println("Invalid Input. Try Again!");
                    break;
            }
        }
    }

    public static void listStudents(Scanner scanner) {
        // TODO: Implement
        // NOTE: Inefficient code. Look for a better alternative if there is time.

        /*

        +---------------------+-----------+---------------------------+---------------------+
        | Name                | ID        | Email                     | Phone Number        |
        +---------------------+-----------+---------------------------+---------------------+
        | One One             | U0000001  | one.one@student.email     | 07123456789         |
        +---------------------+-----------+---------------------------+---------------------+

         */

        StringBuilder sb = new StringBuilder();
        int nameWidth = 4;
        int idWidth = 7; // Constant. ID's should always be 7 characters.
        int emailWidth = 5;
        int phoneWidth = 11; // Should always be 11 characters
        for (Object studentObj : App.students.values().toArray()) {
            Student student = (Student) studentObj;
            if (student.getName().length() > nameWidth) { nameWidth = student.getName().length(); }
            //if (student.getUserId().length() > idWidth) { idWidth = student.getUserId().length(); }
            if (student.getEmail().length() > emailWidth) { emailWidth = student.getEmail().length(); }
            //if (student.getPhoneNumber().length() > phoneWidth) { phoneWidth = student.getPhoneNumber().length(); }
        }

        sb.append(StringUtils.rightPad("+-", nameWidth, "-") + StringUtils.rightPad("-+-", idWidth, "-") + StringUtils.rightPad("-+-", emailWidth, "-") + StringUtils.rightPad("-+-", phoneWidth, "-") + "-+");
        IO.println(sb.toString());

        IO.println("Press ENTER to continue...");
        scanner.nextLine(); // Waits for a key press to exit
    }
}
