package com.library.utils;


import android.widget.Toast;

import com.library.utils.httpservice.HttpRequestUtils;
import com.library.utils.httpservice.OkHttp3NetWork;
import com.library.utils.httpservice.SubmitData;
import com.library.utils.httpservice.UploadDataAsyncTask;
import com.library.utils.utils.FormatUtils;
import com.library.utils.utils.Jwt;
import com.library.utils.utils.StringUtil;
import com.library.utils.utils.TimeUtils;
import com.library.utils.utils.VersionControl;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
//       TimeUtils.TimeBean timeBean =  TimeUtils.getInstance().getStringData();
//        System.out.println(timeBean.getDay());
//
//        System.out.println(StringUtil.getInstance().setReplace(3, 7, "13674928326", "****"));
//        System.out.println(StringUtil.getInstance().getRandomString("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789", 10));
//
//        System.out.println(StringUtil.getInstance().getGUID());
//        System.out.println(FormatUtils.getInstance().getRegexBankCard( "1234567890123454"));
//        System.out.println(FormatUtils.getInstance().getRegexPhone( "13674928326"));

        Map<String, Object> payload = new HashMap<String, Object>();
        Date date = new Date();
        payload.put("uid", "291969452");//用户id
        payload.put("iat", date.getTime());//生成时间
        payload.put("ext", date.getTime() + 1000 * 60 * 60);//过期时间1小时
        String token = null;
        token = Jwt.createToken(payload);
        System.out.println("args = [" + token + "]");
        System.out.println(Jwt.validToken(token));

        System.out.println(  StringUtil.getInstance().getRandomString("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789",2));
        System.out.println("args = " + FormatUtils.getInstance().getMinimum(12, 5));
        System.out.println(TimeUtils.getInstance().getTimeForFormat("2018-06-02", TimeUtils.TIME_FORMAT_10, "MM"));
    }

}
