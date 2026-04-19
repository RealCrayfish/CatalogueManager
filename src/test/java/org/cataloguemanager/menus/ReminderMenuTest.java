package org.cataloguemanager.menus;

import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import org.cataloguemanager.App;
import org.cataloguemanager.TestSupport;
import org.cataloguemanager.models.Item;
import org.cataloguemanager.models.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReminderMenuTest {
    @BeforeEach
    void setUp() {
        TestSupport.resetAppState();
    }

    @AfterEach
    void tearDown() {
        TestSupport.resetAppState();
    }

    @Test
    void getReminderCount() throws Exception {
        PhoneNumber phone = App.phoneUtil.parse("07392346180", "GB");
        App.users.put("U123456", new Student("Alice", "U123456", "alice@example.com", phone));

        Item dueToday = new Item("I000001", "Laptop", "Dell", LocalDate.of(2025, 1, 1), "IT");
        dueToday.setAvailability(false);
        dueToday.setBorrowerID("U123456");
        dueToday.setBorrowDate(LocalDate.now().minusDays(1));
        dueToday.setDueDate(LocalDate.now());

        Item overdue = new Item("I000002", "Ariane 6", "Arianespace", LocalDate.of(2025, 1, 1), "Aerospace");
        overdue.setAvailability(false);
        overdue.setBorrowerID("U123456");
        overdue.setBorrowDate(LocalDate.now().minusDays(3));
        overdue.setDueDate(LocalDate.now().minusDays(1));

        Item available = new Item("I000003", "Book", "Penguin", LocalDate.of(2025, 1, 1), "Text");

        App.items.put(dueToday.getAssetID(), dueToday);
        App.items.put(overdue.getAssetID(), overdue);
        App.items.put(available.getAssetID(), available);

        assertEquals(2, ReminderMenu.getReminderCount());
    }
}

