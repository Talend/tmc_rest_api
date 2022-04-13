package com.talend.tmc.dom;

import lombok.Data;

@Data
public class ExecutionPromotionRequest {
    private String executable;
    private boolean keepTargetResources;
    private boolean keepTargetRunProfiles;
    private Advanced advanced;
}


