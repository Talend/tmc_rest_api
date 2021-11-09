package com.talend.tmc.dom;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class Executable {
    private String executable;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Workspace workspace;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String artifactId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Runtime runtime;
}
