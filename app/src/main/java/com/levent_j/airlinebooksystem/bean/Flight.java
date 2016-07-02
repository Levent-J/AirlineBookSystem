package com.levent_j.airlinebooksystem.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by levent_j on 16-7-2.
 */
public class Flight extends BmobObject{
    private String flightNo;
    private String flightType;
    private String originPlace;
    private String destinationPlace;
    private String data;
    private String startTime;
    private String endTime;
    private int price;
    private int bookedTickets;
    private int surplusTickets;

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getFlightType() {
        return flightType;
    }

    public void setFlightType(String flightType) {
        this.flightType = flightType;
    }

    public String getOriginPlace() {
        return originPlace;
    }

    public void setOriginPlace(String originPlace) {
        this.originPlace = originPlace;
    }

    public String getDestinationPlace() {
        return destinationPlace;
    }

    public void setDestinationPlace(String destinationPlace) {
        this.destinationPlace = destinationPlace;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getBookedTickets() {
        return bookedTickets;
    }

    public void setBookedTickets(int bookedTickets) {
        this.bookedTickets = bookedTickets;
    }

    public int getSurplusTickets() {
        return surplusTickets;
    }

    public void setSurplusTickets(int surplusTickets) {
        this.surplusTickets = surplusTickets;
    }
}
