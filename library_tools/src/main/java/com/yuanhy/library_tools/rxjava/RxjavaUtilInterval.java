package com.yuanhy.library_tools.rxjava;

import com.yuanhy.library_tools.app.AppFramentUtil;
import com.yuanhy.library_tools.util.YCallBack;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * 定时器
 */
public class RxjavaUtilInterval {
    private String tag = "RxjavaUtil";
    //	private Disposable disposableInterval;
    YCallBack yCallBack;
    ObServerBean obServerBean;

    public RxjavaUtilInterval(YCallBack yCallBack) {
        this.yCallBack = yCallBack;
//	obServerBean = new ObServerBean<Long>() {
//		@Override
//		public void onSuccees(Long aLong) {
//			AppFramentUtil.logCatUtil.i(tag,
//					"interval：" + aLong);
//			intervalWork();
//		}
//
//		@Override
//		protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//
//		}
//
////		@Override
////		public void onSubscribe(Disposable d) {
////		s
////		}
//	};
    }

    /**
     * 多长时间一次
     *
     * @param period 秒单位
     */
    public void interval(long period) {
        cancelDisposable();
        obServerBean = new ObServerBean<Long>() {
            @Override
            public void onSuccees(Long aLong) {
                AppFramentUtil.logCatUtil.i(tag,
                        "interval：" + aLong);
                intervalWork();
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

            }

//		@Override
//		public void onSubscribe(Disposable d) {
//		s
//		}
        };
        Observable.interval(period, TimeUnit.SECONDS).compose(RxJavaUtil.io_uiMain()).subscribe(obServerBean);
    }

    /**
     * 取消轮序接口
     */
    public void cancelDisposable() {
//		//取消订阅
//		if (disposableInterval != null && !disposableInterval.isDisposed()) {
//			disposableInterval.dispose();
//		}
        if (obServerBean != null)
            obServerBean.onRequestEnd();
    }

    /**
     * 被轮巡的接口
     */
    private void intervalWork() {
        yCallBack.requestSuccessful(null);
    }

    ;
}
