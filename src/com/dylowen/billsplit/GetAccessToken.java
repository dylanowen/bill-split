package com.dylowen.billsplit;

import java.io.Console;
import java.util.Map;

import com.dylowen.billsplit.settings.Account;
import com.dylowen.billsplit.settings.Settings;
import com.plaid.client.PlaidClients;
import com.plaid.client.PlaidUserClient;
import com.plaid.client.request.Credentials;

/**
 * TODO add description
 *
 * @author dylan.owen
 * @since Jan-2016
 */
public class GetAccessToken
    implements Action {

    final Settings settings;

    GetAccessToken(final Settings settings) {
        this.settings = settings;
    }

    public void execute() {
        final Console console = System.console();
        if (console == null) {
            System.err.println("could not get a console");
            return;
        }

        final String username = new String(console.readPassword("Username: "));
        final String password = new String(console.readPassword("Password: "));

        final PlaidUserClient plaidUserClient = PlaidClients.testUserClient(settings.plaid.clientId, settings.plaid.secret, 60);
        final Credentials credentials = new Credentials(username, password);

        for (Map.Entry<String, Account> entry : settings.accounts.entrySet()) {
            plaidUserClient.addUser(credentials, entry.getKey(), entry.getValue().email, null);
        }

        System.out.println("Access Token: " + plaidUserClient.getAccessToken());
    }
}