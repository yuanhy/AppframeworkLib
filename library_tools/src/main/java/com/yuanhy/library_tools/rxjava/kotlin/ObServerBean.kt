package com.yuanhy.library_tools.rxjava.kotlin


import android.accounts.NetworkErrorException
import com.yuanhy.library_tools.app.AppFramentUtil
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

/**
 * yuanhy
 */
open abstract class ObServerBean<T> : Observer<T> {
    var  TAG:String="ObServerBean"
   var disposable: Disposable?=null ;

   override fun onSubscribe(d:Disposable ) {
        disposable = d;
    }

    override fun onNext(t :T ) {
        onSuccees(t);
    }

    override fun onError(e:Throwable  ) {
        try {
            e.printStackTrace();
            AppFramentUtil.logCatUtil.e(TAG, e.getStackTrace().toString());
            if (e is TimeoutException
                    || e is TimeoutException
                    || e is NetworkErrorException
                    || e is UnknownHostException) {
                onFailure(e, true);  //网络错误
            } else {
                onFailure(e, false);
            }
        } catch (e1:Exception ) {
            e1.printStackTrace();
        }
    }

    override fun onComplete() {

    }

    public abstract fun onSuccees(t:T );

    /**
     * 返回失败,外部类要继承实现的
     *
     * @param e
     * @param isNetWorkError 是否是网络错误
     * @throws Exception
     */
    protected abstract fun onFailure(e:Throwable  ,   isNetWorkError:Boolean) : Exception;

    /**
     * 请求结束  如果外部类要继承实现,就加上修饰符  abstract
     */
    public  fun onRequestEnd() {
        //取消订阅
        if (disposable != null && !disposable!!.isDisposed()) {
            disposable!!.dispose();
        }
    }
}
