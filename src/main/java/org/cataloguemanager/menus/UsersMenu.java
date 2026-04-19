package org.cataloguemanager.menus;

import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import org.apache.commons.lang3.StringUtils;
import org.cataloguemanager.App;
import org.cataloguemanager.models.Item;
import org.cataloguemanager.models.Staff;
import org.cataloguemanager.models.Student;
import org.cataloguemanager.models.User;
import org.cataloguemanager.utils.TerminalUtils;

import java.util.Scanner;

public class UsersMenu {
    public static void run(Scanner scanner) {
        scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            TerminalUtils.displayLogo();
            IO.println("1: List All Users");
            IO.println("2: List Students");
            IO.println("3: List Staff\n");
            IO.println("4: View User");
            IO.println("5: Add User");
            IO.println("0: Close\n");

            String input = scanner.nextLine();
            switch (input.toLowerCase()) {
                case "1":
                    String listUsersInput = TerminalUtils.promptID(
                            scanner,
                            listStudents()+"\n"+listStaff()+"\nEnter an ID to view that User",
                            false,
                            true
                    );

                    if (listUsersInput.equals("0")) { break; }
                    viewUser(scanner, listUsersInput);
                    break;
                case "2":
                    listStudents(scanner);
                    break;
                case "3":
                    listStaff(scanner);
                    break;
                case "4":
                    String vierUserInput = TerminalUtils.promptID(
                            scanner,
                            "Enter an ID to view that User",
                            false,
                            true
                    );
                    if (vierUserInput.equals("0")) { return; }
                    viewUser(scanner, vierUserInput);
                    break;
                case "5":
                    addUser(scanner);
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
        String listStudentsInput = TerminalUtils.promptID(
                scanner,
                listStudents()+"Enter an ID to view that User",
                false,
                true
        );
        if (listStudentsInput.equals("0")) { return; }
        viewUser(scanner, listStudentsInput);
    }

    public static String listStudents() {
        Object[] studentObjs = App.users.values().toArray();
        String[][] records = new String[studentObjs.length][];
        for (int i = 0; i < studentObjs.length; i++) {
            if (studentObjs[i] instanceof Student student) {
                records[i] = student.toArray();
            }
        }
        return StringUtils.center(" Students: ", 30, "-")+"\n" + TerminalUtils.tableBuilder(new String[]{"Name", "ID", "Email", "Phone Number"}, records);
    }

    public static void listStaff(Scanner scanner) {
        String listStaffInput = TerminalUtils.promptID(
                scanner,
                listStaff()+"Enter an ID to view that User",
                false,
                true
        );
        if (listStaffInput.equals("0")) { return; }
        viewUser(scanner, listStaffInput);
    }

    public static String listStaff() {
        Object[] staffObjs = App.users.values().toArray();
        String[][] records = new String[staffObjs.length][];
        for (int i = 0; i < staffObjs.length; i++) {
            if (staffObjs[i] instanceof Staff staff) {
                records[i] = staff.toArray();
            }
        }
        return StringUtils.center(" Staff: ", 30, "-")+"\n" + TerminalUtils.tableBuilder(new String[]{"Name", "ID", "Email", "Phone Number"}, records);
    }

    public static void viewUser(Scanner scanner, String userID) {
        User user = App.users.get(userID);

        while (true) {
            TerminalUtils.displayLogo();

            IO.println();
            IO.println(TerminalUtils.tableBuilder(new String[]{"Name", "ID", "Email", "Phone Number"}, new String[][]{ user.toArray() }));
            IO.println("1: Edit User");
            IO.println("2: Delete User");
            IO.println("0: Close");
            IO.println("");

            String mainMenuInput = scanner.nextLine();
            switch (mainMenuInput.toLowerCase()) {
                case "1":
                    // Edit
                    String newName = TerminalUtils.prompt(
                            scanner,
                            "The current name is:"+user.getName()+"\nEnter the Name",
                            value -> (!value.isBlank()),
                            "Invalid Input. Try Again!"
                    );

                    String newEmail = TerminalUtils.promptEmail(
                            scanner,
                            "The current email is: "+user.getEmail()+"\nEnter the new Email"
                    );

                    PhoneNumber newPhoneNumber = TerminalUtils.promptPhoneNumber(
                            scanner,
                            "The current phone number is: "+user.getPhoneNumberString()+"\nEnter the new Phone Number"
                    );

                    if (!TerminalUtils.promptConfirm(scanner)) return;

                    user.setName(newName);
                    user.setEmail(newEmail);
                    user.setPhoneNumber(newPhoneNumber);
                    break;
                case "2":
                    if (!TerminalUtils.promptConfirm(scanner)) return;
                    App.users.remove(userID);
                    return;
                case "0":
                    return;
                default:
                    IO.println("Invalid Input. Try Again!");
                    TerminalUtils.sleep(2000);
                    break;
            }
        }
    }

    public static void addUser(Scanner scanner) {
        String confirm = TerminalUtils.prompt(
                scanner,
                "1: New Student\n2: New Staff\n0: Cancel\n\nEnter Selection: ",
                value -> (value.strip().equals("1") || value.strip().equals("2") ||  value.strip().equals("0")),
                "Invalid Input. Try Again!"
        );
        if (confirm.equals("0")) { return; }

        String newID = TerminalUtils.promptNewID(
                scanner,
                "Enter the ID",
                false
        );

        String newName = TerminalUtils.prompt(
                scanner,
                "Enter the Name: ",
                value -> (!value.isBlank()),
                "Invalid Input. Try Again!"
        );

        String newEmail = TerminalUtils.promptEmail(
                scanner,
                "Enter the Email"
        );

        PhoneNumber newPhoneNumber = TerminalUtils.promptPhoneNumber(
                scanner,
                "Enter the Phone Number"
        );

        if (!TerminalUtils.promptConfirm(scanner)) return;

        if (confirm.equals("1")) {
            App.users.put(newID, new Student(newName, newID, newEmail, newPhoneNumber));
        }
        else {
            App.users.put(newID, new Staff(newName, newID, newEmail, newPhoneNumber));
        }

    }
}
