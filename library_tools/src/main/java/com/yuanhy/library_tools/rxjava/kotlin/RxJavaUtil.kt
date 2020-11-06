package com.yuanhy.library_tools.rxjava.kotlin

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

abstract class RxJavaUtil {
    private val tag = "RxjavaUtil"
    private val disposable: Disposable? = null

    /**
     * 多长时间一次
     *
     * @param period 秒单位
     */
    fun interval(period: Long) {
        //        Observable.interval(period, TimeUnit.SECONDS).compose(RxJavaUtil.io_uiMain()).subscribe(new ObServerBean<Long>() {
        //            @Override
        //            public void onSuccees(Long aLong) {
        //                AppFramentUtil.logCatUtil.i(tag,
        //                        "interval：" + aLong);
        //                intervalWork();
        //            }
        //
        //            @Override
        //            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
        //
        //            }
        //
        //            @Override
        //            public void onSubscribe(Disposable d) {
        //                disposable = d;
        //            }
        //        });
    }

    /**
     * 取消轮序接口
     */
    fun cancelDisposable() {
        //取消订阅
        if (disposable != null && !disposable.isDisposed) {
            disposable.dispose()
        }
    }

    /**
     * 被轮巡的接口
     */
    fun intervalWork() {

    }

    companion object {

        fun <T> io_uiMain(): ObservableTransformer<T, T> {
            return ObservableTransformer { upstream -> upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) }
        }

        fun <T> io_io(): ObservableTransformer<T, T> {
            return ObservableTransformer { upstream -> upstream.subscribeOn(Schedulers.io()).observeOn(Schedulers.io()) }
        }
    }
}
