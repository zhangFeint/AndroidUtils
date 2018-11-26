package com.library.utils.utils;

import android.app.Activity;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

// TODO:  一个例子
@Deprecated
public class MySharedPreferences {
    private static final String TAG = "MySharedPreferences";
    private SharedPreferences sp;
    SharedPreferences.Editor editor;
    public static final String MY_PREFS = "MyPrefs";
    public static final String VERSION = "Version";

    /**
     * @param activity
     * @param name
     */
    public MySharedPreferences(Activity activity, String name) {
        sp = activity.getSharedPreferences(name, Activity.MODE_PRIVATE);
        editor = sp.edit();
    }


    public void MySharedPreferences(Activity activity, String SharedPreferencesName, String strObject) {
        SharedPreferences sp = activity.getSharedPreferences(SharedPreferencesName, Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("strObject", strObject);
        edit.commit();
    }


    /**
     * 首选项保存
     *
     * @param k
     * @param v
     */
    public void setData(String[] k, String[] v) {
        for (int i = 0; i < k.length; i++) {
            editor.putString(k[i], v[i]);
        }
    }


    public void setString(String k, String v) {
        editor.putString(k, v);
    }

    public void setIntger(String k, int v) {
        editor.putInt(k, v);
    }

    public void setBoolean(String k, boolean v) {
        editor.putBoolean(k, v);
    }

    public void commit() {
        editor.commit();
    }

    /**
     * 定义一个保存数据的方法
     *
     * @param map
     */
    public void saveMap(HashMap<String, String> map) {
        for (String key : map.keySet()) {
            //map.keySet()返回的是所有key的值
            String value = map.get(key);//得到每个key多对用value的值
            setString(key, value);

        }
        System.out.println(TAG + "：信息已写入SharedPreference中");
    }


    /**
     * 首选项  读取
     *
     * @param keyArr
     * @return
     */
    public Map<String, String> readMap(String[] keyArr) {
        Map<String, String> map = new HashMap<>();
        for (String key : keyArr) {
            map.put(key, sp.getString(key, ""));
        }
        return map;
    }

    public String getString(String k) {
        return sp.getString(k, "");
    }

    public int getIntger(String k) {
        return sp.getInt(k, 0);
    }

    public boolean getBoolean(String k) {
        return sp.getBoolean(k, true);
    }

}
