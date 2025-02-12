package io.blindnet.storageconnector;

import com.fasterxml.jackson.databind.json.JsonMapper;
import io.blindnet.storageconnector.handlers.DataRequestHandler;
import io.blindnet.storageconnector.handlers.ErrorHandler;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;

public interface StorageConnector {
    static StorageConnector create() throws URISyntaxException {
        String env = System.getenv().get("BN_CONNECTOR_ENDPOINT");
        if(env != null) return create(env);

        return new StorageConnectorImpl();
    }

    static StorageConnector create(String endpoint) throws URISyntaxException {
        return create(new URI(endpoint));
    }

    static StorageConnector create(URI endpoint) {
        if(!endpoint.getScheme().startsWith("http"))
            throw new IllegalArgumentException("Invalid endpoint URI: not HTTP(S)");

        return new StorageConnectorImpl(endpoint);
    }

    DataRequestHandler getDataRequestHandler();

    StorageConnector setDataRequestHandler(DataRequestHandler dataRequestHandler);

    ErrorHandler getErrorHandler();

    StorageConnector setErrorHandler(ErrorHandler errorHandler);

    ExecutorService getExecutorService();

    StorageConnector setExecutorService(ExecutorService executorService);

    JsonMapper getJsonMapper();

    StorageConnector setJsonMapper(JsonMapper objectMapper);

    void start();

    void startBlocking() throws IOException, InterruptedException;
}
