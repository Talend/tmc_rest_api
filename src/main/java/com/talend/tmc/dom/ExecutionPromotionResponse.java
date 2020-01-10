package com.talend.tmc.dom;

import lombok.Data;

@Data
public class ExecutionPromotionResponse {
    private String executionId;
    private String startTimestamp;
    private String finishTimestamp;
    private String userId;
    private String promotionId;
    private boolean keepTargetResources;
    private Advanced advanced;
    private boolean defective;
    private String status;
    private String statusMessage;
    private Workspace[] workspaces;
    private Engine[] engines;
    private Cluster[] clusters;
    private String pipelineId;
}
