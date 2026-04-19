package org.cataloguemanager.utils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import org.apache.commons.lang3.StringUtils;
import org.cataloguemanager.App;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.function.Predicate;

public class TerminalUtils {

    /**
     * Replacement for Thread.sleep() to include exception handling.
     * @param milliseconds The number of milliseconds to sleep for.
     */
    public static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            IO.println("Interrupt Detected.");
        }
    }

    /**
     * Clears the terminal or prints whitespace.
     */
    public static void clearTerminal()  {
        try {
            if (App.isUnix) {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
        } catch (IOException e) {
            IO.println("\n\n"); // If unable to clear, print empty lines instead.
        }
        catch (InterruptedException e) {
            IO.println("Interrupt Detected.");
        }
    }

    /**
     * Displays the app's logo.
     */
    public static void displayLogo() {
        clearTerminal();
        IO.println("\n  ___      _        _                    __  __                             ");
        IO.println(" / __|__ _| |_ __ _| |___  __ _ _  _ ___|  \\/  |__ _ _ _  __ _ __ _ ___ _ _ ");
        IO.println("| (__/ _` |  _/ _` | / _ \\/ _` | || / -_) |\\/| / _` | ' \\/ _` / _` / -_) '_|");
        IO.println(" \\___\\__,_|\\__\\__,_|_\\___/\\__, |\\_,_\\___|_|  |_\\__,_|_||_\\__,_\\__, \\___|_|  ");
        IO.println("                          |___/                               |___/         \n\n");
    }

    /**
     * Creates a stylised table.
     * @param columnHeaders The table column headers.
     * @param records The individual records of the table.
     * @return The stylised table as a string.
     */
    public static String tableBuilder(String[] columnHeaders, String[][] records) {

        StringBuilder sb = new StringBuilder();
        int[] columnWidths = new int[columnHeaders.length];

        for (int i = 0; i < columnHeaders.length; i++) {
            columnWidths[i] = columnHeaders[i].length();
        }
        for (String[] record : records) {
            if (record == null || record.length == 0) { continue; }
            for (int j = 0; j < record.length; j++) {
                if (record[j].length() > columnWidths[j]) {
                    columnWidths[j] = record[j].length();
                }
            }
        }

        String splitter = "+";
        for (int columnWidth : columnWidths) {
            splitter = splitter.concat(StringUtils.repeat('-', columnWidth+2)).concat("+");
        }

        sb.append(splitter);

        sb.append("\n| ");
        for (int i = 0; i < columnHeaders.length; i++) {
            sb.append(StringUtils.rightPad(columnHeaders[i], columnWidths[i]+1, ' ')).append("| ");
        }

        sb.append("\n").append(splitter);

        // Records
        for (String[] record : records) {
            if (record == null || record.length == 0) { continue; }
            sb.append("\n| ");
            for (int j = 0; j < record.length; j++) {
                sb.append(StringUtils.rightPad(record[j], columnWidths[j] + 1, ' ')).append("| ");
            }
        }

        sb.append("\n").append(splitter);

        sb.append("\n");
        return sb.toString();
    }

    /**
     * Creates a prompt which repeats until it recieves a valid input.
     * @param scanner The shared scanner.
     * @param prompt The prompt text.
     * @param validator The validator.
     * @param invalidMessage The invalid message.
     * @return The return string.
     */
    public static String prompt(Scanner scanner, String prompt, Predicate<String> validator, String invalidMessage) {
        while (true) {
            TerminalUtils.displayLogo();
            IO.print(prompt);
            String input = scanner.nextLine().strip();
            if (validator.test(input)) {
                return input.strip();
            }
            IO.println(invalidMessage);
            TerminalUtils.sleep(2000);
        }
    }

    /**
     * Creates a prompt for an ID.
     * @param scanner The shared scanner.
     * @param prompt The prompt text.
     * @param itemID Whether it is an Item ID or User ID.
     * @param allowCancel Whether this prompt can be cancelled.
     * @return The new ID.
     */
    public static String promptID(Scanner scanner, String prompt, boolean itemID, boolean allowCancel) {
        String input = TerminalUtils.prompt(
                scanner,
                ( (allowCancel) ? prompt+" (0 to cancel): " : prompt+": " ),
                value -> {
                    if (value.strip().equals("0") && allowCancel) { return true; }
                    return ((itemID) ? App.items.containsKey(value.toUpperCase().strip()) : App.users.containsKey(value.toUpperCase().strip()));
                },
                "Invalid ID. Try again!"
        );
        return input.toUpperCase().strip();
    }

    /**
     * Creates a prompt for a new ID.
     * @param scanner The shared scanner.
     * @param prompt The prompt text.
     * @param item Whether it is an Item ID or User ID.
     * @return The new ID.
     */
    public static String promptNewID(Scanner scanner, String prompt, boolean item) {
        while (true) {
            TerminalUtils.displayLogo();
            if (item) {
                IO.print(prompt + " (I000000): ");
                String input = scanner.nextLine().strip();
                if (input.matches("^I\\d{6}$") && !App.items.containsKey(input.toUpperCase().strip())) {
                    return input;
                }
            }
            else {
                IO.print(prompt + " (U000000): ");
                String input = scanner.nextLine().strip();
                if (input.matches("^U\\d{6}$") && !App.users.containsKey(input.toUpperCase().strip())) {
                    return input;
                }
            }
            IO.println("Invalid ID.");
            TerminalUtils.sleep(2000);
        }
    }

    /**
     * Creates a prompt for a date.
     * @param scanner The shared scanner.
     * @param prompt The prompt text.
     * @return The date.
     */
    public static LocalDate promptDate(Scanner scanner, String prompt) {
        while (true) {
            TerminalUtils.displayLogo();
            IO.print(prompt + " (yyyy-MM-dd or leave blank for today): ");
            String input = scanner.nextLine().strip();
            if (input.isBlank()) { return LocalDate.now(); }
            try {
                return LocalDate.parse(input.strip());
            } catch (DateTimeParseException e) {
                IO.println("Invalid date. Use yyyy-MM-dd.");
                TerminalUtils.sleep(2000);
            }
        }
    }

    /**
     * Creates a prompt for an email.
     * @param scanner The shared scanner.
     * @param prompt The prompt text.
     * @return The email.
     */
    public static String promptEmail(Scanner scanner, String prompt) {
        while (true) {
            TerminalUtils.displayLogo();
            IO.print(prompt + " (example@email.com): ");
            String input = scanner.nextLine().strip();
            // Regex pattern acquired from https://emailregex.com/
            if (input.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
                return input;
            }
            IO.println("Invalid email address.");
            TerminalUtils.sleep(2000);
        }
    }

    /**
     * Creates a prompt for a phone number.
     * @param scanner The shared scanner.
     * @param prompt The prompt text.
     * @return The phone number.
     */
    public static PhoneNumber promptPhoneNumber(Scanner scanner, String prompt) {
        while (true) {
            TerminalUtils.displayLogo();
            IO.print(prompt + " ("+App.phoneUtil.format(App.phoneUtil.getExampleNumber("GB"), PhoneNumberUtil.PhoneNumberFormat.NATIONAL)+"): ");
            String input = scanner.nextLine().strip();
            try {
                return App.phoneUtil.parse(input, "GB");
            } catch (NumberParseException e) {
                IO.println("Invalid phone number.");
                TerminalUtils.sleep(2000);
            }
        }
    }

    /**
     * Creates a confirmation prompt.
     * @param scanner The shared scanner.
     * @return Confirmation Boolean.
     */
    public static boolean promptConfirm(Scanner scanner) {
        String confirm = TerminalUtils.prompt(
                scanner,
                "1: Confirm\n2: Cancel\n\nEnter Selection: ",
                value -> (value.strip().equals("1") || value.strip().equals("2")),
                "Invalid Input. Try Again!"
        );
        return confirm.equals("1");
    }

    /**
     * Holds the program until enter is pressed.
     * @param scanner The shared scanner
     */
    public static void hold(Scanner scanner) {
        IO.println("\nPress ENTER to continue...");
        scanner.nextLine();
    }
}
