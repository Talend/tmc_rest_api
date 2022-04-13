package com.talend.tmc.dom;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class TaskNew {
    private String workspaceId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String environmentId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private  Map<String, String> artifact;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String[] tags;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private  Map<String, String> parameters;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private  Map<String, String> AutoUpgradeInfo;
    
    // resources missing
    // connections missing
}
