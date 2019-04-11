package com.library.utils.utils;

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
            if (value.isEmpty()) {
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
}
