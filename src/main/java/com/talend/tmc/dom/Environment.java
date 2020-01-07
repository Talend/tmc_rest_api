package com.talend.tmc.dom;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Environment {
    private String id;
    private String name;
    private String description;
    @JsonProperty("default")
    private String _default;
}
