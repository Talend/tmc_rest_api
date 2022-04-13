package com.talend.tmc.services.workspaces;


import java.io.IOException;
import java.util.Hashtable;

import org.apache.cxf.jaxrs.ext.search.SearchParseException;
import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talend.tmc.dom.Workspace;
import com.talend.tmc.services.TalendApiClient;
import com.talend.tmc.services.TalendCloudRegion;
import com.talend.tmc.services.TalendCredentials;
import com.talend.tmc.services.TalendError;
import com.talend.tmc.services.TalendRestException;

public class WorkspaceService {

    private final String path = "workspaces";
    private final TalendApiClient client;
    private final TalendCloudRegion region;
    private ObjectMapper mapper;

    public static WorkspaceService instance(TalendCredentials credentials, TalendCloudRegion region) throws NullPointerException
    {
        if (region == null) throw new NullPointerException("TalendCloudRegion cannot be null");
        if (credentials == null) throw new NullPointerException("TalendCredentials cannot be null");
        WorkspaceService _instance = new WorkspaceService(credentials, region);

        return _instance;
    }

    private WorkspaceService(TalendCredentials credentials, TalendCloudRegion region) {
        client = TalendApiClient.createNewInstance(credentials);
        this.region = region;
        // Set ObjectMapper
        this.mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public Workspace[] get() throws TalendRestException, SearchParseException, IOException {
    	return get(null);
    }

    public Workspace[] get(String fiqlQuery) throws TalendRestException, SearchParseException, IOException {

        Workspace[] workspace = null;
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
                workspace = mapper.readValue(payload, Workspace[].class);
            }

        }

        return workspace;
    }
}
