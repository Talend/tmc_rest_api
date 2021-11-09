package com.talend.tmc.dom;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class Task {
    private String id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String executable;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Workspace workspace;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String version;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Artifact artifact;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String[] tags;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private  Map<String, String> parameters;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private  Map<String, String> AutoUpgradeInfo;
    
    // resources missing
}
