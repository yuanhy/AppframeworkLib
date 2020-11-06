package com.yuanhy.library_tools.websockt;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yuanhy.library_tools.activity.WebViewUtilActivity;
import com.yuanhy.library_tools.app.AppFramentUtil;
import com.yuanhy.library_tools.rxjava.RxjavaUtilInterval;
import com.yuanhy.library_tools.util.YCallBack;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.enums.ReadyState;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.java_websocket.enums.ReadyState.NOT_YET_CONNECTED;

public   class WebSocketUtile {
    String TAG=getClass().getName();
    WebSocketClientUtile webSocketClientUtileData;
    YCallBack yCallBack;
    private static WebSocketUtile webSocketClientUtile;
    private static String mBaseUri;

    public static WebSocketUtile getInstance(String serverUri) {

        if (webSocketClientUtile == null) {
            synchronized (WebSocketUtile.class) {
                mBaseUri = serverUri;
                webSocketClientUtile = new WebSocketUtile();
            }
        }
        return webSocketClientUtile;
    }

    public void setyCallBack(String className,YCallBack yCallBack) {
        this.yCallBack = yCallBack;
        if (yCallBack==null){
            yCallBackHashMap.remove(className);
        }
        yCallBackHashMap.put(className,yCallBack);
    }

    WebSocktStatus webSocktStatus = new WebSocktStatus() {
        @Override
        public void open() {
            if (yCallBack != null) {
                yCallBack.sendMessge("");
            }
        }

        @Override
        public void reconnectBlocking() {

        }

        @Override
        public void onMessage(String message) {

        }

        @Override
        public void onEorro(String eorro) {
            if (yCallBack != null) {
                yCallBack.onError(eorro);
            }
        }
    };

    private WebSocketUtile() {
        initWebSocketClientUtileData();
    }

    private void initWebSocketClientUtileData() {
        if (webSocketClientUtileData == null) {
            URI uri = URI.create(mBaseUri);
            webSocketClientUtileData = new WebSocketClientUtile(uri) {

                @Override
                public void onMessage(final String message) {
                    super.onMessage(message);
                    if (TextUtils.isEmpty(message)) {
                    } else {
//                        if (message.equals(messageThem)) {//
//                            return;
//                        }
                        AppFramentUtil.logCatUtil.i(TAG,"onMessage():" + message);
                        for(Map.Entry<String, YCallBack> entry : yCallBackHashMap.entrySet()){
                            if ( entry.getValue() !=null){
                                entry.getValue() .requestSuccessful(message);
                            }
                        }

//                        if (yCallBack != null)
//                            yCallBack.requestSuccessful(message);
                    }
                }
            };

        }
        webSocketClientUtileData.setWebSocktStatus(webSocktStatus);
        AppFramentUtil.logCatUtil.i(TAG,"ReadyState:" + webSocketClientUtileData.getReadyState());
        if (!webSocketClientUtileData.isOpen()) {//&& webSocketClientUtileGuaDan.getReadyState()==NOT_YET_CONNECTED

            if (webSocketClientUtileData.getReadyState() == NOT_YET_CONNECTED) {
                try {
                    webSocketClientUtileData.connect();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }catch (IllegalThreadStateException e){
                    e.printStackTrace();
                }catch (NullPointerException n){
                    n.getStackTrace();
                }
            } else if (webSocketClientUtileData.getReadyState().equals(ReadyState.CLOSING)
                    || webSocketClientUtileData.getReadyState().equals(ReadyState.CLOSED)) {
                webSocketClientUtileData.reconnect();
            }
        }
        protectInterval.interval(10);
    }

