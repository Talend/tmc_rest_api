package com.talend.tmc.dom;

import lombok.Data;

@Data
public class Action {
    private String id;
    private String targetId;
    private String name;
    private String type;
    private Report analyzeReport;
    private Report promotionReport;
}
