package com.levent_j.airlinebooksystem.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by levent_j on 16-7-2.
 */
public class Ticket extends BmobObject{
    private String clientName;
    private String flightNo;
    private String data;
    private String destination;
    private int seatNo;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
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

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(int seatNo) {
        this.seatNo = seatNo;
    }
}
