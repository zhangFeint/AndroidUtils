package com.library.utils.httpservice;

import android.os.AsyncTask;


/**
 * 向服务器提交数据
 * <li>把UI展示作为一个线程，请求数据做为一个线程，2个线程互不影响。
 * <li>UI线程不需要再开子线程等复杂操作，在{@link NetWorkInterface}的result回调里直接拿数据即可。
 */
public class UploadDataAsyncTask extends AsyncTask<byte[], Integer, String> {
    private static final String TAG = UploadDataAsyncTask.class.getSimpleName();
    private NetWorkInterface netWork;//数据的提交接口
    private DialogControl control;

    private int overtime;//超时时间


    int requetWay = 1;


    /**
     * 向服务器提交多次数据
     *
     * @param netWork
     * @param control
     * @param overtime
     */
    public UploadDataAsyncTask(NetWorkInterface netWork, DialogControl control, int overtime, int requetWay) {
        if (!netWork.validate()) { // 如果校验没有通过，不继续执行
            return;
        }
        this.netWork = netWork;
        this.overtime = overtime;//超时时间
        this.control = control;
        this.requetWay = requetWay;
        if (null != control) {
            this.control.show(this);// 加载中文字
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
        String result = HttpRequestUtils.getInstance().getRequestRresults(data.getUrl(), data.getHeaders(), data.getBoby(), requetWay);
        if (null != control) {//捕获异常时关闭dialog
            control.done();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) { //执行回调接口方法
        netWork.result(result);
        super.onPostExecute(result);
    }


    /**
     * 数据的提交接口
     *
     * @author redkid
     */
    public interface NetWorkInterface {

        /**
         * 校验UI数据
         *
         * @return true往下进行，false停止 （注：返回false时自行处理提示信息）
         */
        boolean validate();

        /**
         * 需要提交服务器的数据
         */
        SubmitData getSubmitData();

        /**
         * 处理服务器返回数据
         *
         * @param result 返回数据结果
         */
        void result(final String result);

    }
}
