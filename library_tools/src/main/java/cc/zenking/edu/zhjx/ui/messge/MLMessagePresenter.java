package cc.zenking.edu.zhjx.ui.messge;

import android.content.Context;

import com.google.gson.Gson;
import com.yuanhy.library_tools.http.retrofit.RetrofitUtile;
import com.yuanhy.library_tools.presenter.BasePresenter;
import com.yuanhy.library_tools.rxjava.ObServerBean;
import com.yuanhy.library_tools.util.YCallBack;

import java.util.ArrayList;

import cc.zenking.edu.zhjx.BuildConfig;
import cc.zenking.edu.zhjx.api.AppUrl;
import cc.zenking.edu.zhjx.api.ChildApi;
import cc.zenking.edu.zhjx.db.dbenty.UserEnty;
import cc.zenking.edu.zhjx.enty.childEnty.Child;
import cc.zenking.edu.zhjx.presenter.ChildPresenter;
import cc.zenking.edu.zhjx.utils.ZhjxAppFramentUtil;

public class MLMessagePresenter extends BasePresenter {

    YCallBack yCallBack;
    MLMesageApi messageApi;
    String TAG = "MessageListPresenter";
    Context context;
    UserEnty userEnty;
    Gson gson ;
    ChildPresenter childPresenter;
    String schoolId;
    String stuId;


    public MLMessagePresenter(Context context,YCallBack yCallBack){
        this.context = context;
        this.yCallBack = yCallBack;
        messageApi = RetrofitUtile.getInstanceSSL(AppUrl.BASE_URL).createSSL(MLMesageApi.class);
        userEnty = ZhjxAppFramentUtil.getUserInfo();
        gson = new Gson();
        initPresenter();
    }


    public void requestMessageInformationList(){


        messageApi.requestMessageList("1.0","",userEnty.userid,stuId,BuildConfig.VERSION_NAME,schoolId)
                .compose(io_uiMain()).subscribe(new ObServerBean<MLParsingModel>() {
            @Override
            public void onSuccees(MLParsingModel mlParsingModel) {

              yCallBack.requestSuccessful(mlParsingModel.getNotifys());
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
             yCallBack.requestFail(null);
            }
        });
    }

//    获取孩子列表

    public void initPresenter() {

        childPresenter = new ChildPresenter(context, new YCallBack() {
            @Override
            public void requestSuccessful(Object o) {
                ArrayList<Child> childArrayList = (ArrayList<Child>) o;
                Child model = null;
                if(childArrayList.size() > 0){
                    model = childArrayList.get(0);
                    schoolId = model.schoolId;
                    stuId = model.studentId;
                }
                requestMessageInformationList();
            }

            @Override
            public void onError(Object o) {

            }
        });

        getAppFunction();
    }

    private void getAppFunction() {

        childPresenter.getBindStudentList();
    }
}
