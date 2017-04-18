package com.vlink.campaign.model;

/**
 * Created by ankush.jadon on 4/18/2017.
 */
public class Email {
    private String to;
    private String subject;
    private String body;
    private long date;

    public void setTo(String to) {
        this.to = to;
    }

    public void setsubject(String subject) {
        this.subject = subject;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getTo() {

        return to;
    }

    public String getsubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public long getDate() {
        return date;
    }
}
