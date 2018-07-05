package org.isegodin.expenses.adviser.telegram.service;

/**
 * @author i.segodin
 */
public interface JsonService {

    String toJson(Object o);

    <T> T fromJson(String json, Class<T> type);
}
