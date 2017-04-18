package com.vlink.campaign.scheduler;

import com.vlink.campaign.model.Email;
import com.vlink.campaign.model.SMS;
import com.vlink.campaign.service.SendEmail;
import com.vlink.campaign.service.SendSMS;
import org.joda.time.DateTime;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Set;

/**
 * Created by ankush.jadon on 4/17/2017.
 */
public class SchedulerJob {
    public  void emailSchedule(Email email) {
//        long epoch = System.currentTimeMillis()/1000;
//          dateTime.setTime(epoch);
        //      System.out.println(dateTime);
        DateTime dateTime = new DateTime(email.getDate());
        int mm = dateTime.getMinuteOfHour();
        int hh = dateTime.getHourOfDay();
        int dd = dateTime.getDayOfMonth();
        int MM = dateTime.getMonthOfYear();
        int YYYY = dateTime.getYear();
        System.out.println(YYYY);
        String cronExp = "0" + " " + mm + " " + hh + " "  + dd + " " + MM + " " + "?" + " " + YYYY;

        System.out.println(cronExp);
        JobDetail job = JobBuilder.newJob(SendEmail.class)
                .withIdentity("userid", "campaign").usingJobData("to", email.getTo()).usingJobData("subject", email.getsubject()).usingJobData("body", email.getBody()).build();
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("userid", "campaign")
                .withSchedule(
                        CronScheduleBuilder.cronSchedule(cronExp))
                .build();

        Scheduler scheduler = null;
        try {
            scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
            System.out.println("Scheduler" + e );
        }

    }

    public  void smsSchedule(SMS sms) {
        DateTime dateTime = new DateTime(sms.getDate());
        int mm = dateTime.getMinuteOfHour();
        int hh = dateTime.getHourOfDay();
        int dd = dateTime.getDayOfMonth();
        int MM = dateTime.getMonthOfYear();
        int YYYY = dateTime.getYear();
        System.out.println(YYYY);
        String cronExp = "0" + " " + mm + " " + hh + " "  + dd + " " + MM + " " + "?" + " " + YYYY;

        System.out.println(cronExp);
        JobDetail job = JobBuilder.newJob(SendSMS.class)
                .withIdentity("userid", "campaign").usingJobData("to", sms.getTo()).usingJobData("message", sms.getMessage()).build();
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("userid", "campaign")
                .withSchedule(
                        CronScheduleBuilder.cronSchedule(cronExp))
                .build();

        Scheduler scheduler = null;
        try {
            scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
            System.out.println("Scheduler" + e );
        }

    }
}
