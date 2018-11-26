package com.library.utils.utils;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2018\1\22 0022.
 */

public class TimeUtils {
    private static TimeUtils timeUtils;
    public static final String TIME_FORMAT_1 = "yyyy年MM月dd日 HH:mm:ss";
    public static final String TIME_FORMAT_2 = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_FORMAT_3 = "MM月dd日 HH:mm:ss";
    public static final String TIME_FORMAT_4 = "MM-dd HH:mm:ss";
    public static final String TIME_FORMAT_5 = "MM月dd日 HH:mm";
    public static final String TIME_FORMAT_6 = "MM-dd HH:mm";
    public static final String TIME_FORMAT_7 = "yyyyMMddHHmmss";
    public static final String TIME_FORMAT_8 = "HH:mm:ss";
    public static final String TIME_FORMAT_10 = "yyyy-MM-dd";
    public static final String TIME_FORMAT_11 = "yyyy年MM月dd日";
    public static final String TIME_FORMAT_12 = "MM-dd";
    public static final String TIME_FORMAT_13 = "MM月dd日";

    /**
     * 单例模式
     */
    public static TimeUtils getInstance() {
        if (timeUtils == null) {
            timeUtils = new TimeUtils();
        }
        return timeUtils;
    }
    //********************************************************************日期与时间***************************************************************************************

