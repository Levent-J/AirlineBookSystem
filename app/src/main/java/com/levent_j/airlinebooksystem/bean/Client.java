package com.levent_j.airlinebooksystem.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by levent_j on 16-7-2.
 */
public class Client extends BmobObject{
    private String name;
    private String idCard;
    private String flightNo;
    private String data;
    private Integer seatNo;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(Integer seatNo) {
        this.seatNo = seatNo;
    }
}
