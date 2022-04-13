package com.talend.tmc.dom;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ExecutionItems {
    @JsonProperty("items") 
    private Execution[] executions;
}
