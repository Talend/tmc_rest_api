package com.talend.tmc.dom;

import lombok.Data;

@Data
public class Promotion {
    private String executable;
    private String name;
    private String description;
    private Environment sourceEnvironment;
    private Environment targetEnvironment;
}
