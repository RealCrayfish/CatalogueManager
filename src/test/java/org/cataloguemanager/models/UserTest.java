package org.cataloguemanager.models;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import org.cataloguemanager.App;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class UserTest {
    @Test
    void constructorSettersWork() throws Exception {
        PhoneNumber phone = App.phoneUtil.parse("07700900000", "GB");
        User user = new User("Alice", "U123456", "alice@example.com", phone);

        assertEquals("Alice", user.getName());
        assertEquals("U123456", user.getUserId());
        assertEquals("alice@example.com", user.getEmail());
        assertSame(phone, user.getPhoneNumber());
        assertEquals(App.phoneUtil.format(phone, PhoneNumberUtil.PhoneNumberFormat.NATIONAL), user.getPhoneNumberString());
        assertArrayEquals(new String[] {
                "Alice",
                "U123456",
                "alice@example.com",
                App.phoneUtil.format(phone, PhoneNumberUtil.PhoneNumberFormat.NATIONAL)
        }, user.toArray());

        PhoneNumber newPhone = App.phoneUtil.parse("07700900009", "GB");
        user.setName("Bob");
        user.setUserId("U654321");
        user.setEmail("bob@example.com");
        user.setPhoneNumber(newPhone);

        assertEquals("Bob", user.getName());
        assertEquals("U654321", user.getUserId());
        assertEquals("bob@example.com", user.getEmail());
        assertSame(newPhone, user.getPhoneNumber());
    }
}

