package com.ruoyi.bsapi.test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestMain {

    public static void main(String[] args) {
        Long time = 1484L * 1000;
        //long time = 3600000+366000;//1m = 1000毫秒
        //Date date = new Date(time);

        //long hour = time/(60*60*1000);
        //long minute = (time - hour*60*60*1000)/(60*1000);
        //long second = (time - hour*60*60*1000 - minute*60*1000)/1000;
        //System.out.println(hour+ "时" + minute + "分 " + second+"秒");

        String str = "佳龙亲亲嘴辣条大刀肉经典儿时麻辣烧零食吃货解馋小包装休闲食品";
        String s = "麻辣";
        System.out.println(str.indexOf(s));
        System.out.println(s.length());
        System.out.println(str.replace("麻辣", "<b>麻辣</b>"));
    }

}
