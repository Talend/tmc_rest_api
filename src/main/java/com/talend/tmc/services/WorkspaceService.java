package com.talend.tmc.services;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talend.tmc.dom.Workspace;
import org.apache.cxf.jaxrs.ext.search.SearchParseException;
import org.apache.cxf.jaxrs.ext.search.client.FiqlSearchConditionBuilder;
import org.apache.cxf.jaxrs.ext.search.client.SearchConditionBuilder;
import org.apache.cxf.jaxrs.ext.search.fiql.FiqlParser;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.util.Hashtable;

public class WorkspaceService {

    private final String path = "workspaces";
    private final TalendApiClient client;
    private ObjectMapper mapper;

    public static WorkspaceService newInstance(TalendCredentials credentials)
    {
        WorkspaceService instance = new WorkspaceService(credentials);

        return instance;
    }

    private WorkspaceService(TalendCredentials credentials) {
        client = TalendApiClient.createNewInstance(credentials);

        // Set ObjectMapper
        this.mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public Workspace[] get(TalendCloudRegion region, String fiqlQuery) throws TalendRestException, SearchParseException, IOException {

        //Validates the fiqlQuery to meet the FIQL Spec. If not throw exception immediately
        FiqlParser<Workspace> parser = new FiqlParser<>(Workspace.class);
        parser.parse(fiqlQuery);

        Workspace[] workspace = null;
        StringBuilder uri = new StringBuilder();
        uri.append(region.toString()+path);
        if (fiqlQuery != null) {
            uri.append("?query="+fiqlQuery);
        }

        System.out.println(uri.toString());
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
