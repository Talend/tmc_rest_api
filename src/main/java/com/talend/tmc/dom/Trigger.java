package com.talend.tmc.dom;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class Trigger {
	// Type of schedule = ['ONCE', 'DAILY', 'WEEKLY', 'MONTHLY', 'WEBHOOK', 'MANUAL'],
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String type;
    // Interval between task/plan running in days/weeks/months, required only if type of schedule is not equal to 'ONCE') ,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String interval;
    // Date when the task should start to run
    // format: "2019-09-25"
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String startDate;
    // Time zone for task schedule
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String timeZone;
}
