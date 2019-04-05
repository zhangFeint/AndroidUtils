package com.library.utils.httpservice;

import android.os.AsyncTask;


/**
 * 向服务器提交数据
 * <li>把UI展示作为一个线程，请求数据做为一个线程，2个线程互不影响。
 * <li>UI线程不需要再开子线程等复杂操作，在{@link OnNetWorkInterface}的result回调里直接拿数据即可。
 */
public class UploadDataAsyncTask extends AsyncTask<byte[], Integer, String> {
    private static final String TAG = UploadDataAsyncTask.class.getSimpleName();
    private OnNetWorkInterface netWork;//数据的提交接口
    private OnLoadListener listener;
    //错误返回 500 找不到网络路径
    public static String error = "{\"code\":500,\"error\":\"服务器内部错误\"}";
    private int overtime;//超时时间
    int requetWay = 1;


    /**
     * 向服务器提交多次数据
     *
     * @param netWork
     * @param listener
     * @param overtime
     */
    public UploadDataAsyncTask(OnNetWorkInterface netWork, OnLoadListener listener, int overtime, int requetWay) {
        if (!netWork.validate()) { // 如果校验没有通过，不继续执行
            return;
        }
        this.netWork = netWork;
        this.overtime = overtime;//超时时间
        this.listener = listener;
        this.requetWay = requetWay;
        if (null != listener) {
            listener.onShow();
        }
    }


    /**
     * 加上触发前的判断
     */
    public void execute() {
        if (null != netWork && null != netWork.getSubmitData()) {
            this.execute("".getBytes());
        }
    }

    /**
     * @param params
     * @return
     */
    @Override
    protected String doInBackground(byte[]... params) {
        SubmitData data = netWork.getSubmitData();//获取提交数据信息
        HttpRequestUtils.getInstance().setOvertime(overtime);
        String result = null;
        try {
            result = HttpRequestUtils.getInstance().getRequestRresults(data.getUrl(), data.getHeaders(), data.getBoby(), requetWay);
            if (null != listener) {//捕获异常时关闭dialog
                listener.onConceal();
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            if (null != listener) {//捕获异常时关闭dialog
                listener.onConceal();
            }
            return error;
        }


    }

    @Override
    protected void onPostExecute(String result) { //执行回调接口方法
        netWork.result(result);
        super.onPostExecute(result);
    }

}
