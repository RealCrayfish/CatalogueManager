package org.cataloguemanager.menus;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import org.cataloguemanager.App;
import org.cataloguemanager.TestSupport;
import org.cataloguemanager.models.Staff;
import org.cataloguemanager.models.Student;
import org.cataloguemanager.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UsersMenuTest {
    @BeforeEach
    void setUp() {
        TestSupport.resetAppState();
    }

    @AfterEach
    void tearDown() {
        TestSupport.resetAppState();
    }

    @Test
    void addUserCreatesStudent() {
        UsersMenu.addUser(TestSupport.scannerOf(
                "1",
                "U123456",
                "Alice",
                "alice@example.com",
                "07700900000",
                "1"
        ));

        User user = App.users.get("U123456");
        assertInstanceOf(Student.class, user);
        assertEquals("Alice", user.getName());
        assertEquals("alice@example.com", user.getEmail());
    }

    @Test
    void addUserCreatesStaff() {
        UsersMenu.addUser(TestSupport.scannerOf(
                "2",
                "U123456",
                "Bob",
                "bob@example.com",
                "07700900000",
                "1"
        ));

        User user = App.users.get("U123456");
        assertInstanceOf(Staff.class, user);
        assertEquals("Bob", user.getName());
        assertEquals("bob@example.com", user.getEmail());
    }

    @Test
    void viewUserEdits() throws Exception {
        PhoneNumber phone = App.phoneUtil.parse("07700900000", "GB");
        Student user = new Student("Bob", "U123456", "bob@example.com", phone);
        App.users.put("U123456", user);

        UsersMenu.viewUser(TestSupport.scannerOf(
                "1",
                "Alice",
                "alice@example.com",
                "07700900009",
                "1",
                "0"
        ), "U123456");

        assertEquals("Alice", user.getName());
        assertEquals("alice@example.com", user.getEmail());
        assertEquals(App.phoneUtil.format(App.phoneUtil.parse("07700900009", "GB"), PhoneNumberUtil.PhoneNumberFormat.NATIONAL), user.getPhoneNumberString());
        assertTrue(App.users.containsKey("U123456"));
    }

    @Test
    void viewUserDeleteRecord() throws Exception {
        PhoneNumber phone = App.phoneUtil.parse("07700900000", "GB");
        App.users.put("U123456", new Student("Alice", "U123456", "alice@example.com", phone));

        UsersMenu.viewUser(TestSupport.scannerOf(
                "2",
                "1"
        ), "U123456");

        assertTrue(App.users.isEmpty());
    }
}

