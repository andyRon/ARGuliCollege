package com.andyron.msmservice.service;

import java.util.Map;

/**
 * @author andyron
 **/
public interface MsmService {
    boolean send(Map<String, Object> param, String phone);
}
