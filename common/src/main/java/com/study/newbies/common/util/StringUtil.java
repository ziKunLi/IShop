package com.study.newbies.common.util;

/**
 *
 * @author NewBies
 * @date 2018/9/1
 */

public class StringUtil {

    public static boolean isNull(String value){
        return value == null||value.trim().equals("");
    }
}
