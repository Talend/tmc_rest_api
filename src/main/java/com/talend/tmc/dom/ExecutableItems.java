package com.talend.tmc.dom;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ExecutableItems {
    @JsonProperty("items") 
    private Executable[] executables;
}
