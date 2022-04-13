package com.talend.tmc.dom;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Artifacts {
    @JsonProperty("items") 
    private Artifact[] items;
    @JsonProperty("limits") 
    private int limits;
    @JsonProperty("offset") 
    private int offset;
    @JsonProperty("total") 
    private int total;
}
