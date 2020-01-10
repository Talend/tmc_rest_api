package com.talend.tmc.dom;

import lombok.Data;

import java.util.HashMap;

@Data
public class ExecutionPromotionRequest {
    private String executable;
    private boolean keepTargetResources;
    private Advanced advanced;
}


