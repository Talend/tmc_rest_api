package com.talend.tmc.dom;

import lombok.Data;

@Data
public class ExecutionPlanDetail {
    private String executionId;
    private String startTimestamp;
    private String finishTimestamp;
    private String userId;
    private String planId;
    private String executionStatus;
    private int plannedExecutableCount;
    private int doneExecutableCount;
    private Execution[] doneExecutableDetails;
}
