package com.library.utils.httpservice;


import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static java.lang.String.valueOf;


/**
 * 向服务端提交的数据<br>
 * 包括3部分内容：服务器连接部分信息（{@link}枚举）、提交请求字段信息（Map）、附件path列表信息（List）。
 * <li>需要上传文件时，加path
 *
 * @author redkid
 */
public class SubmitData {

    private String url;
    private HashMap<String, String> headers;
    private MultipartBody.Builder builder;
    private String charset = "utf-8";
    private RequestBody body;
    public static final int MODE_FORM = 0;
    public static final int MODE_JSON = 1;
    private int mode = 0;

    /**
     * @param url
     */
    public SubmitData(String url) {
        this.url = url;
        builder = new MultipartBody.Builder().setType(MultipartBody.FORM);//MultipartBody可以构建与HTML文件上传格式兼容的复杂请求体
    }

    /**
     * @param mode
     */
    @Deprecated
    public void setMode(int mode) {
        this.mode = mode;
    }

    /**
     * 设置字符集
     *
     * @param charset
     */
    public void setCharset(String charset) {
        this.charset = charset;
    }


    /**
     * 请求头部信息
     *
     * @param headers
     */
    @Deprecated
    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    /**
     * 请求体，表单格式
     *
     * @param map
     */
    public void setBoby(HashMap<String, String> map) {
        mode = MODE_FORM;
        if (map != null) {
            for (Map.Entry entry : map.entrySet()) {//追加表单信息
                builder.addFormDataPart(valueOf(entry.getKey()), valueOf(entry.getValue()));
            }
        }

    }

    /**
     * 请求体，json格式
     *
     * @param map
     */
    public void setJosnBoby(HashMap<String, String> map) {
        //TODO：
        mode = MODE_JSON;
        JSONObject json = new JSONObject(map);
        MediaType JSON = MediaType.parse("application/json; charset=" + charset);
        body = RequestBody.create(JSON, String.valueOf(json));
    }

    /**
     * 请求体
     *
     * @param map  请求体
     * @param mode 上传方式 表单： 0   json格式 ：1
     */
    public void setBoby(HashMap<String, String> map, int mode) {
        //TODO：
        this.mode = mode;
        switch (mode) {
            case MODE_FORM:
                setBoby(map);
                break;
            case MODE_JSON:
                setJosnBoby(map);
                break;
        }
    }

    /**
     * 请求体，默认表单格式（推荐）
     *
     * @param map
     * @param headers 头部信息
     */
    public void setBoby(HashMap<String, String> headers, HashMap<String, String> map, int mode) {
        this.headers = headers;
        setBoby(map,mode);

    }


    /**
     * 请求体，表单格式
     *
     * @param map     参数
     * @param fileKey 文件key
     * @param file    文件
     */
    public void setFileBoby(HashMap<String, String> map, String fileKey, File file) {
        this.mode = MODE_FORM;
        setBoby(map);
        builder.addFormDataPart(fileKey, file.getName(), MultipartBody.create(MediaType.parse("*/*"), file)); // 参数分别为， 请求key ，文件名称 ， RequestBody
    }

    /**
     * 请求体，JOSN格式
     * @param map
     * @param fileKey
     * @param file
     */
    public void setFileJsonBoby(HashMap<String, String> map, String fileKey, File file) {
        this.mode = MODE_JSON;
        JSONObject json = new JSONObject(map);
        MediaType JSON = MediaType.parse("application/json; charset=" + charset);
        builder.addFormDataPart(fileKey, file.getName(), MultipartBody.create(MediaType.parse("*/*"), file)); // 参数分别为， 请求key ，文件名称 ， RequestBody
        body = RequestBody.create(JSON, String.valueOf(json));
    }
    /**
     * 请求体，json格式
     *
     * @param map     参数
     * @param fileKey 文件key
     * @param file    文件
     * @param mode    上传方式 表单： 0   json格式 ：1
     */
    public void setFileBoby(HashMap<String, String> map, String fileKey, File file, int mode) {
        this.mode = mode;
        switch (mode) {
            case MODE_FORM:
                setFileBoby(map, fileKey, file);
                break;
            case MODE_JSON:
                setFileJsonBoby(map,fileKey,file);
                break;
        }
    }

    /**
     * 请求体，json格式
     *
     * @param map     参数
     * @param fileKey 文件key
     * @param file    文件
     * @param mode    上传方式 表单： 0   json格式 ：1
     */
    public void setFileBoby(HashMap<String, String> headers, HashMap<String, String> map, String fileKey, File file, int mode) {
        this.headers = headers;
        setFileBoby(map, fileKey, file, mode);
    }



    /**
     * 请求体，json格式
     *
     * @param map     参数
     * @param fileKey 文件key
     * @param files   文件集合
     * @param mode    上传方式 表单： 0   json格式 ：1
     */
    public void setFileListBoby(HashMap<String, String> map, String fileKey, List<File> files, int mode) {
        switch (mode) {
            case MODE_FORM:
                setBoby(map);
                for (File file : files) {
                    builder.addFormDataPart(fileKey, file.getName(), MultipartBody.create(MediaType.parse("*/*"), file)); // 参数分别为， 请求key ，文件名称 ， RequestBody
                }
                break;
            case MODE_JSON:
                JSONObject json = new JSONObject(map);
                MediaType JSON = MediaType.parse("application/json; charset=" + charset);
                for (File file : files) {
                    builder.addFormDataPart(fileKey, file.getName(), MultipartBody.create(MediaType.parse("*/*"), file)); // 参数分别为， 请求key ，文件名称 ， RequestBody
                }
                body = RequestBody.create(JSON, String.valueOf(json));
                break;
        }
    }



    /**
     * 请求的路径
     *
     * @return
     */
    public String getUrl() {
        return url;
    }

    /**
     * 提交服务器的请求头部
     *
     * @return
     */
    public HashMap<String, String> getHeaders() {
        return headers;
    }

    /**
     * 提交服务器的请求体，默认表格格式
     * 上传方式 表单： 0   json格式 ：1
     */
    public RequestBody getBoby() {
        try {
            RequestBody requestBody = null;
            switch (mode) {
                case MODE_FORM:
                    requestBody = builder.build();
                    break;
                case MODE_JSON:
                    requestBody = body;
                    break;
            }
            return requestBody;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
