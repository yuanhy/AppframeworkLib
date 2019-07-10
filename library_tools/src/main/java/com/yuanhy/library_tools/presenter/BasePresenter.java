package com.yuanhy.library_tools.presenter;



import com.yuanhy.library_tools.rxjava.RxJavaUtil;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<T> extends RxJavaUtil {
    public WeakReference<T> weakReference;
   public void onBindView(T view){
       weakReference = new WeakReference<T>(view);
   }
    public void onDestroy(){
       if (weakReference!=null){
           weakReference.clear();
       }
    }
}
