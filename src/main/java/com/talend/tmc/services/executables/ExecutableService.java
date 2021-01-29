package com.talend.tmc.services.executables;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talend.tmc.dom.Executable;
import com.talend.tmc.dom.Items;
import com.talend.tmc.services.*;
import org.apache.cxf.jaxrs.ext.search.SearchParseException;
import org.apache.cxf.jaxrs.ext.search.fiql.FiqlParser;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.util.Hashtable;

public class ExecutableService {
    private final String path = "executables/tasks";
    private final TalendApiClient client;
    private final TalendCloudRegion region;
    private ObjectMapper mapper;

    public static ExecutableService instance(TalendCredentials credentials, TalendCloudRegion region) throws NullPointerException
    {
        if (region == null) throw new NullPointerException("TalendCloudRegion cannot be null");
        if (credentials == null) throw new NullPointerException("TalendCredentials cannot be null");
        ExecutableService _instance = new ExecutableService(credentials, region);

        return _instance;
    }

    private ExecutableService(TalendCredentials credentials, TalendCloudRegion region)
    {
        client = TalendApiClient.createNewInstance(credentials);
        this.region = region;
        // Set ObjectMapper
        this.mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public Executable[] getById(String id) throws TalendRestException,
            SearchParseException, IOException, NullPointerException
    {
        if (id == null) throw new NullPointerException("Value of id cannot be null");
        return get( null, id);
    }

    public Executable[] getByQuery(String fiqlQuery) throws TalendRestException,
            SearchParseException, IOException
    {
        return get(fiqlQuery, null);
    }

    private Executable[] get(String fiqlQuery, String id) throws TalendRestException,
            SearchParseException, IOException
    {
        //Validates the fiqlQuery to meet the FIQL Spec. If not throw exception immediately
        //if (fiqlQuery != null) {
        //    FiqlParser<Executable> parser = new FiqlParser<>(Executable.class);
        //    parser.parse(fiqlQuery);
        //}
        Items items = null;
       
        StringBuilder uri = new StringBuilder();
        uri.append(region.toString()+path);
        if (fiqlQuery != null) {
            //uri.append("?_s="+fiqlQuery); Remove since v2.0
            uri.append("?"+fiqlQuery);
        }
        if (id != null)
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
                items = mapper.readValue(payload, Items.class);
            }

        }

        return items.getExecutables();
    }
}
