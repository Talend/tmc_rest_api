package com.talend.tmc.services;

public class TalendRestException extends Exception {
    public TalendRestException(int errorCode, String message)
    {
        super(errorCode + "::" + message);
    }
}
