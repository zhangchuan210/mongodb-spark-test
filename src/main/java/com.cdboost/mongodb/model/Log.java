package com.cdboost.mongodb.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zc
 * @desc 测试实体类
 * @create 2017-09-04 15:10
 **/
@Document(collection = "log")
public class Log implements Serializable {
    private static final long serialVersionUID = 1L;

    private long ID;
    private String GatewayEUI;
    private int LogLevel;
    private String LogTime;
    private Content LogContent;
    private int send_flag;

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getGatewayEUI() {
        return GatewayEUI;
    }

    public void setGatewayEUI(String gatewayEUI) {
        GatewayEUI = gatewayEUI;
    }

    public int getLogLevel() {
        return LogLevel;
    }

    public void setLogLevel(int logLevel) {
        LogLevel = logLevel;
    }

    public String getLogTime() {
        return LogTime;
    }

    public void setLogTime(String logTime) {
        LogTime = logTime;
    }

    public Content getLogContent() {
        return LogContent;
    }

    public void setLogContent(Content logContent) {
        LogContent = logContent;
    }

    public int getSend_flag() {
        return send_flag;
    }

    public void setSend_flag(int send_flag) {
        this.send_flag = send_flag;
    }

    @Override
    public String toString() {
        return "Log{" +
                "ID:" + ID +
                ", GatewayEUI:'" + GatewayEUI + '\'' +
                ", LogLevel:" + LogLevel +
                ", LogTime:'" + LogTime + '\'' +
                ", LogContent:'" + LogContent + '\'' +
                ", send_flag:" + send_flag +
                '}';
    }
}
