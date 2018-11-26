package com.library.utils.Service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.library.utils.httpservice.HttpRequestUtils;
import com.library.utils.httpservice.OkHttp3NetWork;
import com.library.utils.utils.SystemUtils;

import java.io.File;

/**
 * 软件下载安装
 * Intent intent = new Intent(this, DownLoadService.class);
 * intent.putExtra(DownLoadService.FILE_URL_SIGN, "http://ucan.25pp.com/Wandoujia_web_seo_baidu_homepage.apk");
 * intent.putExtra(DownLoadService.FILE_DOWNLOAD_TIBLE, "tible");
 * startService(intent);
 */
public class DownLoadService extends Service {
    private Context mContext;

    //通知栏
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;
    private int preProgress = 0;

    private String tible = "更新包下载中...";
    private int icon = -1;
    public int notify_id = 1000;//通知栏id
    //数据传输的标记
    public static final String FILE_URL_SIGN = "url";
    public static final String FILE_DOWNLOAD_TIBLE = "tible";
    public static final String FILE_NOTIFY_ID = "notifyID";//通知栏id

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mContext = this;
        String url = intent.getStringExtra(FILE_URL_SIGN);
        notify_id = intent.getIntExtra(FILE_NOTIFY_ID, 1000);
        tible = intent.getStringExtra(FILE_DOWNLOAD_TIBLE) == null ? tible : intent.getStringExtra(FILE_DOWNLOAD_TIBLE);
        tible = intent.getStringExtra(FILE_DOWNLOAD_TIBLE) == null ? tible : intent.getStringExtra(FILE_DOWNLOAD_TIBLE);
        initNotification(-1);
        OkHttp3NetWork.submitDownloadFile(this, url, HttpRequestUtils.FILE_SAVE_CATALOGUE, new HttpRequestUtils.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(File file) {
                Log.i("zs", "请求成功");
                cancelNotification();
                SystemUtils.getInstance().installApk(mContext, file);  // 安装软件
            }

            @Override
            public void onDownloading(int progress) {
                updateNotification(progress);
            }

            @Override
            public void onDownloadFailed() {
                Log.i("zs", "请求失败");
                cancelNotification();
            }
        });


        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    /**
     * 初始化Notification通知
     */
    public void initNotification(int icon) {
        builder = new NotificationCompat.Builder(mContext)
                .setContentText("0%")
                .setContentTitle(tible)
                .setProgress(100, 0, false);
        if (icon != -1) {
            builder.setSmallIcon(icon);
        }

        notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notify_id, builder.build());
    }

    /**
     * 更新通知
     */
    public void updateNotification(long progress) {
        int currProgress = (int) progress;
        if (preProgress < currProgress) {
            builder.setContentText(progress + "%");
            builder.setProgress(100, (int) progress, false);
            notificationManager.notify(notify_id, builder.build());
        }
        preProgress = (int) progress;
    }

    /**
     * 取消通知
     */
    public void cancelNotification() {
        notificationManager.cancel(notify_id);
        this.stopSelf();
    }
}
