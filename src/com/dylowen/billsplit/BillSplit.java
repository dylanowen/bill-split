package com.dylowen.billsplit;

import org.apache.commons.cli.CommandLine;

import com.dylowen.billsplit.settings.Settings;

/**
 * TODO add description
 *
 * @author dylan.owen
 * @since Jan-2016
 */
public class BillSplit
    implements Action {

    final Settings settings;
    final CommandLine line;

    BillSplit(final Settings settings, final CommandLine line) {
        this.settings = settings;
        this.line = line;
    }

    @Override
    public void execute() {
        
    }
}
