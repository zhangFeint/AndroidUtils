package com.library.utils.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.Log;


import com.library.utils.R;
import com.library.utils.httpservice.HttpRequestUtils;
import com.library.utils.httpservice.OkHttp3NetWork;
import com.library.utils.utils.SystemUtils;

import java.io.File;

/**
 * 软件下载安装
 * Intent intent = new Intent(this, DownLoadService.class);
 * intent.putExtra(DownLoadService.KEY_URL_SIGN, "http://ucan.25pp.com/Wandoujia_web_seo_baidu_homepage.apk");
 * intent.putExtra(DownLoadService.FILE_DOWNLOAD_TIBLE, "tible");
 * startService(intent);
 */
public class DownLoadService extends Service {
    private final String TAG = "DownLoadService";
    private Context mContext;
    public int notifyId ;//通知栏id

    private String tible = "";

    //数据传输的标记
    public static final String KEY_URL_SIGN = "url";
    public static final String KEY_TIBLE = "tible";
    public static final String KEY_ICON = "icon";
    public static final String KEY_NOTIFY_ID = "notifyID";//通知栏id

    public static void show(Context context, @DrawableRes int icon, String url, String tible, int notifyId) {
        Intent intent = new Intent(context, DownLoadService.class);
        intent.putExtra(KEY_URL_SIGN, url);
        intent.putExtra(KEY_TIBLE, tible == null ? "更新包下载中..." : tible);
        intent.putExtra(KEY_ICON, icon );
        intent.putExtra(KEY_NOTIFY_ID, notifyId == -1 ? 1000 : notifyId);
        context.startService(intent);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mContext = this;
        String url = intent.getStringExtra(KEY_URL_SIGN);
        int icon = intent.getIntExtra(KEY_ICON, -1);
        notifyId = intent.getIntExtra(KEY_NOTIFY_ID, 1000);
        tible = intent.getStringExtra(KEY_TIBLE);
        ProgressNotification.getInstance().initNotification(mContext, icon, tible, notifyId);
        OkHttp3NetWork.submitDownloadFile(this, url, HttpRequestUtils.FILE_SAVE_CATALOGUE, new HttpRequestUtils.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(File file) {
                Log.i(TAG, "请求成功");
                ProgressNotification.getInstance().cancelNotification();
                DownLoadService.this.stopSelf();
                SystemUtils.getInstance().installApk(mContext, file);  // 安装软件
            }

            @Override
            public void onDownloading(int progress) {
                ProgressNotification.getInstance().updateNotification(progress);
            }

            @Override
            public void onDownloadFailed() {
                Log.i(TAG, "请求失败");
                ProgressNotification.getInstance().cancelNotification();
            }
        });


        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
