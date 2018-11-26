package com.library.utils.httpservice;

import android.app.Dialog;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

/**
 * 当一次向服务器发起多次请求时，在第一个请求时弹出对话框，最后一个请求关闭对话框
 * @author redkid
 *
 */
public class DialogControl {

    /**总数目*/
    private int                      totleNum;
    /**当前第X个请求*/
    private int                      curReqNum;
    /**已经完成的数目*/
    private int                      doneNum;
    /** 提示信息框，用户提交时弹出，服务器响应后消失 */
    private Dialog dialog;
    /** 任务列表*/
    private List<AsyncTask<?, ?, ?>> tasks = new ArrayList<AsyncTask<?, ?, ?>>();

    /**
     * 构造函数
     * 
     * @param totleNum {@link DialogControl#totleNum}
     */
    public DialogControl(int totleNum, Dialog dialog) {
        this.totleNum = totleNum;
        this.doneNum = 0;
        this.curReqNum = 1;
        this.dialog = dialog;
    }

    /**
     * 弹出对话框
     * 
     */
    public void show(final AsyncTask<?, ?, ?> task,String tible) {
        tasks.add(task);
        if (curReqNum == 1 && null != dialog) {
            dialog.setTitle(tible);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(true);
            dialog.show();
        }
    }

    /**
     * 请求全部完成，关闭对话框
     */
    public synchronized void done() {
        doneNum = doneNum + 1;
        if (totleNum == doneNum && null != dialog) {
            dialog.cancel();
        }
    }

    /**
     * Getter method for property <tt>totleNum</tt>.
     * 
     * @return property value of totleNum
     */
    public int getTotleNum() {
        return totleNum;
    }

    /**
     * Setter method for property <tt>totleNum</tt>.
     * 
     * @param totleNum value to be assigned to property totleNum
     */
    public void setTotleNum(int totleNum) {
        this.totleNum = totleNum;
    }

    /**
     * Getter method for property <tt>curReqNum</tt>.
     * 
     * @return property value of curReqNum
     */
    public int getCurReqNum() {
        return curReqNum;
    }

    /**
     * Setter method for property <tt>curReqNum</tt>.
     * 
     * @param curReqNum value to be assigned to property curReqNum
     */
    public void setCurReqNum(int curReqNum) {
        this.curReqNum = curReqNum;
    }

    /**
     * Getter method for property <tt>doneNum</tt>.
     * 
     * @return property value of doneNum
     */
    public int getDoneNum() {
        return doneNum;
    }

    /**
     * Setter method for property <tt>doneNum</tt>.
     * 
     * @param doneNum value to be assigned to property doneNum
     */
    public void setDoneNum(int doneNum) {
        this.doneNum = doneNum;
    }

}
