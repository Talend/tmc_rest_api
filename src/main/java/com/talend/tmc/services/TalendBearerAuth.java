package com.talend.tmc.services;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TalendBearerAuth extends TalendCredentialsImpl {
    private final String bearerToken;

    @Override
    public String getBearerToken() {
        return this.bearerToken;
    }
}
