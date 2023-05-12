package com.ruoyi.bsapi.utils;

import java.util.Date;

/**
 * 时间工具类
 */
public class DateUtils {

    /**
     * 获取redis过期时间，已时、分、秒
     * @param time
     * @return
     */
    public static String getRedisExpire(Long time) {
       if (time < 0) {
           return null;
       }
        time = time * 1000;
        long hour = time/(60*60*1000);
        long minute = (time - hour*60*60*1000)/(60*1000);
        long second = (time - hour*60*60*1000 - minute*60*1000)/1000;
        return hour + ":" + minute + ":" + second;
    }

}
