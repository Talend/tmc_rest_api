package com.talend.tmc.dom;

import lombok.Data;

@Data
public class Flow {
    private String id;
    private String targetId;
    private String name;
    private String type;
    private String version;
    private String description;
    private String destination;
    private String workspaceId;
    private boolean plan;
    private Report analyzeReport;
    private Report promotionReport;
}
