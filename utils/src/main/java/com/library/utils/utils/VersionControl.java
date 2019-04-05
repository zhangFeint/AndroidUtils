package com.library.utils.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.library.utils.service.DownLoadService;


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
    private int versioncode = 0;//程序版本号
    private String versionName;//程序版本名
    private final String VERSION_CODE = "VersionCode";

    private String dialog_tible = "更新提示";
    private String dialog_affirm = "立即更新";
    private String dialog_cancel = "以后更新";

    public VersionControl(Context context) {
        this.context = context;
    }

//    /**
//     * 版本升级，带拒绝后不会弹出功能的对话框
//     *
//     * @param nVersion 最新的版本号
//     * @param message  升级的内容
//     * @param url      apk文件的url
//     */
//    public void updateRecordVersionDialog(final int nVersion, String message, final String url) {
//        final SaveDataUtils saveData = new SaveDataUtils((Activity) context, SaveDataUtils.SAVE_VERSION);
//        int saveVersion = saveData.getInt(VERSION_CODE, getAppVersionCode(context));
//        saveData.setInt(VERSION_CODE, getAppVersionCode(context));
//        if (nVersion > saveVersion) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            builder.setCancelable(false);
//            builder.setTitle(dialog_tible);
//            builder.setMessage(message);
//            builder.setPositiveButton(dialog_affirm, new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//                    saveData.setInt(VERSION_CODE, nVersion);
//                    saveData.commit();
//                    dialog.dismiss();
//                    startDownload(url);
//                }
//            });
//            builder.setNegativeButton(dialog_cancel, new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//                    saveData.setInt(VERSION_CODE, nVersion);
//                    saveData.commit();
//                    dialog.dismiss();
//                }
//            });
//            builder.create().show();
//        }
//    }


    /**
     * 版本升级，带拒绝后不会弹出功能的对话框
     *
     * @param nVersion 最新的版本号
     * @param message  升级的内容
     * @param url      apk文件的url
     */
    public void updateRecordVersionDialog(final int nVersion, String message, final String url) {
        final SaveDataUtils saveData = new SaveDataUtils((Activity) context, SaveDataUtils.SAVE_VERSION);
        int saveVersion = saveData.getInt(VERSION_CODE, getAppVersionCode(context));
        saveData.setInt(VERSION_CODE, getAppVersionCode(context));
        if (nVersion > saveVersion) {
            updateVersionDialog(dialog_tible, message, url, new OnVersionDialogListener() {
                @Override
                public void onPositive() {
                    saveData.setInt(VERSION_CODE, nVersion);
                    saveData.commit();
                    startDownload(url);
                }

                @Override
                public void onNegative() {
                    saveData.setInt(VERSION_CODE, nVersion);
                    saveData.commit();
                }
            });
        }
    }

    /**
     * 版本升级对话框
     *
     * @param message 升级的内容
     * @param url     apk文件的url
     */
    public void updateVersionDialog(String message, final String url) {
        updateVersionDialog(dialog_tible, message, url, new OnVersionDialogListener() {
            @Override
            public void onPositive() {
                startDownload(url);
            }

            @Override
            public void onNegative() {

            }
        });
    }

    /**
     * 版本升级对话框
     *
     * @param message 升级的内容
     * @param url     apk文件的url
     */
    public void updateVersionDialog(String tible, String message, final String url, final OnVersionDialogListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle(tible);
        builder.setMessage(message);
        builder.setPositiveButton(dialog_affirm, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                listener.onPositive();
            }
        });
        builder.setNegativeButton(dialog_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                listener.onNegative();
            }
        });
        builder.create().show();
    }

    public interface OnVersionDialogListener {
        void onPositive();

        void onNegative();
    }


    private void startDownload(String url) {
        Intent intent = new Intent(context, DownLoadService.class);
        intent.putExtra(DownLoadService.KEY_URL_SIGN, url);
        context.startService(intent);
    }


    /**
     * 返回当前程序版本号
     */
    public int getAppVersionCode(Context context) {
        initVersionInformation(context);
        return versioncode;
    }

    /**
     * 返回当前程序版本名
     */
    public String getAppVersionName(Context context) {
        initVersionInformation(context);
        return versionName;
    }



    /**
     * 本软件的版本信息
     *
     * @return
     */
    private PackageInfo initVersionInformation(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            versioncode = pi.versionCode;
            return pi;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


}
