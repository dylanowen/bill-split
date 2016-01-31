package com.dylowen.billsplit.settings;

/**
 * TODO add description
 *
 * @author dylan.owen
 * @since Jan-2016
 */
public class ApiSettings {
    public String clientId;
    public String secret;
    public String publicKey;
    public String accessToken;

    @Override
    public String toString() {
        return  "\tclientId:    " + clientId + "\n" +
                "\tsecret:      " + secret + "\n" +
                "\tpublicKey:   " + publicKey + "\n" +
                "\taccessToken: " + accessToken + "\n";
    }
}