    /**
     * 获取当前时间
     */
    public String getTime(String format) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            return formatter.format(new Date());
        } catch (Exception ex) {
        }
        return null;
    }

    public String getCurrentTime(String format) {
        try {
            SimpleDateFormat sf = new SimpleDateFormat(format, Locale.getDefault());
            return sf.format(new Date(System.currentTimeMillis()));
        } catch (Exception ex) {
        }
        return null;
    }

    /**
     * 获取当前时间戳
     */
    public long getTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * 将时间戳转为字符串 例如：  cc_time = 1291778220;
     */
    public String getStrTime(String cc_time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String re_StrTime = sdf.format(new Date(Long.valueOf(cc_time) * 1000L));
        return re_StrTime;
    }

    /**
     * 时间戳转字符串
     */
    public String getStrTime(long timeMillis, String format) {
        try {
            return new SimpleDateFormat(format, Locale.getDefault()).format(timeMillis - TimeZone.getDefault().getRawOffset());
        } catch (Exception ex) {
        }
        return null;
    }

    /**
     * 日期字符串转成指定格式的日期字符串 getTimeForFormat("2018-06-02",TimeUtils.TIME_FORMAT_10,TimeUtils.TIME_FORMAT_13)
     */
    public String getTimeForFormat(String str, String currentFormat, String format) {
        try {
            Date date = new SimpleDateFormat(currentFormat, Locale.getDefault()).parse(str.trim());
            return new SimpleDateFormat(format, Locale.getDefault()).format(date);
        } catch (Exception ex) {
        }
        return null;
    }

    /**
     * 字符串转日期getDate("2018-06-02",TimeUtils.TIME_FORMAT_10)
     */
    public Date getDate(String str, String format) {
        try {
            return new SimpleDateFormat(format, Locale.getDefault()).parse(str.trim());
        } catch (Exception ex) {
        }
        return null;
    }

    /**
     * 日期转字符串
     */
    public String getDate(Date data, String format) {
        try {
            return new SimpleDateFormat(format, Locale.getDefault()).format(data);
        } catch (Exception ex) {
        }
        return null;
    }

    /**
     * 获取年月份、星期
     */
    public String getStringData() {
        String mYear, mMonth, mDay, mWay;
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if ("1".equals(mWay)) {
            mWay = "天";
        } else if ("2".equals(mWay)) {
            mWay = "一";
        } else if ("3".equals(mWay)) {
            mWay = "二";
        } else if ("4".equals(mWay)) {
            mWay = "三";
        } else if ("5".equals(mWay)) {
            mWay = "四";
        } else if ("6".equals(mWay)) {
            mWay = "五";
        } else if ("7".equals(mWay)) {
            mWay = "六";
        }
        return mYear + "年" + mMonth + "月" + mDay + "日" + "/星期" + mWay;
    }

    /**
     * 获取星期
     */
    public String getDayOfWeek() {
        String mWay;
        final Calendar c = Calendar.getInstance();
        mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if ("1".equals(mWay)) {
            mWay = "天";
        } else if ("2".equals(mWay)) {
            mWay = "一";
        } else if ("3".equals(mWay)) {
            mWay = "二";
        } else if ("4".equals(mWay)) {
            mWay = "三";
        } else if ("5".equals(mWay)) {
            mWay = "四";
        } else if ("6".equals(mWay)) {
            mWay = "五";
        } else if ("7".equals(mWay)) {
            mWay = "六";
        }
        return mWay;
    }


    /**
     * 获取某月实际天数getActualDaysOfMonth(2020,2)
     */
    public int getActualDaysOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);//Java月份才0开始算
        return cal.getActualMaximum(Calendar.DATE);
    }

    /**
     * 日期（天）相加减dayCalculate("2018-06-02",2)
     */
    public String dayCalculate(String current, int count) {
        Date currentDate = getDate(current, "yyyy-MM-dd");  // 日期字符串转换为date类型
        Calendar mCalendar = Calendar.getInstance(); // 获取Calendar对象
        mCalendar.setTime(currentDate);  // 设置日期为当前已经选择的时间
        // 进行时间相加减操作
        mCalendar.add(Calendar.DAY_OF_MONTH, count);
        String date = getDate(mCalendar.getTime(), "yyyy-MM-dd");
        return date;
    }

    /**
     * 日期（月份）相加减monthCalculate("2018-06-02",2)
     */
    public String monthCalculate(String current, int count) {
        // 日期字符串转换为date类型
        Date currentDate = getDate(current, "yyyy-MM");
        // 获取Calendar对象
        Calendar mCalendar = Calendar.getInstance();
        // 设置日期为当前已经选择的时间
        mCalendar.setTime(currentDate);
        // 进行时间相加减操作
        mCalendar.add(Calendar.MONTH, count);
        String date = getDate(mCalendar.getTime(), "yyyy-MM");
        return date;
    }

    /**
     * 日期大小比较 date1大于date2返回1； date1小于date2返回-1 date1等于date2返回0
     * compareDate("2018-06-02","2018-06-04",TimeUtils.TIME_FORMAT_10)
     */
    public int compareDate(String date1, String date2, String format) {
        DateFormat df = new SimpleDateFormat(format);
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return 2;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * ————————————————————————————————————————————————————倒计时Dome ————————————————————————————————————————————————————
     *
     * @param args
     */
//
//    CountDownTimer timer = new CountDownTimer(240*1000, 1000) {
//        @Override
//        public void onTick(long millisUntilFinished) {
//            // TODO Auto-generated method stub
//            Toast.makeText(MainActivity.this, ""+ TimeUtils.getInstance().getStrTime(millisUntilFinished,TimeUtils.TIME_FORMAT_8), Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onFinish() {
//        }
//    }.start();
    public  Calendar calendar;
    public  Date date;
    public  long midTime;



    /**
     * 方式三： 使用java.util.Timer类进行倒计时
     */
    private void startCountdown(long startTime, long endTime) {
        midTime = (endTime - startTime) / 1000;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                midTime--;
                long hh = midTime / 60 / 60 % 60;
                long mm = midTime / 60 % 60;
                long ss = midTime % 60;
                System.out.println("还剩" + hh + "小时" + mm + "分钟" + ss + "秒");
            }
        }, 0, 1000);
    }

    /**
     * 方式二： 设定时间戳，倒计时
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @param countdownListener 返回接受
     */
    public  void startCountdown(long startTime, long endTime, OnCountdownListener countdownListener) {
        midTime = (endTime - startTime) / 1000;
        while (midTime > 0) {
            midTime--;
            long hh = midTime / 60 / 60 % 60;
            long mm = midTime / 60 % 60;
            long ss = midTime % 60;
            countdownListener.onListener(hh, mm, ss);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 方式一： 给定时长倒计时
     *
     * @param time 60 * 60 * 60
     */
    public void startCountdown(int time, OnCountdownListener countdownListener) {
        while (time > 0) {
            time--;
            try {
                Thread.sleep(1000);
                int hh = time / 60 / 60 % 60;
                int mm = time / 60 % 60;
                int ss = time % 60;
                System.out.println("还剩" + hh + "小时" + mm + "分钟" + ss + "秒");
                countdownListener.onListener(hh, mm, ss);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public interface OnCountdownListener {
         void onListener(long hh, long mm, long ss);
    }

}
