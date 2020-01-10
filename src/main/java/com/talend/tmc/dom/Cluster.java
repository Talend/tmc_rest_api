package com.talend.tmc.dom;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class Cluster {
    private String id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String targetId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String type;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Workspace workspace;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Report analyzeReport;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Report promotionReport;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String createDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String updateDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String runtimeId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String availability;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String[] engines;
}
