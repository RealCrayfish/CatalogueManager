package org.cataloguemanager.menus;

import org.apache.commons.lang3.StringUtils;
import org.cataloguemanager.App;
import org.cataloguemanager.models.Item;
import org.cataloguemanager.utils.TerminalUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class ReminderMenu {
    public static void run(Scanner scanner) {
        Object[] itemObjs = App.items.values().toArray();
        String[][] itemsDueToday = new String[itemObjs.length][];
        String[][] itemsOverdue = new String[itemObjs.length][];

        for (int i = 0; i < itemObjs.length; i++) {
            if (itemObjs[i] instanceof Item item) {
                if (!item.getAvailability() && LocalDate.now().isAfter(item.getDueDate())) {
                    itemsOverdue[i] = item.toArray();
                }
                else if (!item.getAvailability() && LocalDate.now().equals(item.getDueDate())) {
                    itemsDueToday[i] = item.toArray();
                }
            }
        }

        TerminalUtils.displayLogo();
        IO.println(StringUtils.center(" Items Due Today: ", 30, "-")+"\n");
        IO.println(TerminalUtils.tableBuilder(new String[]{"ID", "Name ", "Manufacturer", "Acquisition Date", "Category", "Available", "Borrower", "Borrow Date", "Due Date"}, itemsDueToday));
        IO.println("\n"+StringUtils.center(" Items Overdue: ", 30, "-")+"\n");
        IO.println(TerminalUtils.tableBuilder(new String[]{"ID", "Name ", "Manufacturer", "Acquisition Date", "Category", "Available", "Borrower", "Borrow Date", "Due Date"}, itemsOverdue));
        TerminalUtils.hold(scanner);
    }

    public static int getReminderCount() {
        int reminderCount = 0;
        for (Object itemObj : App.items.values().toArray()) {
            if (itemObj instanceof Item item) {
                int yum = LocalDate.now().until(item.getDueDate()).getDays();
                if (!item.getAvailability() && LocalDate.now().until(item.getDueDate()).getDays() <= 0) {
                    reminderCount++; // Increment if any item is due today or is overdue.
                }
            }
        }
        return reminderCount;
    }
}
