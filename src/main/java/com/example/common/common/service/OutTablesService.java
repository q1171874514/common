package com.example.common.common.service;

import java.util.Map;

public interface OutTablesService<D> {
    D addOutTablesField(D dto, Map<String, Map> outTablesEntityMap);
}
