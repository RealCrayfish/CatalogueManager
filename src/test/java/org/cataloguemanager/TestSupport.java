package org.cataloguemanager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public final class TestSupport {
    private TestSupport() {
    }

    public static Scanner scannerOf(String... lines) {
        String input = lines.length == 0
                ? System.lineSeparator()
                : String.join(System.lineSeparator(), lines) + System.lineSeparator();
        return new Scanner(input);
    }

    public static void resetAppState() {
        App.students.clear();
        App.staff.clear();
        App.users.clear();
        App.items.clear();
        App.isUnix = false;
    }
}

