package com.dylowen.billsplit;

import com.plaid.client.PlaidClients;
import com.plaid.client.PlaidUserClient;
import com.plaid.client.response.Transaction;
import com.plaid.client.response.TransactionsResponse;
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
        final PlaidUserClient plaidUserClient = PlaidClients.testUserClient(settings.plaid.clientId, settings.plaid.secret, 60);
        plaidUserClient.setAccessToken(this.settings.plaid.accessToken);

        TransactionsResponse response = plaidUserClient.updateTransactions();

        for (Transaction transaction : response.getTransactions()) {
             System.out.println(transaction.getName() + ": " + transaction.getCategory() + ": " + transaction.getAmount() + "\n");
        }
    }
}
