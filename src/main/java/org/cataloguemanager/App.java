package org.cataloguemanager;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import org.cataloguemanager.menus.MainMenu;
import org.cataloguemanager.models.Item;
import org.cataloguemanager.models.Staff;
import org.cataloguemanager.models.Student;
import org.cataloguemanager.models.User;
import org.cataloguemanager.utils.TerminalUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class App {
    public static final HashMap<String, Student> students = new HashMap<>();
    public static final HashMap<String, Staff> staff = new HashMap<>();

    public static final HashMap<String, User> users = new HashMap<>();
    public static final HashMap<String, Item> items = new HashMap<>();
    public static Boolean isUnix = false;
    public static final PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

    static void main(String[] args) {
        // Unix or Windows
        IO.println(System.getProperty("os.name"));
        if (!System.getProperty("os.name").toLowerCase().contains("windows")) {
            isUnix = true;
        }

        boolean saveData = true;

        // Read JSON
        try {
            Files.createFile(Path.of("items.json"));
        } catch (FileAlreadyExistsException e) {
            try (FileReader fileReader = new FileReader(Path.of("items.json").toFile())) {
                JsonObject itemsJson = (JsonObject) Jsoner.deserialize(fileReader);
                for (Object itemObj : (JsonArray) itemsJson.get("items")) {
                    JsonObject itemJson = (JsonObject) itemObj;
                    Item newItem = Item.fromJSON(itemJson);
                    items.put(newItem.getAssetID(), newItem);
                }
            } catch (Exception e1) {
                TerminalUtils.displayLogo();
                IO.println("Unable to read item data. All new changes will be lost.");
                TerminalUtils.sleep(2000);
                saveData = false;
            }
        } catch (IOException e) {
            TerminalUtils.displayLogo();
            IO.println("Unable to read item data. All new changes will be lost.");
            TerminalUtils.sleep(2000);
            saveData = false;
        }
        try {
            Files.createFile(Path.of("users.json"));
        } catch (FileAlreadyExistsException e) {
            try (FileReader fileReaders = new FileReader(Path.of("users.json").toFile())) {
                JsonObject usersJson = (JsonObject) Jsoner.deserialize(fileReaders);
                for (Object userObj : (JsonArray) usersJson.get("users")) {
                    JsonObject userJson = (JsonObject) userObj;
                    User newUser = User.fromJSON(userJson);
                    users.put(newUser.getUserId(), newUser);
                }
            } catch (Exception e1) {
                TerminalUtils.displayLogo();
                IO.println("Unable to read user data. All new changes will be lost.");
                TerminalUtils.sleep(2000);
                saveData = false;
            }
        } catch (IOException e) {
            TerminalUtils.displayLogo();
            IO.println("Unable to read user data. All new changes will be lost.");
            TerminalUtils.sleep(2000);
            saveData = false;
        }

        // Create a shared scanner.
        Scanner scanner = new Scanner(System.in);

        // Run the main menu.`
        MainMenu.run(scanner);

        // Write JSON
        if (!saveData) { return; }

        try {
            JsonObject jsonObject = new JsonObject();
            JsonArray itemsJsonArray =  new JsonArray();

            Item[] itemsArray = new Item[items.size()];
            items.values().toArray(itemsArray);
            Arrays.asList(itemsArray).forEach(item -> {itemsJsonArray.add(item.toJSON());});
            jsonObject.put("items", itemsJsonArray);
            Files.writeString(Path.of("items.json"), Jsoner.serialize(jsonObject));
        } catch (IOException e) {
            TerminalUtils.displayLogo();
            IO.println("Unable to write item data. All changes will be lost...");
            TerminalUtils.sleep(2000);
        }
        try {
            JsonObject jsonObject = new JsonObject();
            JsonArray usersJsonArray =  new JsonArray();

            User[] usersArray = new User[users.size()];
            users.values().toArray(usersArray);
            Arrays.asList(usersArray).forEach(user -> {usersJsonArray.add(user.toJSON());});
            jsonObject.put("users", usersJsonArray);
            Files.writeString(Path.of("users.json"), Jsoner.serialize(jsonObject));
        } catch (IOException e) {
            TerminalUtils.displayLogo();
            IO.println("Unable to write user data. All changes will be lost...");
            TerminalUtils.sleep(2000);
        }
    }
}
