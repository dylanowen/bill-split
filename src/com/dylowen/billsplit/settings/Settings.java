package com.dylowen.billsplit.settings;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * TODO add description
 *
 * @author dylan.owen
 * @since Jan-2016
 */
public class Settings {

    public ApiSettings plaid;
    public Map<String, Account> accounts;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Settings\n" + "Plaid Settings:\n" + plaid + "Accounts:\n");
        sb.append(accounts.entrySet().stream().map(e -> e.getKey() + "\n" + e.getValue()).collect(
                Collectors.joining("\n")));

        return sb.toString();
    }
}
