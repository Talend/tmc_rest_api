package com.talend.tmc.services.executables;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talend.tmc.dom.Task;
import com.talend.tmc.dom.TaskNew;
import com.talend.tmc.dom.Tasks;
import com.talend.tmc.services.*;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Hashtable;

public class ExecutableTask {
    private final String path = "executables/tasks";
    private final TalendApiClient client;
    private final TalendCloudRegion region;
    private ObjectMapper mapper;

    public static ExecutableTask instance(TalendCredentials credentials, TalendCloudRegion region) throws NullPointerException
    {
        if (region == null) throw new NullPointerException("TalendCloudRegion cannot be null");
        if (credentials == null) throw new NullPointerException("TalendCredentials cannot be null");
        ExecutableTask _instance = new ExecutableTask(credentials, region);

        return _instance;
    }

    private ExecutableTask(TalendCredentials credentials, TalendCloudRegion region)
    {
        client = TalendApiClient.createNewInstance(credentials);
        this.region = region;
        // Set ObjectMapper
        this.mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public Task create(TaskNew newTask) throws TalendRestException,
    IOException, NullPointerException
    {
    	Task task = null;

    	Writer jsonWriter = new StringWriter();
        mapper.writeValue(jsonWriter, newTask);
        jsonWriter.flush();

        StringBuilder uri = new StringBuilder();
        uri.append(region.toString()+path);

    	Hashtable<Integer, String> response = client.call(HttpMethod.POST, uri.toString(), jsonWriter.toString());

    	for (Integer httpStatus : response.keySet())
    	{
    		String payload = response.get(httpStatus);
    		if (httpStatus != 201)
    		{

    			TalendError error = mapper.readValue(payload, TalendError.class);
    			throw new TalendRestException(error.toString());
    		} else {
    			task = mapper.readValue(payload, Task.class);
    		}
    	}

    	return task;

    }

    public Task getById(String id) throws TalendRestException,
            IOException, NullPointerException
    {
        if (id == null) throw new NullPointerException("Value of id cannot be null");
        return get(id);
    }

    public Task[] getByQuery(String query) throws TalendRestException,
            IOException
    {
        Tasks tasks= null;
        
        StringBuilder uri = new StringBuilder();
        uri.append(region.toString()+path);
        uri.append("?" + query);

        Hashtable<Integer, String> response = client.call(HttpMethod.GET, uri.toString(), null);
 
        for (Integer httpStatus : response.keySet())
        {
            String payload = response.get(httpStatus);
            if (httpStatus != 200)
            {
            	 
            	TalendError error = mapper.readValue(payload, TalendError.class);
                throw new TalendRestException(error.toString());
            } else {
        		tasks = mapper.readValue(payload, Tasks.class);
            }
        }

        return tasks.getItems();

    }

    /**
     * @param name Name of the Job 
     * @param workspace Id of the Workspace
     * @return
     * @throws TalendRestException
     * @throws IOException
     */
    public Task[] getByName(String name, String workspace) throws TalendRestException,
     IOException
    {
    	String query = "name=" + name;
    	
    	query = workspace != null ? query + "&workspaceId=" + workspace : query;
        
    	return getByQuery(query);
    }

    private Task get(String id) throws TalendRestException,
             IOException
    {
        Task task= null;
        
        StringBuilder uri = new StringBuilder();
        uri.append(region.toString()+path);
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
        		task = mapper.readValue(payload, Task.class);
            }
        }

        return task;
    }
}
