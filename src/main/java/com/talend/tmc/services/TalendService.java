package com.talend.tmc.services;

import com.talend.tmc.services.executables.ExecutableService;

public interface TalendService<T> {
    default T instance(TalendCredentials credentials, TalendCloudRegion region) {
        if (region == null) throw new NullPointerException("TalendCloudRegion cannot be null");
        if (credentials == null) throw new NullPointerException("TalendCredentials cannot be null");
//TODO
        return null;
    }
}
