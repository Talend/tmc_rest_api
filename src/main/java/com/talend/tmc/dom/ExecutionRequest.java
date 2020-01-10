package com.talend.tmc.dom;

import lombok.Data;

import java.util.Hashtable;

@Data
public class ExecutionRequest {
    private String executable;
    private Hashtable<String, String> parameters;
}
