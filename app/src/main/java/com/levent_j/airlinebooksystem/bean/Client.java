package com.levent_j.airlinebooksystem.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by levent_j on 16-7-2.
 */
public class Client extends BmobObject{
    private String name;
    private String idCard;
    private String flightNo;
    private int seatNo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public int getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(int seatNo) {
        this.seatNo = seatNo;
    }
}
