package com.library.utils.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.library.utils.Service.DownLoadService;


/**
 * 功能：版本管理
 *
 * @author：zhangerpeng
 * @create：2018\10\10 0010 13:40
 * @version：2018 1.0
 * Created with IntelliJ IDEA
 */
public class VersionControl {
    private Context context;
    private final String VERSION_CODE = "VersionCode";
    private final String STORAGE_NAME = "Version";
    private  String dialog_tible = "更新提示";
    private  String dialog_affirm = "立即更新";
    private  String dialog_cancel = "以后更新";
    public VersionControl(Context context) {
        this.context = context;
    }


    /**
     * 版本升级，带拒绝后不会弹出功能的对话框
     *
     * @param nVersion 最新的版本号
     * @param message  升级的内容
     * @param url      apk文件的url
     */
    public void updateRecordVersionDialog(final int nVersion, String message, final String url){
            SharedPreferences sharedPreferences = context.getSharedPreferences(STORAGE_NAME, context.MODE_PRIVATE);
            final SharedPreferences.Editor editor = sharedPreferences.edit();
            int saveVersion = sharedPreferences.getInt(VERSION_CODE, getAppVersionCode(context));
            if (nVersion > saveVersion) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(false);
                builder.setTitle(dialog_tible);
                builder.setMessage(message);
                builder.setPositiveButton(dialog_affirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        editor.putInt(VERSION_CODE, nVersion);
                        editor.commit();
                        dialog.dismiss();
                        startDownload(url);
                    }
                });
                builder.setNegativeButton(dialog_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        editor.putInt(VERSION_CODE, nVersion);
                        editor.commit();
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }

    }

    /**
     * 版本升级对话框
     *
     * @param message 升级的内容
     * @param url     apk文件的url
     */
    public void updateVersionDialog(String message, final String url) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle(dialog_tible);
        builder.setMessage(message);
        builder.setPositiveButton(dialog_affirm, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                startDownload(url);
            }
        });
        builder.setNegativeButton(dialog_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


    private void startDownload(String url){
        Intent intent = new Intent(context, DownLoadService.class);
        intent.putExtra(DownLoadService.FILE_URL_SIGN, url);
        context.startService(intent);
    }



    /**
     * 返回当前程序版本号
     */
    public int getAppVersionCode(Context context) {
        int versioncode = 0;
        try {
            versioncode = getVersionInformation(context).versionCode;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versioncode;
    }

    /**
     * 返回当前程序版本名
     */
    public String getAppVersionName(Context context) {
        String versionName = null;
        try {
            versionName = getVersionInformation(context).versionName;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    /**
     * 本软件的版本信息
     *
     * @return
     */
    private PackageInfo getVersionInformation(Context context) throws PackageManager.NameNotFoundException {
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
        return pi;
    }


}
