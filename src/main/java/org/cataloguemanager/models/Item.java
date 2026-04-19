package org.cataloguemanager.models;

import com.github.cliftonlabs.json_simple.JsonObject;
import org.cataloguemanager.App;
import org.cataloguemanager.utils.TerminalUtils;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Item {
    private String assetID;
    private String name;
    private String manufacturer;
    private LocalDate acquisitionDate;
    private String category;
    private boolean isAvailable;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private String borrowerID;

    public Item(String assetID, String name, String manufacturer, LocalDate acquisitionDate, String category) {
        this.assetID = assetID;
        this.name = name;
        this.manufacturer = manufacturer;
        this.acquisitionDate = acquisitionDate;
        this.category = category;
        this.isAvailable = true;
        this.borrowDate = null;
        this.dueDate = null;
        this.borrowerID = "";
    }

    public Item() {
        this.isAvailable = true;
        this.borrowDate = null;
        this.dueDate = null;
        this.borrowerID = "";
    }

    public String getAssetID() { return this.assetID; }
    public void setAssetID(String assetID) { this.assetID = assetID; } // Validated at input via the prompt methods.

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    } // Validated at input via the prompt methods.

    public String getManufacturer() {
        return this.manufacturer;
    }
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    } // Validated at input via the prompt methods.

    public LocalDate getAcquisitionDate() {
        if (this.acquisitionDate == null) {
            return LocalDate.of(0, 1, 1);
        }
        return this.acquisitionDate;
    }
    public void setAcquisitionDate(LocalDate date) {
        this.acquisitionDate = date;
    } // Validated at input via the prompt methods.

    public String getCategory() {
        return this.category;
    }
    public void setCategory(String category) {
        this.category = category;
    } // Validated at input via the prompt methods.

    public Boolean getAvailability() {
        return this.isAvailable;
    }
    public void setAvailability(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getBorrowerID() { return this.borrowerID; }
    public void setBorrowerID(String borrowerID) { this.borrowerID = borrowerID; } // Validated at input via the prompt methods.

    public LocalDate getBorrowDate() {
        if (this.borrowDate == null) {
            return LocalDate.of(0, 1, 1);
        }
        return this.borrowDate;
    }
    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    } // Validated at input via the prompt methods.

    public LocalDate getDueDate() {
        if (this.dueDate == null) {
            return LocalDate.of(0, 1, 1);
        }
        return this.dueDate;
    }
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    } // Validated at input via the prompt methods.

    /**
     * Returns the item as an array of Strings. This should only be used alongside tableBuilder.
     * @return The item as an array of Strings.
     */
    public String[] toArray() {
        String[] array = new String[9];
        array[0] = this.getAssetID();
        array[1] = this.getName();
        array[2] = this.getManufacturer();
        array[3] = this.getAcquisitionDate().toString();
        array[4] = this.getCategory();

        array[5] = this.getAvailability() ? "T" : "F";
        if (this.getAvailability() == true) {
            array[6] = "";
            array[7] = "";
            array[8] = "";
        }
        else {
            array[6] = this.getBorrowerID();
            array[7] = this.getBorrowDate().toString();
            array[8] = this.getDueDate().toString();
        }
        return array;
    }

    public void borrow(Scanner scanner) {
        String borrowingString = "Borrowing:\n" + TerminalUtils.tableBuilder(new String[]{"ID", "Name ", "Manufacturer", "Acquisition Date", "Category", "Available", "Borrower", "Borrow Date", "Due Date"}, new String[][]{ this.toArray() }) + "\n";
        TerminalUtils.displayLogo();

        String id = TerminalUtils.promptID(scanner, borrowingString+"Enter User ID", false, true);
        if (id.equals("0")) {
            return;
        }

        LocalDate borrowDate = TerminalUtils.promptDate(scanner, borrowingString+"Enter the borrow date");
        LocalDate returnDate = TerminalUtils.promptDate(scanner, borrowingString+"Enter the return date");

        IO.println("\nNotice: There is an overdue charge of £2 per day not returned.\n");
        TerminalUtils.hold(scanner);

        this.borrowerID = id;
        this.isAvailable = false;
        this.borrowDate = borrowDate;
        this.dueDate = returnDate;
    }

    public void returnItem(Scanner scanner) {
        String borrowingString = "Returning:\n" + TerminalUtils.tableBuilder(new String[]{"ID", "Name ", "Manufacturer", "Acquisition Date", "Category", "Available", "Borrower", "Borrow Date", "Due Date"}, new String[][]{ this.toArray() }) + "\n";
        LocalDate returnedDate = TerminalUtils.promptDate(scanner, borrowingString+"Enter the returned date");

        if (returnedDate.isAfter(this.dueDate)) {
            TerminalUtils.displayLogo();
            IO.println();
            IO.print("Returned after the due date. The default overdue fee is: £");
            IO.println(this.dueDate.until(LocalDate.now()).getDays()*2); // Overdue fee is £2 per day not returned.
            TerminalUtils.hold(scanner);
        }

        this.isAvailable = true;
        this.borrowDate = null;
        this.dueDate = null;
        this.borrowerID = "";
    }



    public static Item fromJSON(JsonObject jsonObject) {
        Item item = new Item();
        item.setAssetID((String)jsonObject.get("assetID"));
        item.setName((String)jsonObject.get("name"));
        item.setManufacturer((String)jsonObject.get("manufacturer"));
        item.setAcquisitionDate(LocalDate.parse((String)jsonObject.get("acquisitionDate")));
        item.setCategory((String)jsonObject.get("category"));
        item.setAvailability((Boolean)jsonObject.get("available"));
        item.setBorrowerID((String)jsonObject.get("borrowerID"));
        item.setBorrowDate(LocalDate.parse((String)jsonObject.get("borrowDate")));
        item.setDueDate(LocalDate.parse((String)jsonObject.get("dueDate")));
        return item;
    }

    public JsonObject toJSON() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.put("assetID", this.getAssetID());
        jsonObject.put("name", this.getName());
        jsonObject.put("manufacturer", this.getManufacturer());
        jsonObject.put("acquisitionDate", this.getAcquisitionDate().toString());
        jsonObject.put("category", this.getCategory());
        jsonObject.put("available", this.getAvailability());
        jsonObject.put("borrowerID", this.getBorrowerID());
        jsonObject.put("borrowDate", this.getBorrowDate().toString());
        jsonObject.put("dueDate", this.getDueDate().toString());
        return jsonObject;
    }
}
