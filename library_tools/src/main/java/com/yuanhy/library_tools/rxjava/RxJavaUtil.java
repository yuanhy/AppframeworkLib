package com.yuanhy.library_tools.rxjava;

import com.yuanhy.library_tools.app.AppFramentUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.operators.observable.ObservableFromFuture;
import io.reactivex.internal.operators.observable.ObservableTakeLastTimed;
import io.reactivex.schedulers.Schedulers;

public abstract class RxJavaUtil {
    private String tag = "RxjavaUtil";
    private Disposable disposable;

    public static  <T>ObservableTransformer<T,T> io_uiMain(){
        return  new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
    public static  <T>ObservableTransformer<T,T> io_io(){
        return  new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(Schedulers.io());
            }
        };
    }

    /**
     * 多长时间一次
     *
     * @param period 秒单位
     */
    public void interval(long period) {
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
    public void cancelDisposable() {
        //取消订阅
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    /**
     * 被轮巡的接口
     */
    public void intervalWork() {

    }
}
