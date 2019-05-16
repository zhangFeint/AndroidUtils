package com.library.utils.utils;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ParametersUtils {
    private static ParametersUtils parametersUtils;

    public static ParametersUtils getInstance() {
        if (parametersUtils == null)
            parametersUtils = new ParametersUtils();
        return parametersUtils;
    }


    /**
     * get  添加选填参数
     * @param url
     * @param map
     * @return
     */
    public String getChoosable(String url, Map<String, String> map) {
        if (map.size() == 0) {
            return url;
        }
        for (String key : map.keySet()) {
            String value = map.get(key);
            if (StringUtil.getInstance().isEmpty(value)) {
                url = url + "";
            } else {
                if (!url.contains("?")) {
                    url = url + "?" + key + "=" + value;
                } else {
                    url = url + "&" + key + "=" + value;
                }
            }
        }
        return url;
    }
    /**
     * get  添加选填参数
     * @param url
     * @param map
     * @return
     */
    public String getChoosableJoint(String url, Map<String, String> map) {
        if (map.size() == 0) {
            return url;
        }
        for (String key : map.keySet()) {
            String value = map.get(key);
            if(value ==null){
                value = "";
            }
                if (!url.contains("?")) {
                    url = url + "?" + key + "=" + value;
                } else {
                    url = url + "&" + key + "=" + value;
                }
        }
        return url;
    }

    /**
     * 添加body选填参数
     *  ParametersUtils.getInstance().setParametersMap(map,"11","11");
     * @param map
     * @param key
     * @param value
     */
    public void setParametersMap(Map map,String key,String value){
        if(StringUtil.getInstance().isEmpty(value)){
           return;
        }else {
            map.put(key,value);
        }
    }
    public void setParametersMapJoint(Map map,String key,String value){
        if(StringUtil.getInstance().isEmpty(value)){
            map.put(key,"");
        }else {
            map.put(key,value);
        }
    }



    /**
     * 解析出url参数中的键值对
     * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     * @param URL  url地址
     * @return  url请求参数部分
     * @author lzf
     */
    public  Map<String, String> urlSplit(String URL){
        Map<String, String> mapRequest = new HashMap<String, String>();
        String[] arrSplit=null;
        String strUrlParam=TruncateUrlPage(URL);
        if(strUrlParam==null){
            return mapRequest;
        }
        arrSplit=strUrlParam.split("[&]");
        for(String strSplit:arrSplit){
            String[] arrSplitEqual=null;
            arrSplitEqual= strSplit.split("[=]");
            //解析出键值
            if(arrSplitEqual.length>1){
                //正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
            }else{
                if(arrSplitEqual[0]!=""){
                    //只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }
    /**
     * 去掉url中的路径，留下请求参数部分
     * @param strURL url地址
     * @return url请求参数部分
     * @author lzf
     */
    private  String TruncateUrlPage(String strURL){
        String strAllParam=null;
        String[] arrSplit=null;
        strURL=strURL.trim().toLowerCase();
        arrSplit=strURL.split("[?]");
        if(strURL.length()>1){
            if(arrSplit.length>1){
                for (int i=1;i<arrSplit.length;i++){
                    strAllParam = arrSplit[i];
                }
            }
        }
        return strAllParam;
    }
}
