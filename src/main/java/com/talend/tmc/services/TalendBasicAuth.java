package com.talend.tmc.services;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TalendBasicAuth extends TalendAuthorizationImpl {
    private final String username;
    private final String password;


    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

}
