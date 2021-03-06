package com.library.utils.utils;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
     * 正则表达式：验证用户名  首字母+数字
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,20}$";

    /**
     * 正则表达式：验证密码 密码不能含有非法字符，长度在6-20之间
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
//    private static final String REGEX_ID_CARD = "(^d{15}$)|(^d{17}([0-9]|X)$)";//身份证 （正则表达式）
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
    public String setMoneyFormat(String str) {
        if (isNumeric(str)){
            return setMoneyFormat(Double.parseDouble(str));
        }
        return str;
    }

    /**
     * 金钱格式化
     *
     * @param doubles
     * @return
     */
    public String setMoneyFormat(Double doubles) {
        return new DecimalFormat("0.00").format(doubles);
    }

    /**
     * 金钱格式化
     */
    public String setMoneyFormat(float money) {
        return  new DecimalFormat("##0.00").format(money);
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
    public boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 银行卡号 用空格断开
     *
     * @param idCard "1234567890123454"
     * @return 1234 5678 9012 3454
     */
    public String getRegexBankCard(String idCard) {
        return idCard.replaceAll("\\d{4}(?!$)", "$0 ");
    }

    /**
     * 手机号 用空格断开
     *
     * @param phone "13674928326"
     * @return 136 7492 8326
     */
    public String getRegexPhone(String phone) {
        return phone.replaceAll("(1\\w{2})(\\w{4})(\\w{4})", "$1 $2 $3");
    }


    /**
     * 手机号 用空格断开
     *
     * @param editText
     */
    public void setPhoneFormat(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s == null || s.length() == 0) return;
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < s.length(); i++) {
                    if (i != 3 && i != 8 && s.charAt(i) == ' ') {
                        continue;
                    } else {
                        sb.append(s.charAt(i));
                        if ((sb.length() == 4 || sb.length() == 9) && sb.charAt(sb.length() - 1) != ' ') {
                            sb.insert(sb.length() - 1, ' ');
                        }
                    }
                }
                if (!sb.toString().equals(s.toString())) {
                    int index = start + 1;
                    if (sb.charAt(start) == ' ') {
                        if (before == 0) {
                            index++;
                        } else {
                            index--;
                        }
                    } else {
                        if (before == 1) {
                            index--;
                        }
                    }
                    editText.setText(sb.toString());
                    editText.setSelection(index);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**
     * 银行卡号 用空格断开
     *
     * @param editText
     */
    public void setBankCardFormat(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s == null || s.length() == 0) return;
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < s.length(); i++) {
                    if (i != 4 && i != 9 && i != 14 && i != 19 && s.charAt(i) == ' ') {
                        continue;
                    } else {
                        sb.append(s.charAt(i));
                        if ((sb.length() == 5 || sb.length() == 10 || sb.length() == 15 || sb.length() == 20) && sb.charAt(sb.length() - 1) != ' ') {
                            sb.insert(sb.length() - 1, ' ');
                        }
                    }
                }
                if (!sb.toString().equals(s.toString())) {
                    int index = start + 1;
                    if (sb.charAt(start) == ' ') {
                        if (before == 0) {
                            index++;
                        } else {
                            index--;
                        }
                    } else {
                        if (before == 1) {
                            index--;
                        }
                    }
                    editText.setText(sb.toString());
                    editText.setSelection(index);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    /**
     * 替换的字符串
     *
     * @param start   开始的位置  3
     * @param end     结束的位置  7
     * @param oldChar 原字符串   "13674928326"
     * @param newChar 替换的字符  "****"
     * @return 136****8326
     */
    public String setReplace(int start, int end, String oldChar, String newChar) {
        return new StringBuilder(oldChar).replace(start, end, newChar).toString();
    }
    /**
     * 最小几位数  001
     *
     * @param num 1
     * @param length    3
     */
    public String getMinimum(int num, int length) {
        String tempNum = num + "";
        while (tempNum.length() < length) {
            tempNum = "0" + tempNum;
        }
        return tempNum;
    }
    /**
     *  百分比计算
     * @param num    1
     * @param total 3   总数
     * @param scale 1  位数
     * @return  33.3%
     */
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public  float accuracy(double num, double total, int scale){
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
        df.setMaximumFractionDigits(scale); //可以设置精确几位小数
        df.setRoundingMode(RoundingMode.HALF_UP);  //模式 例如四舍五入
        float accuracy_num = (float) (num / total * 100);
        return accuracy_num;
    }
    /**
     * 判断字符串是否为数字
     */
    public boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
