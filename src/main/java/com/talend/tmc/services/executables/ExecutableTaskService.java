package com.talend.tmc.services.executables;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talend.tmc.dom.Executable;
import com.talend.tmc.dom.Items;
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

    public Executable[] getById(String id) throws TalendRestException,
            IOException, NullPointerException
    {
        if (id == null) throw new NullPointerException("Value of id cannot be null");
        return get( null, id);
    }

    public Executable[] getByQuery(String query) throws TalendRestException,
            IOException
    {
        return get(query, null);
    }

    public Executable[] getByName(String name, String workspace) throws TalendRestException,
     IOException
    {
    	String query = "name=" + name;
    	
    	query = workspace != null ? query + "&workspaceId=" + workspace : query;
    	
    	return get(query, null);
    }

    private Executable[] get(String query, String id) throws TalendRestException,
             IOException
    {
        Items items = null;
        Executable executable = null;
        Executable[] returnValue = null; 
        
        StringBuilder uri = new StringBuilder();
        uri.append(region.toString()+path);
        if (query != null) {
            uri.append("?" + query);
        }
        if (id != null)
            uri.append("/" + id);

        Hashtable<Integer, String> response = client.call(HttpMethod.GET, uri.toString(), null);
 
        for (Integer httpStatus : response.keySet())
        {
            String payload = response.get(httpStatus);
            if (httpStatus != 200)
            {
            	TalendError error = mapper.readValue(payload, TalendError.class);
                throw new TalendRestException(error.toString());
            } else {
            	if (id == null) {
            		items = mapper.readValue(payload, Items.class);
            		returnValue = items.getExecutables();
            	} else {
            		executable = mapper.readValue(payload, Executable.class);
            		returnValue= new Executable[1];
            		returnValue[0] = executable;
            	}
            }

        }

        return returnValue;
    }
}
