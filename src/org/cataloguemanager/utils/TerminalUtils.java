package org.cataloguemanager.utils;

import org.cataloguemanager.App;

import java.io.IOException;

public class TerminalUtils {
    /**
     * Alternative to Thread.sleep() to ignore interrupts.
     * @param milliseconds
     */
    public static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            IO.println("Interrupt Detected.");
        }
    }

    public static void clearTerminal()  {
        try {
            if (App.isUnix) {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
        } catch (IOException e) {
            IO.println("\n\n"); // If unable to clear, print empty lines instead.
        }
        catch (InterruptedException e) {
            IO.println("Interrupt Detected.");
        }
    }

    public static void displayLogo() {
        clearTerminal();
        IO.println("\n  ___      _        _                    __  __                             ");
        IO.println(" / __|__ _| |_ __ _| |___  __ _ _  _ ___|  \\/  |__ _ _ _  __ _ __ _ ___ _ _ ");
        IO.println("| (__/ _` |  _/ _` | / _ \\/ _` | || / -_) |\\/| / _` | ' \\/ _` / _` / -_) '_|");
        IO.println(" \\___\\__,_|\\__\\__,_|_\\___/\\__, |\\_,_\\___|_|  |_\\__,_|_||_\\__,_\\__, \\___|_|  ");
        IO.println("                          |___/                               |___/         \n");
    }
}
