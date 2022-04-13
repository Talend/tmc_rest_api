package com.talend.tmc.services.runtime;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talend.tmc.dom.*;
import com.talend.tmc.services.*;
import org.apache.cxf.jaxrs.ext.search.fiql.FiqlParser;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Hashtable;

public class ClusterService {
    private final String path = "runtimes/remote-engine-clusters";
    private final TalendApiClient client;
    private final TalendCloudRegion region;
    private ObjectMapper mapper;

    public static ClusterService instance(TalendCredentials credentials, TalendCloudRegion region) throws NullPointerException
    {
        if (region == null) throw new NullPointerException("TalendCloudRegion cannot be null");
        if (credentials == null) throw new NullPointerException("TalendCredentials cannot be null");
        ClusterService _instance = new ClusterService(credentials, region);

        return _instance;
    }

    private ClusterService(TalendCredentials credentials, TalendCloudRegion region) {
        this.client = TalendApiClient.createNewInstance(credentials);
        this.region = region;
        // Set ObjectMapper
        this.mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public Cluster post(ClusterRequest request) throws TalendRestException, IOException,
            NullPointerException
    {
        if (request == null) throw new NullPointerException("ClusterRequest cannot be null");
        Writer jsonWriter = new StringWriter();
        mapper.writeValue(jsonWriter, request);
        jsonWriter.flush();
        StringBuilder uri = new StringBuilder();
        uri.append(region.toString()+path);

        Cluster cluster = null;

        Hashtable<Integer, String> response = client.call(HttpMethod.POST, uri.toString(), jsonWriter.toString());

        for (Integer httpStatus : response.keySet())
        {
            String payload = response.get(httpStatus);
            if (httpStatus != 201)
            {
                TalendError error = mapper.readValue(payload, TalendError.class);
                throw new TalendRestException(error.toString());
            } else {
                cluster = mapper.readValue(payload, Cluster.class);
            }

        }

        return cluster;
    }

    public boolean deleteCluster(String clusterId) throws TalendRestException, IOException,
            NullPointerException
    {
        if (clusterId == null) throw new NullPointerException("clusterId cannot be null");
        return action(clusterId, null, HttpMethod.DELETE);
    }

    public boolean deleteClusterRemoteEngine(String clusterId, String engineId) throws TalendRestException, IOException,
            NullPointerException
    {
        if (clusterId == null) throw new NullPointerException("clusterId cannot be null");
        if (engineId == null) throw new NullPointerException("engineId cannot be null");
        return action(clusterId, engineId, HttpMethod.DELETE);
    }

    public boolean put(String clusterId, String engineId) throws TalendRestException, IOException,
            NullPointerException
    {
        if (clusterId == null) throw new NullPointerException("clusterId cannot be null");
        if (engineId == null) throw new NullPointerException("engineId cannot be null");
        return action(clusterId, engineId, HttpMethod.PUT);
    }

    private boolean action(String clusterId, String engineId, HttpMethod method) throws TalendRestException, IOException,
            NullPointerException
    {
        boolean isDeleted = false;

        StringBuilder uri = new StringBuilder();
        uri.append(region.toString()+path+"/"+clusterId);
        if (engineId != null)
            uri.append("/engines/"+engineId);

        Hashtable<Integer, String> response = client.call(method, uri.toString(), null);

        for (Integer httpStatus : response.keySet())
        {
            String payload = response.get(httpStatus);
            if (httpStatus == 200 || httpStatus == 204){
                isDeleted = true;
            } else {
                TalendError error = mapper.readValue(payload, TalendError.class);
                throw new TalendRestException(error.toString());
            }

        }

        return isDeleted;
    }

    public Cluster[] get(String fiqlQuery) throws TalendRestException, IOException,
            NullPointerException
    {
        //Validates the fiqlQuery to meet the FIQL Spec. If not throw exception immediately
/*   kjdfkerhg     if (fiqlQuery != null) {
            FiqlParser<Executable> parser = new FiqlParser<>(Executable.class);
            parser.parse(fiqlQuery);
        }*/
        Cluster[] clusters = null;

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
                clusters = mapper.readValue(payload, Cluster[].class);
            }

        }

        return clusters;
    }

    public Cluster getById(String clusterId) throws TalendRestException, IOException,
            NullPointerException
    {
        if (clusterId == null) throw new NullPointerException("clusterId cannot be null");
        Cluster cluster = null;

        StringBuilder uri = new StringBuilder();
        uri.append(region.toString()+path+"/"+clusterId);


        Hashtable<Integer, String> response = client.call(HttpMethod.GET, uri.toString(), null);

        for (Integer httpStatus : response.keySet())
        {
            String payload = response.get(httpStatus);
            if (httpStatus != 200)
            {
                TalendError error = mapper.readValue(payload, TalendError.class);
                throw new TalendRestException(error.toString());
            } else {
                cluster = mapper.readValue(payload, Cluster.class);
            }

        }

        return cluster;
    }
}
