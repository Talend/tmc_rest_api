package com.talend.tmc.services.executables;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talend.tmc.dom.Executable;
import com.talend.tmc.dom.ExecutablePlanDetail;
import com.talend.tmc.services.*;
import org.apache.cxf.jaxrs.ext.search.SearchParseException;
import org.apache.cxf.jaxrs.ext.search.fiql.FiqlParser;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.util.Hashtable;

public class ExecutablePromotionService {
    private final String path = "executables/promotions";
    private final TalendApiClient client;
    private final TalendCloudRegion region;
    private ObjectMapper mapper;

    public static ExecutablePromotionService instance(TalendCredentials credentials, TalendCloudRegion region) throws NullPointerException
    {
        if (region == null) throw new NullPointerException("TalendCloudRegion cannot be null");
        if (credentials == null) throw new NullPointerException("TalendCredentials cannot be null");
        ExecutablePromotionService _instance = new ExecutablePromotionService(credentials, region);

        return _instance;
    }

    private ExecutablePromotionService(TalendCredentials credentials, TalendCloudRegion region)
    {
        client = TalendApiClient.createNewInstance(credentials);
        this.region = region;
        // Set ObjectMapper
        this.mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public Executable getById(String id) throws TalendRestException,
            SearchParseException, IOException, NullPointerException
    {
        if (id == null) throw new NullPointerException("Value of id cannot be null");

        Executable executable = null;
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
                executable = mapper.readValue(payload, Executable.class);
            }

        }

        return executable;
    }

    public Executable[] getByQuery(String fiqlQuery) throws TalendRestException,
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
