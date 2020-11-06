package com.yuanhy.library_tools.websockt;

import android.content.Context;
import android.util.Log;

import com.yuanhy.library_tools.app.AppFramentUtil;
import com.yuanhy.library_tools.http.okhttp3.AndroidOkHttp3;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Map;

import retrofit2.http.Url;

public class WebSocketClientUtile extends WebSocketClient {

    WebSocktStatus webSocktStatus;
    public void setWebSocktStatus(WebSocktStatus webSocktStatus) {
        this.webSocktStatus = webSocktStatus;
    }


    public WebSocketClientUtile(URI serverUri) {
        super(serverUri);
//        super(serverUri, new Draft_6455());Draft_6455()代表使用的协议版本，这里可以不写或者写成这样即可
    }

    public WebSocketClientUtile(URI serverUri, Draft protocolDraft) {
        super(serverUri, protocolDraft);
    }

    public WebSocketClientUtile(URI serverUri, Map<String, String> httpHeaders) {
        super(serverUri, httpHeaders);
    }

    public WebSocketClientUtile(URI serverUri, Draft protocolDraft, Map<String, String> httpHeaders) {
        super(serverUri, protocolDraft, httpHeaders);
    }

    public WebSocketClientUtile(URI serverUri, Draft protocolDraft, Map<String, String> httpHeaders, int connectTimeout) {
        super(serverUri, protocolDraft, httpHeaders, connectTimeout);
    }




    @Override
    public void onOpen(ServerHandshake handshakedata) {//方法在websocket连接开启时调用
        AppFramentUtil.logCatUtil.v("WebSocketClientUtile", "onOpen()");
        if (webSocktStatus!=null)
            webSocktStatus.open();
    }

    @Override
    public void onMessage(String message) {//方法在接收到消息时调用
        AppFramentUtil.logCatUtil.v("WebSocketClientUtile", "onMessage():"+message);
        if (webSocktStatus!=null)
            webSocktStatus.onMessage(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {//方法在连接断开时调用
        AppFramentUtil.logCatUtil.v("WebSocketClientUtile", "onClose()");
    }

    @Override
    public void onError(Exception ex) {//方法在连接出错时调用
        AppFramentUtil.logCatUtil.v("WebSocketClientUtile", "onError()");
         ex.printStackTrace();
        if (webSocktStatus!=null)
            webSocktStatus.reconnectBlocking();
    }

}
