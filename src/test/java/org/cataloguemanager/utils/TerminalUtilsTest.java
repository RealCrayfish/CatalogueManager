package org.cataloguemanager.utils;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TerminalUtilsTest {
    @BeforeEach
    void setUp() {
        TestSupport.resetAppState();
    }

    @AfterEach
    void tearDown() {
        TestSupport.resetAppState();
    }

    @Test
    void promptDateUsesTodayWhenInputIsBlank() {
        assertEquals(LocalDate.now(), TerminalUtils.promptDate(TestSupport.scannerOf(""), "Enter Date"));
    }

    @Test
    void promptEmailAcceptsValidAddress() {
        assertEquals("alice@example.com", TerminalUtils.promptEmail(TestSupport.scannerOf("alice@example.com"), "Enter Email"));
    }

    @Test
    void promptPhoneNumberParsesValidNumber() {
        PhoneNumber result = TerminalUtils.promptPhoneNumber(TestSupport.scannerOf("07700900000"), "Enter Phone Number");
        assertNotNull(result);
        assertEquals(44, result.getCountryCode());
        assertTrue(result.getNationalNumber() > 0);
    }

    @Test
    void promptConfirmReturnsTrueForCancelAndFalseForConfirm() {
        assertTrue(TerminalUtils.promptConfirm(TestSupport.scannerOf("1")));
        assertFalse(TerminalUtils.promptConfirm(TestSupport.scannerOf("2")));
    }

    @Test
    void promptIdReturnsUppercaseAndAllowsCancel() throws Exception {
        App.items.put("I123456", new Item("I123456", "Ariane 6", "Arianespace", LocalDate.now(), "Aerospace"));
        App.users.put("U123456", new Student("Alice", "U123456", "alice@example.com", App.phoneUtil.parse("07700900000", "GB")));
        assertEquals("I123456", TerminalUtils.promptID(TestSupport.scannerOf("i123456"), "Enter Item ID", true, true));
        assertEquals("U123456", TerminalUtils.promptID(TestSupport.scannerOf("u123456"), "Enter User ID", false, true));
        assertEquals("0", TerminalUtils.promptID(TestSupport.scannerOf("0"), "Enter Item ID", true, true));
    }

    @Test
    void promptNewIdAcceptsValidItemAndUserIds() {
        assertEquals("I000123", TerminalUtils.promptNewID(TestSupport.scannerOf("I000123"), "Enter Item ID", true));
        assertEquals("U000123", TerminalUtils.promptNewID(TestSupport.scannerOf("U000123"), "Enter User ID", false));
    }
}


