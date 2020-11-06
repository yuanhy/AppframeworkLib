package com.yuanhy.library_tools.websockt;

public interface WebSocktStatus {
    void open();//连接成功
    void reconnectBlocking();//需要重连
    void onMessage(String message);
    void onEorro(String eorro);
}
