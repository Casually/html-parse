package cc.casually.htmlParse.util;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;

/**
 * 连接属性设置
 */
public class ClientConfiguration {

    // 连接超时设置
    private int connectionTimeoutMillis;
    private int socketTimeoutMillis;
    private Proxy proxy;

    public ClientConfiguration() {
        this.connectionTimeoutMillis = 0;
        this.socketTimeoutMillis = 0;
        this.proxy = Proxy.NO_PROXY;
    }

    public ClientConfiguration(int connectionTimeoutMillis, int socketTimeoutMillis, Proxy proxy) {
        this.connectionTimeoutMillis = connectionTimeoutMillis;
        this.socketTimeoutMillis = socketTimeoutMillis;
        this.proxy = proxy;
    }

    /**
     * 获取超时时间
     * @return
     */
    public int getConnectionTimeoutMillis() {
        return connectionTimeoutMillis;
    }

    /**
     * 设置超时时间
     * @param connectionTimeoutMillis
     */
    public void setConnectionTimeoutMillis(int connectionTimeoutMillis) {
        this.connectionTimeoutMillis = connectionTimeoutMillis;
    }

    /**
     * 获取协议超时时间
     * @return
     */
    public int getSocketTimeoutMillis() {
        return socketTimeoutMillis;
    }

    /**
     * 设置协议超时时间
     * @param socketTimeoutMillis
     */
    public void setSocketTimeoutMillis(int socketTimeoutMillis) {
        this.socketTimeoutMillis = socketTimeoutMillis;
    }

    public Proxy getProxy() {
        return proxy;
    }

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    public void setProxy(String host, int port, Proxy.Type type) {
        SocketAddress addr = new InetSocketAddress(host, port);
        this.proxy = new Proxy(type, addr);
    }
}
