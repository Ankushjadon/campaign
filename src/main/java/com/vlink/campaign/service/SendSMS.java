package com.vlink.campaign.service;

import com.vlink.campaign.model.SMS;
import org.quartz.Job;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.vlink.campaign.scheduler.SchedulerJob;
import org.joda.time.DateTime;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by ankush.jadon on 4/17/2017.
 */
public class SendSMS implements Job {

    public static final String ACCOUNT_SID = "AC2ebc43ebd0a9bbe649945a2b44a6fe12";
    public static final String AUTH_TOKEN = "aa556e00df28119726459b6d3ed8f606";


    public void execute(JobExecutionContext context) throws JobExecutionException {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        String to = jobDataMap.getString("to");
        String message = jobDataMap.getString("message");
//        SchedulerJob schedulerJob = new SchedulerJob();
//        schedulerJob.setSchedule(dateTime.toDateTime(), getClass());
        PhoneNumber to_number = new PhoneNumber(to);
        PhoneNumber from_number = new PhoneNumber("+12566459450");

        Message messg = Message.creator(
                to_number, // TO number
                from_number, // From Twilio number
                message
        ).create();
        System.out.println(messg.toString());
    };
//
//    public void sendMessage(String to, String message, DateTime dateTime) {
//
//
//    }
}
