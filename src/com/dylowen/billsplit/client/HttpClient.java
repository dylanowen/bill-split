package com.dylowen.billsplit.client;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * TODO add description
 *
 * @author dylan.owen
 * @since Feb-2016
 */
public class HttpClient {
    private static final int DEFAULT_CONNECTION_POOL_SIZE = 5;
    private static final int DEFAULT_CONNECTION_TIMEOUT = 60;

    private final PoolingHttpClientConnectionManager connectionManager;
    private final CloseableHttpClient client;

    protected HttpClient() {
        this(DEFAULT_CONNECTION_TIMEOUT, DEFAULT_CONNECTION_POOL_SIZE);
    }

    protected HttpClient(final int timeout, final int poolSize) {
        final RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(timeout).build();

        this.connectionManager = new PoolingHttpClientConnectionManager();
        this.connectionManager.setMaxTotal(poolSize);

        this.client = HttpClients.custom().setConnectionManager(this.connectionManager).setDefaultRequestConfig(
                requestConfig).build();
    }

    public void close()
            throws IOException {
        this.connectionManager.shutdown();
        this.client.close();
    }

    public String execute(final HttpRequestBase request)
            throws IOException {
        return execute(request, StringResponseHandler.get());
    }

    public <T> T execute(final HttpRequestBase request, final ResponseHandler<T> responseHandler)
            throws IOException {
        try {
            return this.client.execute(request, responseHandler);
        }
        finally {
            request.releaseConnection();
        }
    }

    //TODO http://stackoverflow.com/questions/10146692/how-do-i-write-to-an-outputstream-using-defaulthttpclient
    protected HttpEntity getJsonBody(final Object json, final ObjectMapper mapper)
            throws JsonProcessingException {
        return new StringEntity(mapper.writeValueAsString(json), ContentType.APPLICATION_JSON);
    }
}
