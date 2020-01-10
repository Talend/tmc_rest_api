package com.talend.tmc.dom;

import lombok.Data;

@Data
public class Execution {
    private String executionId;
    private String startTimestamp;
    private String finishTimestamp;
    private String userId;
    private String jobId;
    private String jobVersion;
    private String environmentVersion;
    private String executionStatus;
    private String executionType;
    private String executionDestination;
    private String containerId;
    private String runProfileId;
    private String remoteEngineId;
    private String remoteEngineClusterId;
    private int numberOfProcessedRows;
    private int numberOfRejectedRows;
    private String accountId;
    private String workspaceId;
    private String planId;
    private String errorType;
    private String errorMessage;
}
