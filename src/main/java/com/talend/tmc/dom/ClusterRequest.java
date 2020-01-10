package com.talend.tmc.dom;

import lombok.Data;

@Data
public class ClusterRequest {
    private String name;
    private String workspaceId;
    private String environmentId;
    private String description;
    private String[] remoteEngines;
}
