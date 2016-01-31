package com.dylowen.billsplit;

import java.io.File;
import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.dylowen.billsplit.client.Json;
import com.dylowen.billsplit.settings.Settings;

/**
 * TODO add description
 *
 * @author dylan.owen
 * @since Jan-2016
 */
public class Main {
    final static Option SETTINGS_PATH = Option.builder("s").longOpt("settings").argName("settings_file").hasArg().desc(
            "the settings file location").build();
    final static Option GET_ACCESS_TOKEN = new Option("access_token", "get an access token");

    final static Option HELP = new Option("help", "print this menu");

    final static Options MAIN_OPTIONS = new Options();
    final static Options HELP_OPTIONS = new Options();

    static {
        MAIN_OPTIONS.addOption(SETTINGS_PATH);
        MAIN_OPTIONS.addOption(GET_ACCESS_TOKEN);

        HELP_OPTIONS.addOption(HELP);
    }

    public static void main(String[] args) {
        CommandLineParser parser = new DefaultParser();

        try {
            // parse the command line help arguments
            CommandLine line = parser.parse(HELP_OPTIONS, args, true);

            if (line.getOptions().length > 0) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("restdefinition", MAIN_OPTIONS.addOption(HELP));
            }
            else {
                line = parser.parse(MAIN_OPTIONS, args);

                final String settingsPath;

                //find the settings path
                if (line.hasOption(SETTINGS_PATH.getOpt())) {
                    settingsPath = line.getOptionValue(SETTINGS_PATH.getOpt());
                }
                else {
                    final String home = System.getProperty("user.home");
                    settingsPath = home + "/.billsplit.json";
                }

                try {
                    final Settings settings = Json.get().getObjectMapper().readValue(new File(settingsPath),
                            Settings.class);

                    System.out.println(settings.toString());

                    final Action action;
                    if (line.hasOption(GET_ACCESS_TOKEN.getOpt())) {
                        action = new GetAccessToken(settings);
                    }
                    else {
                        action = new BillSplit(settings, line);
                    }

                    action.execute();
                }
                catch (final IOException e) {
                    System.err.println("Could not read settings file: " + settingsPath + "\n" + e.getMessage());
                }
            }
        }
        catch (MissingOptionException e) {
            for (Object o : e.getMissingOptions()) {
                printMissingArgError(MAIN_OPTIONS.getOption(o.toString()));
            }
        }
        catch (ParseException exp) {
            // oops, something went wrong
            System.err.println("Parsing failed. Reason: " + exp.getMessage());
        }
    }

    static void printMissingArgError(final Option option) {
        System.err.println("Missing Argument: <" + option.getLongOpt() + "> " + option.getDescription());
    }
}
