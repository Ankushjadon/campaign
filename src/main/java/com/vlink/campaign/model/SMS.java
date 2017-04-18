package com.vlink.campaign.model;

/**
 * Created by ankush.jadon on 4/18/2017.
 */
public class SMS {
    private String to;
    private String message;
    private long date;

    public void setTo(String to) {
        this.to = to;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getTo() {

        return to;
    }

    public String getMessage() {
        return message;
    }

    public long getDate() {
        return date;
    }
}
