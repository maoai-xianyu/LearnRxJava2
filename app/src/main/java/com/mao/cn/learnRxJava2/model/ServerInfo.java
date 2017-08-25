// +----------------------------------------------------------------------
// | FileName:   ${file_name}  
// +----------------------------------------------------------------------
// | CreateTime: 15/9/7  @下午3:45
// +----------------------------------------------------------------------
// | Author:     xab(admin@xueyong.net.cn)
// +----------------------------------------------------------------------
// | Description:
// +----------------------------------------------------------------------
package com.mao.cn.learnRxJava2.model;

import java.io.Serializable;

public class ServerInfo implements Serializable {
    private String serverName;
    private String serverHost;
    private String serverRecommendUrl;

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public String getServerRecommendUrl() {
        return serverRecommendUrl;
    }

    public void setServerRecommendUrl(String serverRecommendUrl) {
        this.serverRecommendUrl = serverRecommendUrl;
    }

    @Override
    public String toString() {
        return "ServerInfo{" +
                "serverName='" + serverName + '\'' +
                ", serverHost='" + serverHost + '\'' +
                ", serverRecommendUrl='" + serverRecommendUrl + '\'' +
                '}';
    }
}
