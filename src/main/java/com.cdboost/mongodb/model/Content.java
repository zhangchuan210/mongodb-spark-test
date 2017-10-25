package com.cdboost.mongodb.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zc
 * @desc
 * @create 2017-09-08 15:17
 **/
public class Content implements Serializable {
    private static final long serialVersionUID = 2L;
    private double ackr;
    private int dwnb;
    private String eui;
    private int rxfw;
    private int rxnb;
    private int rxok;
    private String time;
    private int txnb;

    public double getAckr() {
        return ackr;
    }

    public void setAckr(double ackr) {
        this.ackr = ackr;
    }

    public int getDwnb() {
        return dwnb;
    }

    public void setDwnb(int dwnb) {
        this.dwnb = dwnb;
    }

    public String getEui() {
        return eui;
    }

    public void setEui(String eui) {
        this.eui = eui;
    }

    public int getRxfw() {
        return rxfw;
    }

    public void setRxfw(int rxfw) {
        this.rxfw = rxfw;
    }

    public int getRxnb() {
        return rxnb;
    }

    public void setRxnb(int rxnb) {
        this.rxnb = rxnb;
    }

    public int getRxok() {
        return rxok;
    }

    public void setRxok(int rxok) {
        this.rxok = rxok;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTxnb() {
        return txnb;
    }

    public void setTxnb(int txnb) {
        this.txnb = txnb;
    }
}
