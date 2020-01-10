package com.talend.tmc.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Hashtable;

public class TalendApiClient {
    private static TalendApiClient instance = null;
    private HttpHeaders httpHeaders;
    private final TalendCredentials credentials;
    private RestTemplate rest;

    private TalendApiClient(TalendCredentials credentials)
    {
        this.credentials = credentials;
        this.httpHeaders = createHeaders(credentials);

        // Set RestTemplate
        this.rest = new RestTemplate();
        this.rest.setErrorHandler(new CustomResponseErrorHandler());
    }

    public static TalendApiClient createNewInstance(TalendCredentials authorization)
    {
        if (instance == null)
            instance = new TalendApiClient(authorization);

        return instance;
    }

    public Hashtable<Integer, String> call(HttpMethod method, String uri, String payload) throws TalendRestException
    {
        Hashtable<Integer, String> response = new Hashtable<>();
        HttpEntity<String> requestEntity = null;
        ResponseEntity<String> responseEntity = null;
        try {

            requestEntity = new HttpEntity<String>(payload, this.httpHeaders);

            responseEntity = rest.exchange(uri, method, requestEntity, String.class);

            response.put(responseEntity.getStatusCodeValue(),responseEntity.getBody());

        } catch(Exception e)
        {
            throw new TalendRestException(e.getMessage());
        }


        return response;
    }

    private HttpHeaders createHeaders(TalendCredentials credentials)
    {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("Accept", "application/json,text/plain");
        if (credentials instanceof TalendBearerAuth)
            httpHeaders.add("Authorization", "Bearer " + credentials.getBearerToken());
        else
        {
            httpHeaders.add("Authorization", "Basic " + Base64.getEncoder().encodeToString((credentials.getUsername() +
                    ":" + credentials.getPassword()).getBytes()));
        }

        return httpHeaders;
    }
}
