package org.cataloguemanager.models;

import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import org.cataloguemanager.App;
import org.cataloguemanager.TestSupport;
import org.cataloguemanager.menus.ItemMenu;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ItemTest {
    @BeforeEach
    void setUp() {
        TestSupport.resetAppState();
    }

    @AfterEach
    void tearDown() {
        TestSupport.resetAppState();
    }

    @Test
    void constructorAndGettersAndSettersWork() {
        Item item = new Item("I000001", "Ariane 6", "Arianespace", LocalDate.of(2025, 1, 1), "Aerospace");

        item.setAssetID("I000002");
        item.setName("Ariane 5");
        item.setManufacturer("Arianespace New");
        item.setAcquisitionDate(LocalDate.of(2025, 1, 2));
        item.setCategory("Aerospace Test");
        item.setAvailability(false);

        assertEquals("I000002", item.getAssetID());
        assertEquals("Ariane 5", item.getName());
        assertEquals("Arianespace New", item.getManufacturer());
        assertEquals(LocalDate.of(2025, 1, 2), item.getAcquisitionDate());
        assertEquals("Aerospace Test", item.getCategory());
        assertFalse(item.getAvailability());

    }

    @Test
    void borrowAndReturnItem() throws Exception {
        PhoneNumber phone = App.phoneUtil.parse("07700900000", "GB");
        App.users.put("U123456", new Student("Alice", "U123456", "alice@example.com", phone));

        Item item = new Item("I000001", "Laptop", "Dell", LocalDate.of(2025, 1, 2), "IT");

        item.borrow(TestSupport.scannerOf(
                "U123456",
                "2025-01-03",
                "2025-01-08",
                ""
        ));

        assertFalse(item.getAvailability());
        assertEquals("U123456", item.getBorrowerID());
        assertEquals(LocalDate.of(2025, 1, 3), item.getBorrowDate());
        assertEquals(LocalDate.of(2025, 1, 8), item.getDueDate());

        item.returnItem(TestSupport.scannerOf(
                "2025-01-09",
                ""
        ));

        assertTrue(item.getAvailability());
    }

    @Test
    void editItem() throws Exception {
        Item item = new Item("I000001", "Ariane 6", "Arianespace", LocalDate.of(2025, 1, 1), "Aerospace");
        App.items.put("I000001", item);

        ItemMenu.editItemMenu(TestSupport.scannerOf(
                "1",
                "Ariane 5",
                "2",
                "Arianespace New",
                "3",
                "2025-01-02",
                "4",
                "Aerospace Test",
                "0"
        ), "I000001");

        assertEquals("Ariane 5", item.getName());
        assertEquals("Arianespace New", item.getManufacturer());
        assertEquals(LocalDate.of(2025, 1, 2), item.getAcquisitionDate());
        assertEquals("Aerospace Test", item.getCategory());
    }
}

