package com.library.utils;


import com.library.utils.utils.TimeUtils;

/**
 * 功能：
 *
 * @author：zhangerpeng
 * @create：2018\12\6 0006 16:00
 * @version：2018 1.0
 * Created with IntelliJ IDEA
 */
public class TextDemo {
    public static void main(String[] args) {
//        TimeUtils timeUtils = TimeUtils.getInstance();
//        String longtime = timeUtils.getTimestamp("2018-12-9 06:10:12", TimeUtils.TIME_FORMAT_2);
//        System.out.println(longtime);
//        System.out.println(timeUtils.getTimeStateNew(longtime, TimeUtils.TIME_FORMAT_10));
       TimeUtils.TimeBean timeBean =  TimeUtils.getInstance().getStringData();
        System.out.println(timeBean.getDay());
    }

}
