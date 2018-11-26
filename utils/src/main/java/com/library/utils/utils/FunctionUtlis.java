package com.library.utils.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;


/**
 * 功能模块
 */
public class FunctionUtlis {
    private static final String TAG = FunctionUtlis.class.getSimpleName();

    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% 启动页优先跳转 %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


    /**
     * 第一次运行跳转到引导页面
     *
     * @param context
     * @param listener
     */
    private static final String STORAGE_FIRST_RUN_WIZARD_NAME = "share";
    private static final String ISFIRSTRUN = "isFirstRun";
    public static void getFirstRun(Context context, OnSkipListener listener) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(STORAGE_FIRST_RUN_WIZARD_NAME, context.MODE_PRIVATE); //1、实例化首选项存储
        SharedPreferences.Editor editor = sharedPreferences.edit();  //2、使用edit()获取SharedPreferences.Editor对象，用来存储数据
        boolean isFirstRun = sharedPreferences.getBoolean(ISFIRSTRUN, true);  //3、获取首选项存储的数据，第一个是nome，第二个是默认值
        if (isFirstRun) {
            Log.i(TAG, "：首次运行");
            editor.putBoolean(ISFIRSTRUN, false); //5、进行首选项存储
            editor.commit(); //6、进行首选项提交数据
            listener.skipGuide(); // 第一次则跳转到引导页面
        } else {
            Log.i(TAG, "：多次运行");
            listener.skipMain(); //如果是第二次启动则直接跳转到主页面
        }
    }

    /**
     * 程序进入时，第一跳转欢迎页面
     */
    public interface OnSkipListener {
        void skipGuide();//跳转欢迎页面

        void skipMain();//跳转主页面

    }
    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%双击退出函数%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


    /**
     * 双击退出函数
     *
     * @param activity
     * @param type 1:返回桌面 2:直接退出 3：弹出对话框
     */
    private static Boolean isExit = false;

    public static void exitBy2Click(Activity activity, int type) {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(activity, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            switch (type) {
                case 1:
                    // 返回桌面操作
                    Intent home = new Intent(Intent.ACTION_MAIN);
                    home.addCategory(Intent.CATEGORY_HOME);
                    activity.startActivity(home);
                    break;
                case 2:
                    // 双击直接退出
                    System.exit(0);
                    break;
                case 3:
                    // 双击弹出对话框
                    displayDialog(activity);
                    break;
                default:
                    // 返回桌面操作
                    Intent home1 = new Intent(Intent.ACTION_MAIN);
                    home1.addCategory(Intent.CATEGORY_HOME);
                    activity.startActivity(home1);
                    break;
            }
        }
    }

    /**
     * 双击退出弹出对话框
     *
     * @param activity
     */
    public static void displayDialog(Activity activity) {
        new AlertDialog.Builder(activity).setTitle("确认退出程序吗？")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“确认”后的操作
                        System.exit(0);
                    }
                })
                .setNegativeButton("返回", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“返回”后的操作,这里不设置没有任何操作
                    }
                }).show();
    }

    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%登录页面记住密码功能%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

    private static SharedPreferences sharedPreferences;
    private static final String STORAGE_NAME = "Remember";

    private static final String SAVE_USER = "user";
    private static final String SAVE_PASSWORD = "password";

    /**
     * @param activity
     * @param isSave   保存账号密码： true  不保存： false
     * @param user
     * @param password
     */
    public static void saveAccount(Activity activity, boolean isSave, String user, String password) {
        sharedPreferences = activity.getSharedPreferences(STORAGE_NAME, activity.MODE_PRIVATE); //1、实例化首选项存储
        SharedPreferences.Editor editor = sharedPreferences.edit();  //2、使用edit()获取SharedPreferences.Editor对象，用来存储数据
        if (isSave) {
            editor.putString(SAVE_USER, user);
            editor.putString("", password);
            editor.commit(); //6、进行首选项提交数据
        } else {
            editor.putString(SAVE_USER, "");
            editor.putString(SAVE_PASSWORD, "");
            editor.commit(); //6、进行首选项提交数据
        }

    }

    public static String getAccounts() {
        return sharedPreferences == null ? "" : sharedPreferences.getString(SAVE_USER, "");
    }

    public static String getPasswords() {
        return sharedPreferences == null ? "" : sharedPreferences.getString(SAVE_PASSWORD, "");
    }


    /**
     *EditText提供了一个android:digits配置，它表示EditText能够接受的字符集合。
     * 场景一：只允许输入数字android:digits="0123456789"
     * 场景二：只允许输入数字和英文字母 android:digits="0123456789abcdefghigjklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
     * 场景三：只允许输入数字，英文字母和@.两个符号android:digits="0123456789abcdefghigjklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ@."
     *
     */

    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%改变矢量图形的颜色%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


}
