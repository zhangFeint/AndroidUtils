package com.library.utils.httpservice;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;


/**
 * 判断当前手机是否有网络连接
 */

public class OkHttp3NetWork {
    private static final String NO_NETWORK_TEXT = "   没有网络能干啥，\n去设置中开启网络吧!";

    /**
     * 访问本地xml信息，用来做测试
     *
     * @param context
     * @param localFile 文件 "city.json" assets 包下（与res同级）
     * @param netWork
     * @throws Exception
     */
    public static void submitDialog(Context context, String localFile, OnNetWorkInterface netWork) throws Exception {
        netWork.result(HttpRequestUtils.getInstance().readJson(context, localFile));     //返回数据  跳到本页面的handleOrderList方法里面 146line
    }

    /**
     * 提交数据,带提示信息
     *
     * @param context
     * @param requetWay  请求方式
     * @param onNetworkListener   断网接口
     * @param onLoadListener      加载弹框
     * @param netWork
     */
    public static void submitDialog(Context context, int requetWay, OnNetworkListener onNetworkListener, OnLoadListener onLoadListener, OnNetWorkInterface netWork) {
        submitData(context, requetWay, onNetworkListener, onLoadListener, 25000, netWork);
    }

    /**
     * 提交数据,OKhttp3 请求
     *
     * @param context
     * @param requetWay   请求方式
     * @param onNetworkListener  断网接口
     * @param onLoadListener     加载弹框
     * @param netWork
     */
    public static void submitData(Context context, int requetWay, OnNetworkListener onNetworkListener, OnLoadListener onLoadListener, int overtime, OnNetWorkInterface netWork) {
        initNetworkAvailable(context, onNetworkListener);
        UploadDataAsyncTask up = new UploadDataAsyncTask(netWork, onLoadListener, overtime, requetWay);
        up.execute();
    }

    /**
     * 下载文件
     *
     * @param context
     * @param url   "http://ucan.25pp.com/Wandoujia_web_seo_baidu_homepage.apk"
     * @param saveDir
     * @param onNetworkListener   断网接口
     * @param listener
     */
    public static void submitDownloadFile(Context context, String url, String saveDir, OnNetworkListener onNetworkListener, HttpRequestUtils.OnDownloadListener listener) {
        initNetworkAvailable(context, onNetworkListener);
        new HttpRequestUtils().download(url, saveDir, listener);
    }


    /**
     * 检测当的网络（WLAN、3G/2G）状态 true 表示网络可用
     *
     * @param context
     * @param onNetworkListener  断网接口
     */
    public static void initNetworkAvailable(Context context, OnNetworkListener onNetworkListener) {
        if (!isNetworkAvailable(context)) {   // 如果手机中没有可用的连接，给出提示信息
            if (onNetworkListener == null) {
                Toast.makeText(context, NO_NETWORK_TEXT, Toast.LENGTH_SHORT).show();
                return;
            }
            onNetworkListener.onNoNetwork();
            return;
        }
    }

    /**
     * 判断当前手机是否有网络连接, true, 有可用的网络连接；false,没有可用的网络连接
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (manager == null || info == null || !info.isAvailable()) {
            return false;
        }
        return true;
    }

}
