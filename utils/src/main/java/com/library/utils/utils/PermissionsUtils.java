package com.library.utils.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.support.v4.content.FileProvider.getUriForFile;

/**
 * 权限管理辅助类
 */
public class PermissionsUtils {

    /**
     * 请求动态加载权限
     * <!--在SDCard中创建与删除文件权限  -->
     * <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>
     * <!-- 往SDCard写入数据权限 -->
     * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
     * <!--  从SDCard读取数据权限 -->
     * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
     */
    public static final String[] SD_CARD_PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    public static final String[] CAMERA_PERMISSIONS = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}; //相机权限
    public static final String[] SD_WRITE_READ_PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}; //往SDCard写入数据权限
    public static final String[] PHONE_PERMISSIONS = {Manifest.permission.CALL_PHONE};//打电话权限
    //权限请求码
    public static final int REQUEST_CODE_PERMISSION = 1010;

    // 声明一个集合，在后面的代码中用来存储用户拒绝授权的权
    static List<String> mPermissionList;//没有授权的权限
    static List<String> PermnList;//授权失败的集合

    /**
     * 动态申请权限(在onCreate里使用，配合回调)
     *
     * @param activity             上下文
     * @param permissions          权限数组
     * @param requestCode          权限请求码
     * @param onPermissionListener 接口返回
     */
    public static void requestPermission(Activity activity, String[] permissions, int requestCode, OnPermissionListener onPermissionListener) {
        mPermissionList = new ArrayList<>();
        PermnList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                    mPermissionList.add(permission); //获取没有授权的权限
                }
            }
            if (!mPermissionList.isEmpty()) {
                ActivityCompat.requestPermissions(activity, mPermissionList.toArray(new String[mPermissionList.size()]), requestCode); //请求授权
            }
            for (String perm : mPermissionList) { //验证是否许可权限
                if (ActivityCompat.checkSelfPermission(activity, perm) != PackageManager.PERMISSION_GRANTED) {
                    PermnList.add(perm);
                }
            }
        }
        if (PermnList.isEmpty()) {//未授予的权限为空，表示都授予了
            onPermissionListener.onSucceed();//授权成功
        } else {
            onPermissionListener.onFailure();//授权失败
        }
    }


    /**
     * 动态申请回调（在onRequestPermissionsResult里使用，配合动态申请）
     *
     * @param grantResults
     * @param onPermissionListener 接口返回
     */
    public static void isPermissionsResult(int[] grantResults, OnPermissionListener onPermissionListener) {
        if (grantResults.length > 0) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    onPermissionListener.onSucceed();//某一个权限被拒绝了
                    return;
                }
            }
            onPermissionListener.onSucceed();
        }
    }


    /**
     * 适配了android7 的uri文件暴露权限
     *
     * @param context
     * @param picturefile
     * @return
     */
    public static Uri getFileUriPermission(Context context, File picturefile) {
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//7.0及以上
            uri = getUriForFile(context, context.getPackageName() + ".fileprovider", picturefile);
            context.grantUriPermission(context.getPackageName(), uri, Intent.FLAG_GRANT_READ_URI_PERMISSION); //申请url暴露权限
        } else {//7.0以下
            uri = Uri.fromFile(picturefile);
        }
        return uri;//文件对外暴露权限
    }

    /**
     * 接口
     */
    public interface OnPermissionListener {
        void onSucceed();

        void onFailure();
    }

}
