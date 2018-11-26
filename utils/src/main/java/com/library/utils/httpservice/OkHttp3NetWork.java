package com.library.utils.httpservice;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;



/**
 * 判断当前手机是否有网络连接
 */

public class OkHttp3NetWork {
    private static final String TAG = OkHttp3NetWork.class.getSimpleName();
    private static final String NO_NETWORK_TEXT = "   没有网络能干啥，\n去设置中开启网络吧!";
    private static final String LOADED_TIP = "资源加载中,请稍后...";


    /**
     * 提交数据,带提示信息
     */
    public static void submitNoDialog(Context context, int requetWay, UploadDataAsyncTask.NetWorkInterface netWork) {
        initNetworkAvailable(context);
        UploadDataAsyncTask up = new UploadDataAsyncTask(context, LOADED_TIP, netWork,null, 25000, false, requetWay);
        up.execute();
    }

    /**
     * 访问本地xml信息，用来做测试
     *
     * @param context
     * @param localFile 文件 "city.json" assets 包下（与res同级）
     * @param netWork
     * @throws Exception
     */
    public static void submitNoDialog(Context context, String localFile, UploadDataAsyncTask.NetWorkInterface netWork) throws Exception {
        initNetworkAvailable(context);
        netWork.result(HttpRequestUtils.readJson(context,localFile));     //返回数据  跳到本页面的handleOrderList方法里面 146line
    }


    /**
     * 下载文件
     *
     * @param context
     * @param url      "http://ucan.25pp.com/Wandoujia_web_seo_baidu_homepage.apk"
     * @param listener
     */
    public static void submitDownloadFile(Context context, String url, String saveDir,HttpRequestUtils.OnDownloadListener listener) {
        initNetworkAvailable(context);
       new  HttpRequestUtils().download(url,saveDir,listener);
    }




    /**
     * 检测当的网络（WLAN、3G/2G）状态 true 表示网络可用
     *
     * @param context
     */
    public static void initNetworkAvailable(Context context) {
        if (!isNetworkAvailable(context)) {   // 如果手机中没有可用的连接，给出提示信息
            Toast.makeText(context, NO_NETWORK_TEXT, Toast.LENGTH_LONG).show();
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
