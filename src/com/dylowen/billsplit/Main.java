package com.dylowen.billsplit;

import com.dylowen.billsplit.client.Json;

import java.io.File;
import java.io.IOException;

/**
 * TODO add description
 *
 * @author dylan.owen
 * @since Jan-2016
 */
public class Main {

    public static void main(String [] args) {
        String home = System.getProperty("user.home");
        final String settingsPath = (args.length > 0) ? args[0] : home + "/.billsplit.json";

        try {
            final Settings settings = Json.get().getObjectMapper().readValue(new File(settingsPath), Settings.class);

            System.out.println(settings.toString());
        }
        catch(final IOException e) {
            System.err.println("Could not read settings file: " + settingsPath + "\n" + e.getMessage());
        }
    }
}
