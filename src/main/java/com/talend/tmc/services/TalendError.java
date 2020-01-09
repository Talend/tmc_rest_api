package com.talend.tmc.services;

import lombok.Data;

@Data
public class TalendError {
    private String status;
    private String message;
    private String details;
    private String code;
    private String url;
    private String requestId;
}
