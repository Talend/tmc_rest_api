package com.talend.tmc.dom;

import lombok.Data;

@Data
public class Chart {
    private String stepId;
    private String stepName;
    private String status;
    //private String stepOnException;
    //private String nextStep;
    private Flow[] flows;
}
