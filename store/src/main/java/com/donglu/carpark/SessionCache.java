package com.donglu.carpark;

import com.donglu.carpark.model.storemodel.SessionInfo;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import java.util.concurrent.TimeUnit;

/**
 * Created by xiaopan on 2016-01-05.
 */
public class SessionCache {

    final static Logger LOGGER = LoggerFactory.getLogger(SessionCache.class);

    final static Cache<String, SessionInfo> cache = CacheBuilder
            .newBuilder()
            .maximumSize(100)
            .expireAfterWrite(1, TimeUnit.DAYS)
            .build();

    public static void put(String id,SessionInfo object){
        cache.put(id,object);
    }

    public static SessionInfo get(HttpServletRequest req){
        String sessionId = "";
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName() != null && cookie.getName().equalsIgnoreCase("sessionId")){
                sessionId = cookie.getValue();
            }
        }
        if (sessionId.isEmpty()){
            return null;
        }
        return cache.getIfPresent(sessionId);
    }

}
