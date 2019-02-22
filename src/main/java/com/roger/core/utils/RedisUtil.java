package com.roger.core.utils;

import com.roger.core.constant.SeparatorConstant;

import java.util.ArrayList;
import java.util.List;

public class RedisUtil {

    public static String buildKey(String business,String key){
        return new StringBuilder(business).append(SeparatorConstant.COLON).append(key).toString();
    }

    public static List<String> buildKeys(String business, String ... keys){
        List<String> nodeKeyList = new ArrayList<>(keys.length);
        for(String key : keys){
            nodeKeyList.add(buildKey(business,key));
        }
        return nodeKeyList;
    }

    public static byte[] buildKey(String business,byte[] key){
        byte[] busi = (business + SeparatorConstant.COLON).getBytes();
        byte[] bytes = new byte[busi.length + key.length];
        System.arraycopy(busi, 0, bytes, 0, busi.length);
        System.arraycopy(key, 0, bytes, busi.length, key.length);
        return bytes;
    }

}
