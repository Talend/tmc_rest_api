package com.talend.tmc.services.executions;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talend.tmc.dom.Execution;
import com.talend.tmc.dom.ExecutionItems;
import com.talend.tmc.services.*;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.util.Hashtable;

public class TaskExecutionService {
    private final String path = "executables/tasks/";
    private final TalendApiClient client;
    private final TalendCloudRegion region;
    private ObjectMapper mapper;

    public static TaskExecutionService instance(TalendCredentials credentials, TalendCloudRegion region) throws NullPointerException
    {
        if (region == null) throw new NullPointerException("TalendCloudRegion cannot be null");
        if (credentials == null) throw new NullPointerException("TalendCredentials cannot be null");
        TaskExecutionService _instance = new TaskExecutionService(credentials, region);

        return _instance;
    }

    private TaskExecutionService(TalendCredentials credentials, TalendCloudRegion region) {
        this.client = TalendApiClient.createNewInstance(credentials);
        this.region = region;
        // Set ObjectMapper
        this.mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public Execution[] get(String id) throws TalendRestException, IOException,
            NullPointerException
    {
        Execution[] executions = null;
        ExecutionItems items = null;
        if (id == null) throw new NullPointerException("id of Execution Job cannot be null");
        StringBuilder uri = new StringBuilder();
        uri.append(region.toString()+path+id+"/executions");

        Hashtable<Integer, String> response = client.call(HttpMethod.GET, uri.toString(), null);

        for (Integer httpStatus : response.keySet())
        {
            String payload = response.get(httpStatus);
            if (httpStatus != 200)
            {
                TalendError error = mapper.readValue(payload, TalendError.class);
                throw new TalendRestException(error.toString());
            } else {
        		items = mapper.readValue(payload, ExecutionItems.class);
        		executions = items.getExecutions();
            }

        }

        return executions;
    }
}
