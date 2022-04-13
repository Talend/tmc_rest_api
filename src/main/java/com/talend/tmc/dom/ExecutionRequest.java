package com.talend.tmc.dom;

import lombok.Data;

import java.util.Map;

@Data
public class ExecutionRequest {
    private String executable;
    private Map<String, String> parameters;
}
