package com.talend.tmc.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talend.tmc.dom.Executable;
import com.talend.tmc.dom.Workspace;
import org.apache.cxf.jaxrs.ext.search.SearchParseException;
import org.apache.cxf.jaxrs.ext.search.fiql.FiqlParser;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.util.Hashtable;

public class ExecutableService {
    private final String path = "executables";
    private final TalendApiClient client;
    private ObjectMapper mapper;

    public static ExecutableService newInstance(TalendCredentials credentials)
    {
        ExecutableService instance = new ExecutableService(credentials);

        return instance;
    }

    private ExecutableService(TalendCredentials credentials)
    {
        client = TalendApiClient.createNewInstance(credentials);

        // Set ObjectMapper
        this.mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public Executable[] getById(TalendCloudRegion region, String id) throws TalendRestException,
            SearchParseException, IOException, NullPointerException
    {
        if (id == null) throw new NullPointerException("Value of id cannot be null");
        return get(region, null, id);
    }

    public Executable[] getByQuery(TalendCloudRegion region, String fiqlQuery) throws TalendRestException,
            SearchParseException, IOException
    {
        return get(region, fiqlQuery, null);
    }

    private Executable[] get(TalendCloudRegion region, String fiqlQuery, String id) throws TalendRestException,
            SearchParseException, IOException
    {
        //Validates the fiqlQuery to meet the FIQL Spec. If not throw exception immediately
        if (fiqlQuery != null) {
            FiqlParser<Executable> parser = new FiqlParser<>(Executable.class);
            parser.parse(fiqlQuery);
        }

        Executable[] executables = null;
        StringBuilder uri = new StringBuilder();
        uri.append(region.toString()+path);
        if (fiqlQuery != null) {
            uri.append("?query="+fiqlQuery);
        }
        if (id != null)
            uri.append("/"+id);

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
                executables = mapper.readValue(payload, Executable[].class);
            }

        }

        return executables;
    }
}
