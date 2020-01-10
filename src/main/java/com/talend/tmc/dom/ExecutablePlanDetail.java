package com.talend.tmc.dom;

import com.talend.tmc.dom.Chart;
import com.talend.tmc.dom.Executable;
import com.talend.tmc.dom.Workspace;
import lombok.Data;

@Data
public class ExecutablePlanDetail {
    private Chart chart;
    private String executable;
    private String name;
    private String description;
    private Workspace workspace;
}
