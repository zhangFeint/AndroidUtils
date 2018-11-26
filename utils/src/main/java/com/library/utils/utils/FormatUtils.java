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

    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%常用的数字格式%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

    /**
     * 替换的字符串
     *
     * @param start   开始的位置
     * @param end     结束的位置
     * @param oldChar 原字符串
     * @param newChar 替换的字符
     * @return
     */
    public static String setReplace(int start, int end, String oldChar, String newChar) {
        return new StringBuilder(oldChar).replace(start, end, newChar).toString();
    }


    /**
     * 金钱格式化
     *
     * @param str
     * @return
     */
    public static String setMoneyFormat(String str) {
        return setMoneyFormat(Double.parseDouble(str));
    }

    /**
     * 金钱格式化
     *
     * @param doubles
     * @return
     */
    public static String setMoneyFormat(Double doubles) {
        return new DecimalFormat("0.00").format(doubles);
    }

    /**
     * 金钱格式化
     */
    public static String setMoneyFormat(float money) {
        String sMoney = new DecimalFormat("##0.00").format(money);
        return sMoney;
    }


    /**
     * 验证手机号
     *
     * @param mobiles
     * @return
     */
    public boolean isMobileNO(String mobiles) {
        return match("^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$", mobiles);
    }

    /**
     * 身份证号校验
     */
    public boolean isIDcard(String str) {
        String regex = "(^d{15}$)|(^d{17}([0-9]|X)$)";
        return match(regex, str);
    }


    /**
     * 校验 银行卡卡号
     */
    public boolean checkBankCard(String cardId) {
        return match("/^([1-9]{1})(\\d{14}|\\d{18})$/", cardId);
    }

    /**
     * 固定电话校验
     *
     * @param phone 0371 -
     * @return
     */
    public boolean isFixedTelephonee(String phone) {
        String regex = "^0\\d{2,3}(\\-)?\\d{7,8}$";
        return match(regex, phone);
    }

    /**
     * 判断email格式是否正确
     *
     * @param email 69558409@qq.com
     * @return
     */
    public boolean isEmail(String email) {
        String regex = "^[a-z0-9A-Z]+[- | a-z0-9A-Z . _]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}$";
        return match(regex, email);
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


    /**
     * 手机号 用空格断开
     *
     * @param editText
     */
    public static void setPhoneFormat(final EditText editText) {
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
    public static void setBankCardFormat(final EditText editText) {
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

}
