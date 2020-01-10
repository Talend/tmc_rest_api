package com.talend.tmc.dom;

import lombok.Data;

@Data
public class Workspace {
    private String id;
    private String targetId;
    private String name;
    private String description;
    private String owner;
    private String type;
    private Environment environment;
    private Report analyzeReport;
    private Report promotionReport;
    private Flow[] flows;
    private Plan[] plans;
    private Action[] actions;
    private Connection[] connections;
    private Resource[] resources;
    private Engine[] engines;
    private Cluster[] clusters;
}
