package com.talend.tmc.services.executables;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Hashtable;

import org.springframework.http.HttpMethod;
import com.talend.tmc.dom.RunConfig;
import com.talend.tmc.services.TalendApiClient;
import com.talend.tmc.services.TalendCloudRegion;
import com.talend.tmc.services.TalendCredentials;
import com.talend.tmc.services.TalendError;
import com.talend.tmc.services.TalendRestException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


public class ExecutableRunConfig {
	private final String path = "executables";
	private final TalendApiClient client;
	private final TalendCloudRegion region;
	private ObjectMapper mapper;

	public static ExecutableRunConfig instance(TalendCredentials credentials, TalendCloudRegion region)
			throws NullPointerException {
		if (region == null)
			throw new NullPointerException("TalendCloudRegion cannot be null");
		if (credentials == null)
			throw new NullPointerException("TalendCredentials cannot be null");
		ExecutableRunConfig _instance = new ExecutableRunConfig(credentials, region);

		return _instance;
	}

	private ExecutableRunConfig(TalendCredentials credentials, TalendCloudRegion region) {
		client = TalendApiClient.createNewInstance(credentials);
		this.region = region;
		// Set ObjectMapper
		this.mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public void update(String executableType, String id, RunConfig runConfig)
			throws TalendRestException, IOException, NullPointerException, Exception {

		Writer jsonWriter = new StringWriter();
		mapper.writeValue(jsonWriter, runConfig);
		jsonWriter.flush();

		String url = getUrl(executableType, id);

		Hashtable<Integer, String> response = this.client.call(HttpMethod.PUT, url, jsonWriter.toString());

		for (Integer httpStatus : response.keySet()) {
			String payload = response.get(httpStatus);
			if (httpStatus != 200) {

				TalendError error = mapper.readValue(payload, TalendError.class);
				throw new TalendRestException(error.toString());
			}
		}
	}

	private String getUrl(String executableType, String id) throws NullPointerException, Exception {
		if (id == null)
			throw new NullPointerException("Value of id cannot be null");
		String baseurl = this.region.toString() + path + "/";
		if (executableType == "task") {
			executableType ="tasks/";
		} else if (executableType == "plan") {
			executableType = "plans/";
		} else {
			throw new Exception("type should be 'task' or 'plan'.");
		}
		StringBuilder uri = new StringBuilder();
	
		uri.append(baseurl + executableType + id + "/run-config");
		return uri.toString();
	}

	public RunConfig get(String type, String id) throws TalendRestException, IOException, Exception {
		RunConfig runConfig = null;

		String url = getUrl(type, id);
		Hashtable<Integer, String> response = client.call(HttpMethod.GET, url, null);

		for (Integer httpStatus : response.keySet()) {
			String payload = response.get(httpStatus);
			if (httpStatus != 200) {
				TalendError error = mapper.readValue(payload, TalendError.class);
				throw new TalendRestException(error.toString());
			} else {
				runConfig = mapper.readValue(payload, RunConfig.class);
			}
		}

		return runConfig;
	}
}
