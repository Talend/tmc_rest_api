package com.talend.tmc.dom;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) 
public class Runtime {
	//Type of runtime = ['CLOUD', 'REMOTE_ENGINE', 'REMOTE_ENGINE_CLUSTER', 'CLOUD_EXCLUSIVE'],
    private String type;
	// Runtime id
	private String id;
	//Remote engine id
	private String engineId;
	//Remote engine cluster id
	private String clusterId;
	//Pipeline engine run profile id
	private String runProfileId;
}

/*
 * TODO: implement enum for RuntimeType
 */
/*
public enum RuntimeType {
	CLOUD, REMOTE_ENGINE, REMOTE_ENGINE_CLUSTER, CLOUD_EXCLUSIVE
}
*/