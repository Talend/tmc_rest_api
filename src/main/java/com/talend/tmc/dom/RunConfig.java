package com.talend.tmc.dom;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public class RunConfig {
    private Trigger trigger;
    private Runtime runtime;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String parallelExecutionAllowed;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String logLevel;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String remoteRunAsUser;
}
