package org.cataloguemanager.menus;

import org.cataloguemanager.App;
import org.cataloguemanager.utils.TerminalUtils;

import java.util.Scanner;

public class MainMenu {
    public static void run(Scanner scanner) {
        int reminders;
        boolean running = true;
        while (running) {
            TerminalUtils.displayLogo();

            reminders = ReminderMenu.getReminderCount();
            if (reminders > 0) {
                IO.println("You have "+reminders+" reminder(s)\n");
            }

            IO.println("1: Items");
            IO.println("2: Users");
            IO.println("3: Reminders");
            IO.println("0: Quit");
            IO.println("");


            String mainMenuInput = scanner.nextLine();
            switch (mainMenuInput.toLowerCase()) {
                case "1":
                    ItemMenu.run(scanner);
                    break;
                case "2":
                    UsersMenu.run(scanner);
                    break;
                case "3":
                    ReminderMenu.run(scanner);
                    break;
                case "0":
                    IO.println("Exiting...");
                    TerminalUtils.sleep(2000);
                    running = false;
                    break;
                default:
                    IO.println("Invalid Input. Try Again!");
                    TerminalUtils.sleep(2000);
                    break;
            }
        }
    }
}
