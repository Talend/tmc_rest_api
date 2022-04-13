package com.talend.tmc.services.artifacts;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talend.tmc.dom.Artifact;
import com.talend.tmc.dom.Artifacts;
import com.talend.tmc.services.*;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.util.Hashtable;

/**
 * @author reinier
 *
 */
/**
 * @author reinier
 *
 */
/**
 * @author reinier
 *
 */
public class ArtifactService {
    private final String path = "artifacts";
    private final TalendApiClient client;
    private final TalendCloudRegion region;
    private ObjectMapper mapper;

    public static ArtifactService instance(TalendCredentials credentials, TalendCloudRegion region) throws NullPointerException
    {
        if (region == null) throw new NullPointerException("TalendCloudRegion cannot be null");
        if (credentials == null) throw new NullPointerException("TalendCredentials cannot be null");
        ArtifactService _instance = new ArtifactService(credentials, region);

        return _instance;
    }

    private ArtifactService(TalendCredentials credentials, TalendCloudRegion region)
    {
        client = TalendApiClient.createNewInstance(credentials);
        this.region = region;
        // Set ObjectMapper
        this.mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * @param id
     * @return
     * @throws TalendRestException
     * @throws IOException
     * @throws NullPointerException
     */
    public Artifact getById(String id) throws TalendRestException,
            IOException, NullPointerException
    {
        if (id == null) throw new NullPointerException("Value of id cannot be null");
        return get(id);
    }

   /**
 * @param query
 * @return
 * @throws TalendRestException
 * @throws IOException
 */
public Artifact[] getByQuery(String query) throws TalendRestException,
            IOException
    {
        Artifacts artifacts= null;
        
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
        		artifacts = mapper.readValue(payload, Artifacts.class);
            }
        }

        return artifacts.getItems();

    }

    /**
     * @param name Name of the Artifact 
     * @param workspace Id of the Workspace
     * @return
     * @throws TalendRestException
     * @throws IOException
     */
    public Artifact[] getByName(String name, String workspace) throws TalendRestException,
     IOException
    {
    	String query = "name=" + name;
    	
    	query = workspace != null ? query + "&workspaceId=" + workspace : query;
        
    	return getByQuery(query);
    }

    private Artifact get(String id) throws TalendRestException,
             IOException
    {
        Artifact artifact= null;
        
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
           		artifact = mapper.readValue(payload, Artifact.class);
            }
        }

        return artifact;
    }
}
