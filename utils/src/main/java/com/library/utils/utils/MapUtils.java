package com.library.utils.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能：
 *
 * @author：zhangerpeng
 * @create：2018\8\29 0029 9:31
 * @version：2018 1.0
 * Created with IntelliJ IDEA
 */
public class MapUtils {

    private static String packageName_baidu = "com.baidu.BaiduMap";
    private static String packageName_GaoDe = "com.autonavi.minimap";
    private static String packageName_tencent = "com.tencent.map";
    private static String packageName_Google = "com.google.android.apps.maps";
    private static String tencent_label = "qqmap://map/marker?marker=coord:%1$s,%2$s;";

    private static MapUtils mapUtils;

    /**
     * 单例模式
     */
    public static MapUtils getInstance() {
        if (mapUtils == null) {
            mapUtils = new MapUtils();
        }
        return mapUtils;
    }

    /**
     * @param dlat 38.899533
     * @param dlon -77.036476
     * @return 0 、通用  1、谷歌地图 2、百度 3、高德
     */
    public static String getMapUri(int type, String dlat, String dlon) {
        String uri = null;
        switch (type) {
            case 0:
                uri = "geo:%1$s,%2$s";
                break;
            case 1:
                uri = "google.navigation:q=%1$s,%2$s";
                break;
            case 2:
                uri = "baidumap://map?src=andr.baidu.openAPIdemo";
                return uri;
            case 3:
                uri = "androidamap://myLocation?sourceApplication=softname";
                break;
            case 4:
                uri = "qqmap://map/";  // 启动路径规划页面
                break;
        }
        return String.format(uri, dlat, dlon);
    }


    /**
     *  进行导航
     *
     * @param dlat
     * @param dlon
     * @param dname
     * @return
     */
    public static String getBaiduMapUri(String dlat, String dlon, String dname) {
        String uri = "baidumap://map/marker?location=%1$s,%2$s&src=andr.baidu.openAPIdemo";
        return String.format(uri, dlat, dlon);
    }

    /**
     * 启动腾讯地图App进行导航
     *
     * @param address 目的地
     * @param lat     必填 纬度
     * @param lon     必填 经度
     */
    public static String getTengxunMapUri(String address, double lat, double lon) {
        String uri = "qqmap://map/routeplan?type=drive&from=&fromcoord=CurrentLocation&to=%1$s&tocoord=%2$s,%3$s&policy=0&referer=appName";  // 启动路径规划页面
        return String.format(uri, address, lat, lon);
    }

    /**
     * 获取打开高德地图应用uri
     * style 导航方式(0 速度快; 1 费用少; 2 路程短; 3 不走高速；4 躲避拥堵；5不走高速且避免收费；6 不走高速且躲避拥堵；7 躲避收费和拥堵；8 不走高速躲避收费和拥堵)
     */
    public static String getGdMapUri(String appName, String dlat, String dlon, String dname) {
        //uri.amap.com/navigation?from=116.478346,39.997361&to=116.3246,39.966577&via=&mode=&policy=&src=&coordinate=&callnative=
        String uri = "androidamap://navi?sourceApplication=%1$s&poiname=%2$s&lat=%3$s&lon=%4$s&dev=1&style=2";
        return String.format(uri, appName, dname, dlat, dlon);
    }

    /**
     * 获取打开高德地图应用uri
     *
     * @param appName
     * @param slat
     * @param slon
     * @param sname
     * @param dlat
     * @param dlon
     * @param dname
     * @return
     */
    public static String getGdMapUri(String appName, String slat, String slon, String sname, String dlat, String dlon, String dname) {
        String uri = "androidamap://route?sourceApplication=%1$s&slat=%2$s&slon=%3$s&sname=%4$s&dlat=%5$s&dlon=%6$s&dname=%7$s&dev=0&m=0&t=2";
        return String.format(uri, appName, slat, slon, sname, dlat, dlon, dname);
    }

