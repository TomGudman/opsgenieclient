package com.ifountain.opsgenie.client.marid.http;

import org.littleshoot.proxy.DefaultHttpProxyServer;
import org.littleshoot.proxy.ProxyAuthorizationHandler;

/**
 * @author Sezgin Kucukkaraaslan
 * @version 7/30/12 10:05 AM
 */
public class HttpProxy {
    private DefaultHttpProxyServer proxyServer;
    private int port;
    private String username;
    private String password;

    public HttpProxy(int port, String username, String password) {
        this.port = port;
        this.username = username;
        this.password = password;
        proxyServer = new DefaultHttpProxyServer(port);
        if (this.username != null && this.password != null) {
            proxyServer.addProxyAuthenticationHandler(new ProxyAuthorizationHandler() {
                @Override
                public boolean authenticate(String userName, String password) {
                    return HttpProxy.this.username.equals(userName) && HttpProxy.this.password.equals(password);
                }
            });
        }
    }

    public HttpProxy(int port) {
        this(port, null, null);
    }

    public void start() {
        proxyServer.start();
    }

    public void stop() {
        proxyServer.stop();
    }

}