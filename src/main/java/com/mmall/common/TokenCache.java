package com.mmall.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by zhengquan on 2017/5/28.
 */
public class TokenCache {

    private static Logger logger = LoggerFactory.getLogger(TokenCache.class);

    public static final String TOKEN_PREFIX = "token_";

    //LRU算法
    private static LoadingCache<String,String> loadingCache = CacheBuilder.newBuilder().initialCapacity(1000).maximumSize(1000)
            .expireAfterAccess(12, TimeUnit.HOURS).build(new CacheLoader<String, String>() {
                //默认的数据加载实现，当调用get方法取值的时候，没有key对应的值时，就调用这个方法进行加载。
                @Override
                public String load(String s) throws Exception {
                    return "null";
                }
            });

    public static void setKey(String key,String value){
        loadingCache.put(key,value);
    }

    public static String getKey(String key){
        String value = null;
        try {
            value = loadingCache.get(key);
            if ("null".equals(value)) {
                return null;
            }
        }catch (Exception e){
            logger.error("localcache get error",e);

        }
        return value;
    }

}
