package com.library.utils.httpservice;


import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
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
    private Map<String, String> headers;

    private String charset = "utf-8";
    private RequestBody body;
    private FormBody.Builder formBody;
    private MultipartBody.Builder builder;

    /**
     * @param url
     */
    public SubmitData(String url) {
        this.url = url;
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
    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
    /**
     * map<String,String> identityHashMap = new IdentityHashMap<>()</>
     * identityHashMap.put(new String("key"),"value1")
     * identityHashMap.put(new String("key"),"value2")
     * 这里一定是 new String("")进行存key的值。IdentityHashMap 存住的是可重读key 但是 内部对比的是 key的地址，而不是hash值
     * 也就是说内部使用的是 ==做的比对 大家有兴趣可以自己点进去看下 put的方法
     *     </>
     *
     * @param
     * @return


    /**
     * 请求体，表单格式
     *
     * @param map
     */
    public void setBoby(Map<String, String> map) {
        formBody = new FormBody.Builder();
        if (map != null) {
            for (Map.Entry entry : map.entrySet()) {//追加表单信息
                formBody.add(valueOf(entry.getKey()), valueOf(entry.getValue()));
            }
        }
        body = formBody.build();
    }

    /**
     * 请求体，json格式
     *
     * @param map
     */
    public void setJosnBoby(Map<String, String> map) {
        JSONObject json = new JSONObject(map);
        MediaType JSON = MediaType.parse("application/json; charset=" + charset);
        body = RequestBody.create(JSON, String.valueOf(json));
    }

    /**
     * 单文件上传
     *
     * @param map
     * @param fileKey
     * @param file
     */
    public void setFileBoby(Map<String, String> map, String fileKey, File file) {
        builder = new MultipartBody.Builder().setType(MultipartBody.FORM);//MultipartBody可以构建与HTML文件上传格式兼容的复杂请求体
        if (map != null) {
            for (Map.Entry entry : map.entrySet()) {//追加表单信息
                builder.addFormDataPart(valueOf(entry.getKey()), valueOf(entry.getValue()));
            }
        }
        builder.addFormDataPart(fileKey, file.getName(), MultipartBody.create(MediaType.parse("*/*"), file)); // 参数分别为， 请求key ，文件名称 ， RequestBody
        body = builder.build();
    }


    /**
     * 多文件上传
     *
     * @param map     参数
     * @param fileKey 文件key
     * @param files   文件集合
     */
    public void setFileListBoby(Map<String, String> map, String fileKey, List<File> files) {
        builder = new MultipartBody.Builder().setType(MultipartBody.FORM);//MultipartBody可以构建与HTML文件上传格式兼容的复杂请求体
        if (map != null) {
            for (Map.Entry entry : map.entrySet()) {//追加表单信息
                builder.addFormDataPart(valueOf(entry.getKey()), valueOf(entry.getValue()));
            }
        }
        for (File file : files) {
            builder.addFormDataPart(fileKey, file.getName(), MultipartBody.create(MediaType.parse("*/*"), file)); // 参数分别为， 请求key ，文件名称 ， RequestBody
        }
        body = builder.build();
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
    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * 提交服务器的请求体，默认表格格式
     * 上传方式 表单： 0   json格式 ：1
     */
    public RequestBody getBoby() {
        return body;
    }


}
