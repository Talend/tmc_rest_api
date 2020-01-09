package com.talend.tmc.services;


import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomResponseErrorHandler implements ResponseErrorHandler {
    private static final Logger logger = Logger.getLogger(CustomResponseErrorHandler.class.getName());
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return RestUtil.isError(response.getStatusCode());
    }

    public void handleError(ClientHttpResponse response) throws IOException {
        logger.log(Level.SEVERE,"Response Error: " + response.getStatusCode() + " " + response.getStatusText());
    }
}
