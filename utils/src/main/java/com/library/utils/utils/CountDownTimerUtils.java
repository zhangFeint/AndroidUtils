package com.library.utils.utils;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;


/**
 * 验证码的倒计时
 * 使用方式：
 * new CountDownTimerUtils(textView, 60000, 1000).start();
 */
public class CountDownTimerUtils {


    private CountDownTimer countDownTimer;
    private static CountDownTimerUtils countDownTimerUtils;

    /**
     * 单例模式
     */
    public static CountDownTimerUtils getInstance() {
        if (countDownTimerUtils == null) {
            countDownTimerUtils = new CountDownTimerUtils();
        }
        return countDownTimerUtils;
    }


    /**
     * 验证码倒计时
     *
     * @param tvCode
     * @param timingTime   60000
     * @param intervalTime 1000
     */
    public void startCountdown(final TextView tvCode, long timingTime, long intervalTime, final int defaultBackground, final int RunningBackground) {
        countDownTimer = new CountDownTimer(timingTime, intervalTime) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvCode.setClickable(false);
                tvCode.setText(millisUntilFinished / 1000 + "s重新发送");
                tvCode.setBackgroundResource(RunningBackground); //设置按钮为灰色，这时是不能点击的
                SpannableString spannableString = new SpannableString(tvCode.getText().toString());  //获取按钮上的文字
                ForegroundColorSpan span = new ForegroundColorSpan(Color.RED);
                spannableString.setSpan(span, 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//将倒计时的时间设置为红色
                tvCode.setText(spannableString);
            }

            @Override
            public void onFinish() {
                tvCode.setText("重新获取");
                tvCode.setClickable(true);
                tvCode.setBackgroundResource(defaultBackground);  //还原背景色
            }
        }.start();
    }


    /**
     * 页面销毁前的清理工作
     */
    public void onDestroy() {
        //销毁计时器
        if (!StringUtil.getInstance().isEmpty(countDownTimer)) {
            countDownTimer.cancel();
            countDownTimer.onFinish();
            countDownTimer = null;
        }
    }

}
