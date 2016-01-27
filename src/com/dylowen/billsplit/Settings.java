package com.dylowen.billsplit;

/**
 * TODO add description
 *
 * @author dylan.owen
 * @since Jan-2016
 */
public class Settings {

    public ApiSettings plaid;

    @Override
    public String toString() {
        return  "Settings\n" +
                "Plaid Settings:\n" +
                    plaid + "\n";

    }

    public static class ApiSettings {
        public String clientId;
        public String secret;
        public String publicKey;

        @Override
        public String toString() {
            return  "\tclientId: " + clientId + "\n" +
                    "\tsecret: " + secret + "\n" +
                    "\tpublicKey: " + publicKey;
        }
    }
}
