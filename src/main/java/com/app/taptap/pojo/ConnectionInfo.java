package com.app.taptap.pojo;

public class ConnectionInfo {
    private String websocket;
    private String[] origins;

    public String getWebsocket() {
        return websocket;
    }

    public void setWebsocket(String websocket) {
        this.websocket = websocket;
    }

    public String[] getOrigins() {
        return origins;
    }

    public void setOrigins(String[] origins) {
        this.origins = origins;
    }
}
