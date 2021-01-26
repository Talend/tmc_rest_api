package com.talend.tmc.dom;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Items {
    @JsonProperty("items")
    private Executable[] executables;
}
