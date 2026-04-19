package org.cataloguemanager.menus;

import org.apache.commons.lang3.StringUtils;
import org.cataloguemanager.App;
import org.cataloguemanager.models.Item;
import org.cataloguemanager.utils.TerminalUtils;

import java.time.LocalDate;
import java.util.Scanner;

public class ItemMenu {
    public static void run(Scanner scanner) {
        // TODO: Implement

        boolean running = true;
        while (running) {
            TerminalUtils.displayLogo();
            IO.println("1: Search Items");
            IO.println("2: List All Items");
            IO.println("3: View Item");
            IO.println("4: Add Item");
            IO.println("0: Close");
            IO.println("");

            String itemMenuInput = scanner.nextLine();
            switch (itemMenuInput.toLowerCase()) {
                case "1":
                    listItems(scanner, true);
                    break;
                case "2":
                    listItems(scanner, false);
                    break;
                case "3":
                    // Prompt with validation built in! So cool 😎
                    String displayItemInputID = TerminalUtils.promptID(scanner, "Enter an item ID", true, true);
                    if (displayItemInputID.equals("0")) {
                        break;
                    }
                    viewItem(scanner, displayItemInputID);
                    break;
                case "4":
                    addItem(scanner);
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    IO.println("Invalid Input. Try Again!");
                    TerminalUtils.sleep(2000);
                    break;
            }
        }
    }

    public static void listItems(Scanner scanner, boolean search) {
        String itemNameInput = "";
        if (search) {
            TerminalUtils.displayLogo();
            IO.print("Enter an Item Name: ");
            itemNameInput = scanner.nextLine();
        }

        Object[] itemsObj = App.items.values().toArray();
        String[][] records = new String[itemsObj.length][];
        for (int i = 0; i < itemsObj.length; i++) {
            Item item = (Item) itemsObj[i];
            if (!search) { records[i] = item.toArray(); continue; }

            if (item.getName().toLowerCase().contains(itemNameInput.toLowerCase())) {
                records[i] = item.toArray();
            }
        }

        String listItemsInput = TerminalUtils.promptID(
                scanner,
                StringUtils.center(" Items: ", 30, "-")+ "\n"
                        +TerminalUtils.tableBuilder(new String[]{"ID", "Name ", "Manufacturer", "Acquisition Date", "Category", "Available", "Borrower", "Borrow Date", "Due Date"}, records)
                        +"Enter an ID to view that item",
                true,
                true

        );

        // 0 to cancel
        if (listItemsInput.equals("0")) { return; }

        // Display valid IDs
        viewItem(scanner, listItemsInput);
    }

    public static void viewItem(Scanner scanner, String itemID) {
        Item item = App.items.get(itemID);

        boolean running = true;
        while (running) {
            TerminalUtils.displayLogo();

            IO.println();
            IO.println(TerminalUtils.tableBuilder(new String[]{"ID", "Name ", "Manufacturer", "Acquisition Date", "Category", "Available", "Borrower", "Borrow Date", "Due Date"}, new String[][]{ item.toArray() }));


            if (item.getAvailability()) {
                IO.println("1: Borrow Item");
            } else {
                IO.println("1: Return Item");
            }
            IO.println("2: Edit Item");
            IO.println("3: Delete Item");
            IO.println("0: Close");
            IO.println("");


            String mainMenuInput = scanner.nextLine();
            switch (mainMenuInput.toLowerCase()) {
                case "1":
                    if (item.getAvailability()) {
                        item.borrow(scanner);
                    } else {
                        item.returnItem(scanner);
                    }
                    break;
                case "2":
                    editItemMenu(scanner, itemID);
                    break;
                case "3":
                    App.items.remove(itemID);
                    running = false;
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    IO.println("Invalid Input. Try Again!");
                    TerminalUtils.sleep(2000);
                    break;
            }
        }
    }

    public static void addItem(Scanner scanner) {
        String newID = TerminalUtils.promptNewID(
                scanner,
                "Enter the ID",
                true
        );
        if (newID.equals("0")) { return; }

        String newName = TerminalUtils.prompt(
                scanner,
                "Enter the Name: ",
                value -> (!value.isBlank()),
                "Invalid Input. Try Again!"
        );

        String newManufacturer = TerminalUtils.prompt(
                scanner,
                "Enter the Manufacturer: ",
                value -> (!value.isBlank()),
                "Invalid Input. Try Again!"
        );

        LocalDate newAcquisitionDate = TerminalUtils.promptDate(scanner, "Enter the acquisition date");

        String newCategory = TerminalUtils.prompt(
                scanner,
                "Enter the Category: ",
                value -> (!value.isBlank()),
                "Invalid Input. Try Again!"
        );

        if (!TerminalUtils.promptConfirm(scanner)) return;


        App.items.put(newID, new Item(
                newID,
                newName,
                newManufacturer,
                newAcquisitionDate,
                newCategory
        ));
    }

    public static void  editItemMenu(Scanner scanner, String itemID) {
        Item item = App.items.get(itemID);

        boolean running = true;
        while (running) {
            TerminalUtils.displayLogo();

            IO.println();
            IO.println(TerminalUtils.tableBuilder(new String[]{"ID", "Name ", "Manufacturer", "Acquisition Date", "Category", "Available", "Borrower", "Borrow Date", "Due Date"}, new String[][]{item.toArray()}));

            IO.println("1: Change Item Name");
            IO.println("2: Change Item Manufacturer");
            IO.println("3: Change Item Acquisition Date");
            IO.println("4: Change Item Category");
            IO.println("0: Close");
            IO.println("");


            String editItemMenuInput = scanner.nextLine();
            switch (editItemMenuInput.toLowerCase()) {
                case "1":
                    item.setName(TerminalUtils.prompt(
                            scanner,
                            "The current name is: " + item.getName() + "\nEnter the new Name: ",
                            value -> (!value.isBlank()),
                            "Invalid Input. Try Again!"
                    ));
                    break;
                case "2":
                    item.setManufacturer(TerminalUtils.prompt(
                            scanner,
                            "The current Manufacturer is: " + item.getManufacturer() + "\nEnter the new Manufacturer: ",
                            value -> (!value.isBlank()),
                            "Invalid Input. Try Again!"
                    ));
                    break;
                case "3":
                    item.setAcquisitionDate(TerminalUtils.promptDate(scanner, "The current acquisition date is: " + item.getAcquisitionDate() + "\nEnter the new acquisition date"));
                    break;
                case "4":
                    item.setCategory(TerminalUtils.prompt(
                            scanner,
                            "The current category is: " + item.getCategory() + "\nEnter the Category: ",
                            value -> (!value.isBlank()),
                            "Invalid Input. Try Again!"
                    ));
                    break;
                case "0":
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
