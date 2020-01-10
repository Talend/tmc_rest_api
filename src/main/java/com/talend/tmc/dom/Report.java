package com.talend.tmc.dom;

import lombok.Data;

@Data
public class Report {
    private String status;
    private Message[] messages;
}
