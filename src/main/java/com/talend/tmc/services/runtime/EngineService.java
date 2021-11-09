package com.talend.tmc.services.runtime;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talend.tmc.dom.Engine;
import com.talend.tmc.services.*;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Hashtable;

public class EngineService {
    private final String path = "runtimes/remote-engines";
    private final TalendApiClient client;
    private final TalendCloudRegion region;
    private ObjectMapper mapper;

    public static EngineService instance(TalendCredentials credentials, TalendCloudRegion region) throws NullPointerException
    {
        if (region == null) throw new NullPointerException("TalendCloudRegion cannot be null");
        if (credentials == null) throw new NullPointerException("TalendCredentials cannot be null");
        EngineService _instance = new EngineService(credentials, region);

        return _instance;
    }

    private EngineService(TalendCredentials credentials, TalendCloudRegion region) {
        this.client = TalendApiClient.createNewInstance(credentials);
        this.region = region;
        // Set ObjectMapper
        this.mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public Engine post(Engine request) throws TalendRestException, IOException,
            NullPointerException
    {
        if (request == null) throw new NullPointerException("Engine Request cannot be null");
        Writer jsonWriter = new StringWriter();
        mapper.writeValue(jsonWriter, request);
        jsonWriter.flush();
        StringBuilder uri = new StringBuilder();
        uri.append(region.toString()+path);

        Engine engine = null;

        Hashtable<Integer, String> response = client.call(HttpMethod.POST, uri.toString(), jsonWriter.toString());

        for (Integer httpStatus : response.keySet())
        {
            String payload = response.get(httpStatus);
            if (httpStatus != 201)
            {
                TalendError error = mapper.readValue(payload, TalendError.class);
                throw new TalendRestException(error.toString());
            } else {
                engine = mapper.readValue(payload, Engine.class);
            }

        }

        return engine;
    }

    public boolean delete(String engineId) throws TalendRestException, IOException,
            NullPointerException
    {
        if (engineId == null) throw new NullPointerException("engineId cannot be null");
        return action(engineId, false);
    }

    public boolean unpair(String engineId) throws TalendRestException, IOException,
            NullPointerException
    {
        if (engineId == null) throw new NullPointerException("engineId cannot be null");
        return action(engineId, true);
    }


    private boolean action(String engineId, boolean unpair) throws TalendRestException, IOException,
            NullPointerException
    {
        boolean isDeleted = false;

        StringBuilder uri = new StringBuilder();
        uri.append(region.toString()+path+"/"+engineId);
        if (unpair)
            uri.append("/pairing");

        Hashtable<Integer, String> response = client.call(HttpMethod.DELETE, uri.toString(), null);

        for (Integer httpStatus : response.keySet())
        {
            String payload = response.get(httpStatus);
            if (httpStatus == 200 || httpStatus == 204){
                isDeleted = true;
            } else {
                TalendError error = mapper.readValue(payload, TalendError.class);
                throw new TalendRestException(error.toString());
            }

        }

        return isDeleted;
    }

    public Engine[] get(String fiqlQuery) throws TalendRestException, IOException,
            NullPointerException
    {
        Engine[] engines = null;

        StringBuilder uri = new StringBuilder();
        uri.append(region.toString()+path);
        if (fiqlQuery != null) {
            uri.append("?query="+fiqlQuery);
        }

        Hashtable<Integer, String> response = client.call(HttpMethod.GET, uri.toString(), null);

        for (Integer httpStatus : response.keySet())
        {
            String payload = response.get(httpStatus);
            if (httpStatus != 200)
            {
                TalendError error = mapper.readValue(payload, TalendError.class);
                throw new TalendRestException(error.toString());
            } else {
                engines = mapper.readValue(payload, Engine[].class);
            }

        }

        return engines;
    }

    public Engine getById(String engineId) throws TalendRestException, IOException,
            NullPointerException
    {
        if (engineId == null) throw new NullPointerException("engineId cannot be null");
        Engine engine = null;

        StringBuilder uri = new StringBuilder();
        uri.append(region.toString()+path+"/"+engineId);


        Hashtable<Integer, String> response = client.call(HttpMethod.GET, uri.toString(), null);

        for (Integer httpStatus : response.keySet())
        {
            String payload = response.get(httpStatus);
            if (httpStatus != 200)
            {
                TalendError error = mapper.readValue(payload, TalendError.class);
                throw new TalendRestException(error.toString());
            } else {
                engine = mapper.readValue(payload, Engine.class);
            }

        }

        return engine;
    }
}
