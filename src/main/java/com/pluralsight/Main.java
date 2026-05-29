package com.pluralsight;

import com.pluralsight.ui.UserInterface;

/**
 * Main.java - Application entry point.
 * Java always starts execution here (the main method).
 * All it does is create a UserInterface and call start() --
 * the UserInterface then drives the entire application.
 */

public class Main {

    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        ui.start();
    }
}