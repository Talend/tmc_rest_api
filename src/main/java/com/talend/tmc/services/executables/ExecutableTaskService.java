package com.talend.tmc.services.executables;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talend.tmc.dom.Executable;
import com.talend.tmc.dom.TaskNew;
import com.talend.tmc.services.*;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.util.Hashtable;

public class ExecutableTaskService {
    private final String path = "executables/tasks";
    private final TalendApiClient client;
    private final TalendCloudRegion region;
    private ObjectMapper mapper;

    public static ExecutableTaskService instance(TalendCredentials credentials, TalendCloudRegion region) throws NullPointerException
    {
        if (region == null) throw new NullPointerException("TalendCloudRegion cannot be null");
        if (credentials == null) throw new NullPointerException("TalendCredentials cannot be null");
        ExecutableTaskService _instance = new ExecutableTaskService(credentials, region);

        return _instance;
    }

    private ExecutableTaskService(TalendCredentials credentials, TalendCloudRegion region)
    {
        client = TalendApiClient.createNewInstance(credentials);
        this.region = region;
        // Set ObjectMapper
        this.mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public TaskNew getById(String id) throws TalendRestException,
             IOException, NullPointerException
    {
        if (id == null) throw new NullPointerException("Value of id cannot be null");

        TaskNew planResponse = null;
        StringBuilder uri = new StringBuilder();
        uri.append(region.toString()+path);
        uri.append("/"+id);

        Hashtable<Integer, String> response = client.call(HttpMethod.GET, uri.toString(), null);

        for (Integer httpStatus : response.keySet())
        {
            String payload = response.get(httpStatus);
            if (httpStatus != 200)
            {
                TalendError error = mapper.readValue(payload, TalendError.class);
                throw new TalendRestException(error.toString());
            } else {
                planResponse = mapper.readValue(payload, TaskNew.class);
            }

        }

        return planResponse;
    }

    public Executable[] getByQuery(String fiqlQuery) throws TalendRestException,
            IOException
    {

        Executable[] executables = null;
        StringBuilder uri = new StringBuilder();
        uri.append(region.toString()+path);
        if (fiqlQuery != null) {
            uri.append("?_s="+fiqlQuery);
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
                executables = mapper.readValue(payload, Executable[].class);
            }

        }

        return executables;
    }

}