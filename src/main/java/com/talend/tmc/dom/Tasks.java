package com.talend.tmc.dom;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Tasks {
    @JsonProperty("items") 
    private Task[] items;
    @JsonProperty("limits") 
    private int limits;
    @JsonProperty("offset") 
    private int offset;
    @JsonProperty("total") 
    private int total;
}
