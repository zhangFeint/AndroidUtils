package com.library.utils.httpservice;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * 判断当前手机是否有网络连接
 */

public class OkHttp3NetWork {
    private static final String TAG = OkHttp3NetWork.class.getSimpleName();
    private static final String NO_NETWORK_TEXT = "   没有网络能干啥，\n去设置中开启网络吧!";
    private static final String LOADED_TIP = "资源加载中,请稍后...";

    /**
     * 提交数据,不带提示信息
     */
    @Deprecated
    public static void submitNoDialog(Context context, int requetWay, UploadDataAsyncTask.NetWorkInterface netWork) {
        initNetworkAvailable(context);
        submitData(requetWay, null, 25000,  netWork);
    }

    /**
     * 访问本地xml信息，用来做测试
     *
     * @param context
     * @param localFile 文件 "city.json" assets 包下（与res同级）
     * @param netWork
     * @throws Exception
     */
    public static void submitDialog(Context context, String localFile, UploadDataAsyncTask.NetWorkInterface netWork) throws Exception {
        initNetworkAvailable(context);
        netWork.result(HttpRequestUtils.getInstance().readJson(context, localFile));     //返回数据  跳到本页面的handleOrderList方法里面 146line
    }

    /**
     * 提交数据,不带提示信息
     */
    public static void submitDialog(Context context, int requetWay, UploadDataAsyncTask.NetWorkInterface netWork) {
        initNetworkAvailable(context);
        submitData(requetWay, null, 25000, netWork);
    }

    /**
     * 提交数据,带提示信息
     */
    public static void submitDialog(Context context, int requetWay, OnLoadListener control, UploadDataAsyncTask.NetWorkInterface netWork) {
        initNetworkAvailable(context);
        submitData(requetWay, control, 25000, netWork);
    }

    /**
     * 提交数据,OKhttp3 请求
     */
    public static void submitData(int requetWay, OnLoadListener control, int overtime, UploadDataAsyncTask.NetWorkInterface netWork) {
        UploadDataAsyncTask up = new UploadDataAsyncTask(netWork, control, overtime, requetWay);
        up.execute();
    }

    /**
     * 下载文件
     *
     * @param context
     * @param url      "http://ucan.25pp.com/Wandoujia_web_seo_baidu_homepage.apk"
     * @param listener
     */
    public static void submitDownloadFile(Context context, String url, String saveDir, HttpRequestUtils.OnDownloadListener listener) {
        initNetworkAvailable(context);
        new HttpRequestUtils().download(url, saveDir, listener);
    }


    /**
     * 检测当的网络（WLAN、3G/2G）状态 true 表示网络可用
     * @param context
     */
    public static void initNetworkAvailable(Context context) {
        if (!isNetworkAvailable(context)) {   // 如果手机中没有可用的连接，给出提示信息
            listener.Disnet();
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

    public static OnNetworkListener listener;

    /**
     * 网络监听
     *
     * @param onNetworkListener
     */
    public static void setOnNetworkListener(OnNetworkListener onNetworkListener) {
        listener = onNetworkListener;
    }

    public interface OnNetworkListener {
        void Disnet();
    }


}
