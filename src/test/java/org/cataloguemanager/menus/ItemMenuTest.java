package org.cataloguemanager.menus;

import org.cataloguemanager.App;
import org.cataloguemanager.TestSupport;
import org.cataloguemanager.models.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ItemMenuTest {
    @BeforeEach
    void setUp() {
        TestSupport.resetAppState();
    }

    @AfterEach
    void tearDown() {
        TestSupport.resetAppState();
    }

    @Test
    void addItemCreates() {
        ItemMenu.addItem(TestSupport.scannerOf(
                "I000001",
                "Laptop",
                "Dell",
                "2025-01-01",
                "IT",
                "1"
        ));

        assertTrue(App.items.containsKey("I000001"));
        Item item = App.items.get("I000001");
        assertEquals("Laptop", item.getName());
        assertEquals("Dell", item.getManufacturer());
        assertEquals(LocalDate.of(2025, 1, 1), item.getAcquisitionDate());
        assertEquals("IT", item.getCategory());
        assertTrue(item.getAvailability());
    }

    @Test
    void viewItemDeletes() {
        App.items.put("I000001", new Item("I000001", "Laptop", "Dell", LocalDate.of(2025, 1, 1), "IT"));

        ItemMenu.viewItem(TestSupport.scannerOf("3"), "I000001");

        assertFalse(App.items.containsKey("I000001"));
    }
}