    HashMap<String, String> sendMap = new HashMap<>();
//    ArrayList<YCallBack> yCallBackArrayList = new ArrayList<>();
    HashMap<String, YCallBack> yCallBackHashMap = new HashMap<String, YCallBack>();
    public void senKey(final String sendKey) {
//        yCallBackHashMap.put(sendKey,yCallBack);
        AppFramentUtil.logCatUtil.i(TAG,webSocketClientUtileData.getReadyState() +"sendkey:" + sendKey);
        sendMap.put(sendKey, sendKey);
        if (webSocketClientUtileData != null && webSocketClientUtileData.isOpen()) {
            webSocketClientUtileData.send(sendKey);
            RxjavaUtilInterval rxjavaUtilInterval = errorHashMap.get(sendKey);
            if (rxjavaUtilInterval != null) {
                rxjavaUtilInterval.cancelDisposable();
                errorHashMap.remove(sendKey);
            }
        } else {
            if (webSocketClientUtileData == null){
                AppFramentUtil.logCatUtil.i(TAG, "webSocketClientUtileData==null:" + sendKey);
                initWebSocketClientUtileData();
                senKey(sendKey);
                return;
            }
            AppFramentUtil.logCatUtil.i(TAG,webSocketClientUtileData.getReadyState() +"链接异常 sendkey:" + sendKey);
            if (webSocketClientUtileData.getReadyState() == NOT_YET_CONNECTED) {
                try {
                    webSocketClientUtileData.connect();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }catch (NullPointerException n){
                    n.getStackTrace();
                }catch (IllegalThreadStateException e){
                    e.printStackTrace();
                }
            } else if (webSocketClientUtileData.getReadyState().equals(ReadyState.CLOSING)
                    || webSocketClientUtileData.getReadyState().equals(ReadyState.CLOSED)) {
                try {
                    webSocketClientUtileData.reconnect();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                    try {
                        webSocketClientUtileData.reconnectBlocking();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }catch (NullPointerException n){
                    n.getStackTrace();
                }catch (IllegalThreadStateException e){
                    e.printStackTrace();
                }

            }
            RxjavaUtilInterval rxjavaUtilInterval = new RxjavaUtilInterval(new YCallBack() {
                @Override
                public void requestSuccessful(Object o) {
                    RxjavaUtilInterval rxjavaUtilInterval = errorHashMap.get(sendKey);
                    if (rxjavaUtilInterval != null) {
                        rxjavaUtilInterval.cancelDisposable();
                        errorHashMap.remove(sendKey);
                        senKey(sendKey);
                    }
                }
            });
            rxjavaUtilInterval.interval(2);
            errorHashMap.put(sendKey, rxjavaUtilInterval);
        }
    }



    public void senKeyRemove(final String sendKey) {
        AppFramentUtil.logCatUtil.i(TAG,webSocketClientUtileData.getReadyState() +" senKeyRemove:" + sendKey);
        sendMap.put(sendKey, sendKey);
        if (webSocketClientUtileData != null && webSocketClientUtileData.isOpen()) {
            webSocketClientUtileData.send(sendKey);
            RxjavaUtilInterval rxjavaUtilInterval = errorHashMap.get(sendKey);
            if (rxjavaUtilInterval != null) {
                rxjavaUtilInterval.cancelDisposable();
                errorHashMap.remove(sendKey);
            }
        } else {
            if (webSocketClientUtileData.getReadyState() == NOT_YET_CONNECTED) {
                try {
                    webSocketClientUtileData.connect();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }catch (IllegalThreadStateException e){
                    e.printStackTrace();
                }
            } else if (webSocketClientUtileData.getReadyState().equals(ReadyState.CLOSING)
                    || webSocketClientUtileData.getReadyState().equals(ReadyState.CLOSED)) {
                try {
                    webSocketClientUtileData.reconnect();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                    try {
                        webSocketClientUtileData.reconnectBlocking();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }catch (IllegalThreadStateException e){
                    e.printStackTrace();
                }
            }
            RxjavaUtilInterval rxjavaUtilInterval = new RxjavaUtilInterval(new YCallBack() {
                @Override
                public void requestSuccessful(Object o) {
                    RxjavaUtilInterval rxjavaUtilInterval = errorHashMap.get(sendKey);
                    if (rxjavaUtilInterval != null) {
                        rxjavaUtilInterval.cancelDisposable();
                    }
                    errorHashMap.remove(sendKey);
//                    senKey(sendKey); 只重发一次。失败了不再重发
                }
            });
            rxjavaUtilInterval.interval(1);
            errorHashMap.put(sendKey, rxjavaUtilInterval);
        }
    }

    HashMap<String, RxjavaUtilInterval> errorHashMap = new HashMap<>();

    /**
     * 中断 重连后使用
     */
    public void sendAllKey() {
        for (String value : sendMap.values()) {
            System.out.println("Value = " + value);
            if (value.contains("addChannel")) {
                senKey(String.valueOf(sendMap.keySet()));
            }
        }
    }

    RxjavaUtilInterval protectInterval = new RxjavaUtilInterval(new YCallBack() {
        @Override
        public void requestSuccessful(Object o) {
            AppFramentUtil.logCatUtil.i(TAG,"protectInterval: 守护web连接" + webSocketClientUtileData.getReadyState());
            if (webSocketClientUtileData != null && webSocketClientUtileData.isOpen()) {
                AppFramentUtil.logCatUtil.i(TAG,"protectInterval: 守护web正常");
            } else {
                if (webSocketClientUtileData.getReadyState() == NOT_YET_CONNECTED) {
                    try {
                        AppFramentUtil.logCatUtil.i("protectInterval: connect");
                        webSocketClientUtileData.connect();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                } else if (webSocketClientUtileData.getReadyState().equals(ReadyState.CLOSING)
                        || webSocketClientUtileData.getReadyState().equals(ReadyState.CLOSED)) {
                    AppFramentUtil.logCatUtil.i("protectInterval: reconnect");
                    webSocketClientUtileData.reconnect();
                }
            }
        }
    });
}
