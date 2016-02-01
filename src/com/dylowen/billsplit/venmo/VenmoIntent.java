package com.dylowen.billsplit.venmo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * TODO add description
 *
 * @author dylan.owen
 * @since Jan-2016
 */
public enum VenmoIntent {
    IPHONE("venmosdk://venmo.com/", "client", "ios", "app_version", "1.3.0"),
    ANDROID("venmosdk://paycharge", "using_new_sdk", "true", "app_local_id", "abcd"),
    BROWSER("https://venmo.com/");

    private final NumberFormat formatter = new DecimalFormat("#0.00");
    private final String urlBase;
    private final Map<String, String> queryParms;

    VenmoIntent(final String urlBase, final String... agentQueryParms) {
        this.urlBase = urlBase;
        this.queryParms = new HashMap<>();
        //default query parameters
        this.queryParms.put("app_name", "BillSplit");

        for (int i = 0; i < agentQueryParms.length; i += 2) {
            this.queryParms.put(agentQueryParms[i], agentQueryParms[i + 1]);
        }
    }

    public String pay(final double amount, final String recipient, final String note) {
        return pay(amount, Collections.singleton(recipient), note);
    }

    public String charge(final double amount, final String recipient, final String note) {
        return charge(amount, Collections.singleton(recipient), note);
    }

    public String pay(final double amount, final Set<String> recipients, final String note) {
        return constructIntent(amount, "pay", note);
    }

    public String charge(final double amount, final Set<String> recipients, final String note) {
        return constructIntent(amount, "charge", note);
    }

    //venmosdk://venmo.com/?client=ios&app_version=1.3.0&app_name=&app_id=&txn=charge&note=test&amount=10
    private String constructIntent(final double amount, final String transaction, final String note) {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.urlBase);
        sb.append("?amount=");
        sb.append(this.formatter.format(amount));
        sb.append("&txn=");
        sb.append(transaction);

        if (note != null) {
            sb.append("&note=");
            try {
                sb.append(URLEncoder.encode(note, "UTF-8"));
            }
            catch (UnsupportedEncodingException e) {
                throw new AssertionError(e);
            }
        }

        sb.append("&");

        sb.append(this.queryParms.entrySet().stream().map(e -> e.getKey() + "=" + e.getValue()).collect(
                Collectors.joining("&")));

        return sb.toString();
    }

    public static VenmoIntent getAgent(final String agent) {
        for (VenmoIntent intent : VenmoIntent.values()) {
            if (intent.name().toLowerCase().equals(agent)) {
                return intent;
            }
        }

        //fall back on browser intents
        return BROWSER;
    }
}