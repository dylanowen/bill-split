package com.dylowen.billsplit.client;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * TODO add description
 *
 * @author dylan.owen
 * @since Feb-2016
 */
public class StringResponseHandler
        implements ResponseHandler<String> {

    private static final StringResponseHandler SINGLETON = new StringResponseHandler();

    private StringResponseHandler() {
    }

    @Override
    public String handleResponse(final HttpResponse response)
            throws IOException {
        final StatusLine statusLine = response.getStatusLine();
        final HttpEntity entity = response.getEntity();

        if (statusLine.getStatusCode() >= 300) {
            throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
        }
        if (entity == null) {
            throw new HttpClientException("Response contains no content");
        }

        try (final InputStream inputStream = entity.getContent()) {
            final Scanner scanner = new java.util.Scanner(inputStream).useDelimiter("\\A");

            return scanner.hasNext() ? scanner.next() : "";
        }
    }

    public static StringResponseHandler get() {
        return SINGLETON;
    }
}
