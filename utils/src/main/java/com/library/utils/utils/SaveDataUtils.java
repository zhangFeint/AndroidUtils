package com.library.utils.utils;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * 功能：
 *
 * @author：zhangerpeng
 * @create：2018\12\11 0011 14:14
 * @version：2018 1.0
 * Created with IntelliJ IDEA
 */
public class SaveDataUtils {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    public static final String SAVE_PERSON = "save_person";
    public static final String SAVE_VERSION = "save_version";
    public static final String KEY_DATA = "key_data";

    /**
     * @param activity
     * @param name
     */
    public SaveDataUtils(Activity activity, String name) {
        sp = activity.getSharedPreferences(name, Activity.MODE_PRIVATE);
        editor = sp.edit();
    }

    /**
     * 首选项保存
     */
    public void setObject(String key, Object strObject) throws Exception {
        editor.putString(key, SerializeUtils.serialize(strObject));
    }

    /**
     * 首选项保存
     */
    public void setInt(String key, int i) {
        editor.putInt(key, i);
    }

    /**
     * 首选项提交
     */
    public void commit() {
        editor.commit();
    }

    /**
     * 取出数据
     *
     * @param key
     * @return
     * @throws Exception
     */
    public Object getObject(String key) throws Exception {
        return SerializeUtils.deSerialization(sp.getString(key, null));
    }

    /**
     * 首选项保存
     */
    public int getInt(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    /**
     * 清空数据
     */
    public void clear() {
        editor.clear();
        editor.commit();
    }

}
