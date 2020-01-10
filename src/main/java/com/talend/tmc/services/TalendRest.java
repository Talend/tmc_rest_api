package com.talend.tmc.services;

public interface TalendRest<T> {
    T get(String id);
}
