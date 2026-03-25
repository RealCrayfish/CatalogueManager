package org.cataloguemanager;

import org.cataloguemanager.menus.MainMenu;
import org.cataloguemanager.models.Item;
import org.cataloguemanager.models.Staff;
import org.cataloguemanager.models.Student;

import java.util.HashMap;
import java.util.Scanner;

public class App {
    public static final HashMap<String, Student> students = new HashMap<>();
    public static final HashMap<String, Staff> staff = new HashMap<>();
    public static final HashMap<String, Item> items = new HashMap<>();
    public static Boolean isUnix = false;

    public static void main(String[] args) {
        IO.println(System.getProperty("os.name"));
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            isUnix = false;
        } else { isUnix = true; }


        // FIXME: Remove!!! This is for implementation testing only!!!!
        students.put("S123456", new Student("Big One", "S123456", "big.one1@unimail.hud.ac.uk", "07123456789"));


        // Create a shared scanner.
        Scanner scanner = new Scanner(System.in);

        // Run the main menu.`
        MainMenu.run(scanner);
    }
}