    /**
     * 获取打开百度地图应用uri [http://lbsyun.baidu.com/index.php?title=uri/api/android]  导航
     *
     * @param originLat   起点     经度 此处不传值默认选择当前位置
     * @param originLon   起点     维度 此处不传值默认选择当前位置
     * @param originName  起点     名字
     * @param desLat      目的地   经度
     * @param desLon      目的地   维度
     * @param destination 目的地   名字
     * @param region      目的地   地区
     * @param src         慧医
     * @return
     */
    public static String getBaiduMapUri(String originLat, String originLon, String originName, String desLat, String desLon, String destination, String region, String src) {
        String uri = "intent://map/direction?origin=latlng:%1$s,%2$s|name:%3$s" +
                "&destination=latlng:%4$s,%5$s|name:%6$s&mode=driving&region=%7$s&src=%8$s#Intent;" +
                "scheme=bdapp;package=com.baidu.BaiduMap;end";
        return String.format(uri, originLat, originLon, originName, desLat, desLon, destination, region, src);
    }


    /**
     * 网页版百度地图 有经纬度
     *
     * @param originLat
     * @param originLon
     * @param originName  ->注：必填
     * @param desLat
     * @param desLon
     * @param destination
     * @param region      : 当给定region时，认为起点和终点都在同一城市，除非单独给定起点或终点的城市。-->注：必填，不填不会显示导航路线
     * @param appName
     * @return
     */
    public static String getWebBaiduMapUri(String originLat, String originLon, String originName, String desLat, String desLon, String destination, String region, String appName) {
        String uri = "http://api.map.baidu.com/direction?origin=latlng:%1$s,%2$s|name:%3$s" +
                "&destination=latlng:%4$s,%5$s|name:%6$s&mode=driving&region=%7$s&output=html" +
                "&src=%8$s";
        return String.format(uri, originLat, originLon, originName, desLat, desLon, destination, region, appName);
    }


    public void openMaps(Activity activity, String packageName, String dlat, String dlon) {
        if (packageName.equals(packageName_baidu)) {
            openMap(activity, packageName_baidu, getMapUri(2, dlat, dlon));
            return ;
        }
        if (packageName.equals(packageName_GaoDe)) {
            openMap(activity, packageName_GaoDe, getMapUri(3, dlat, dlon));
            return ;
        }
        if (packageName.equals(packageName_tencent)) {
            openMap(activity, packageName_tencent, getMapUri(4, dlat, dlon));
            return ;
        }
        openMap(activity, getMapUri(0, dlat, dlon));
    }


    /**
     * 打开地图
     *
     * @param activity
     * @param packageName
     * @param uri
     * @return
     */

    public boolean openMap(Activity activity, String packageName, String uri) {
        if (isAvilible(activity, packageName)) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(uri));
                intent.setPackage(packageName);
                activity.startActivity(intent); //启动调用
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return true;
            }
        }
        Toast.makeText(activity, " 启动'地图'异常！\n请检查是否安装了该应用.", Toast.LENGTH_SHORT).show();
        return true;

    }


    /**
     * 显示地图
     *
     * @param activity
     * @param gnote     getMapUri(0, dlat, dlon)
     */
    public void openMap(Activity activity, String gnote) {
        try {
            Toast.makeText(activity, "正在打开地图,请稍后...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(gnote));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(activity, " 启动'地图'异常！\n请检查是否安装了该应用.", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 下载app
     *
     * @param activity
     * @param packageName 包名
     */
    public void downloadApp(Activity activity, String packageName) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName));//显示手机上所有的market商店
        activity.startActivity(intent);
    }


    /**
     * 地图应用是否安装
     *
     * @return
     */

    public static boolean isMapInstalled(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

    /**
     * 检查手机上是否安装了指定的软件
     */
    public boolean isAvilible(Activity activity, String packageName) {
        final PackageManager packageManager = activity.getPackageManager(); //获取packagemanager
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0); //获取所有已安装程序的包信息
        List<String> packageNames = new ArrayList<String>(); //用于存储所有已安装程序的包名
        if (packageInfos != null) { //从pinfo中将包名字逐一取出，压入pName list中
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        return packageNames.contains(packageName);//判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
    }
}
