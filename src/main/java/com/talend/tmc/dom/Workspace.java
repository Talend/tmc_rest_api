package com.talend.tmc.dom;

import lombok.Data;

@Data
public class Workspace {
    private String id;
    private String name;
    private String description;
    private String owner;
    private String type;
    private Environment environment;
}
