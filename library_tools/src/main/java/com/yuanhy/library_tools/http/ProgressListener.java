package com.yuanhy.library_tools.http;

/**
 * Created by yuanhy on 2018/1/22.
 */

public abstract class ProgressListener {
    /**
     *
     * @param fileSizeBytes 总大小
     * @param remainingBytes 已经写入的文件大小
     * @param done 是否下载结束
     */
    public    void onProgress(long fileSizeBytes, long remainingBytes, boolean done){}

    /**
     * 多线程下载器   应用层不需要实现
     * 返回类型都是指的当前线程 是否下载结束
     * @param fileSizeBytes 总大小
     * @param remainingBytes 已经写入的文件大小
     * @param datakey  线程标识
     * @param thredId 线程ID
     * @param done 是否下载结束
     */
    public    void onProgress(long fileSizeBytes, long remainingBytes,String datakey ,String thredId ,boolean done){}
    public   void onOk(Object o){};
    public   void onError(Object o){};
    /**
     * 进度
     *
     * @param o 进度0.00 - 0.50  - 1.00
     * @param o2    文件总大小 单位字节
     */
    public abstract void onProgress(Object o, Object o2);
    public  void onFileSize(Long fileSize){}
}
