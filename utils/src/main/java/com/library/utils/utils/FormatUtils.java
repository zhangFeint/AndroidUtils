package com.library.utils.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 格式规范
 */

public class FormatUtils {
    private static FormatUtils formatUtils;

    /**
     * 单例模式
     */
    public static FormatUtils getInstance() {
        if (formatUtils == null) {
            formatUtils = new FormatUtils();
        }
        return formatUtils;
    }

    /**
     * 银行卡
     */
    public static final String REGEX_BANK_CARD = "/^([1-9]{1})(\\d{14}|\\d{18})$/";
    /**
     * email格式
     */
    public static final String REGEX_EMAIL = "^[a-z0-9A-Z]+[- | a-z0-9A-Z . _]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}$";
    /**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,20}$";

    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,20}$";

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";


    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";
//    //身份证 （正则表达式）
//    private static final String REGEX_ID_CARD = "(^d{15}$)|(^d{17}([0-9]|X)$)";
    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
    /**
     * 正则表达式：固话
     */
    public static final String REGEX_FIX_PHONE = "^0\\d{2,3}(\\-)?\\d{7,8}$";
    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%常用的数字格式%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%




    /**
     * 金钱格式化
     *
     * @param str
     * @return
     */
    public  String setMoneyFormat(String str) {
        return setMoneyFormat(Double.parseDouble(str));
    }

    /**
     * 金钱格式化
     *
     * @param doubles
     * @return
     */
    public  String setMoneyFormat(Double doubles) {
        return new DecimalFormat("0.00").format(doubles);
    }

    /**
     * 金钱格式化
     */
    public  String setMoneyFormat(float money) {
        String sMoney = new DecimalFormat("##0.00").format(money);
        return sMoney;
    }

    /**
     * 验证
     *
     * @param str
     * @return
     */
    public boolean isVerify(String str, String regex) {
        return match(regex, str);
    }

    /**
     * 匹配正则表达式
     *
     * @param regex 正则
     * @param str   字符串
     * @return
     */
    private boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }



}
