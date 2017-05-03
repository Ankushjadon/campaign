package com.vlink.campaign.scheduler;

import com.vlink.campaign.controller.Upload;
import com.vlink.campaign.model.Email;
import com.vlink.campaign.model.SMS;
import com.vlink.campaign.service.CampType;
import com.vlink.campaign.service.SendEmail;
import com.vlink.campaign.service.SendSMS;
import org.eclipse.persistence.internal.helper.Helper;
import org.json.simple.JSONObject;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Date;
import java.util.Set;

/**
 * Created by ankush.jadon on 4/17/2017.
 */
public class SchedulerJob
{



    static Logger logger = LoggerFactory.getLogger(SchedulerJob.class);

    public  void schedule(String campaignId, Long scheduleDateTime) {
        logger.info("emailSchedule method called");
        Long schedulerDateTime = scheduleDateTime;
        Date startDate = new Date(schedulerDateTime);
        logger.info("schedule time " + startDate);
        JobDetail job = JobBuilder.newJob(CampType.class)
                .withIdentity("userid", "campaign").usingJobData("campaignId", campaignId).build();
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("userid", "campaign").startAt(startDate)
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(24).withRepeatCount(0))
                .build();

        Scheduler scheduler = null;
        try {
            scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            logger.error("emailschedule method error" + e);
        }

    }

//    public  void emailSchedule(Email email) {
//        logger.info("emailSchedule method called");
//        Date startDate = new Date(email.getStartdate());
//        Date endDate = new Date(email.getEnddate());
//        logger.info("schedule time " + startDate);
//        JobDetail job = JobBuilder.newJob(SendEmail.class)
//                .withIdentity("userid", "campaign").usingJobData("to", email.getTo()).usingJobData("subject", email.getSubject()).usingJobData("body", email.getBody()).build();
//        Trigger trigger = TriggerBuilder
//                .newTrigger()
//                .withIdentity("userid", "campaign").startAt(startDate)
//                .withSchedule(
//                        SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(24).withRepeatCount(0)).endAt(endDate)
//                .build();
//
//        Scheduler scheduler = null;
//        try {
//            scheduler = new StdSchedulerFactory().getScheduler();
//            scheduler.start();
//            scheduler.scheduleJob(job, trigger);
//        } catch (SchedulerException e) {
//            logger.error("emailschedule method error" + e);
//        }
//
//    }
//
//    public  void smsSchedule(SMS sms) {
//        logger.info("smsSchedule methdo called");
//        Date startdate = new Date(sms.getStartdate());
//        Date enddate = new Date(sms.getEnddate());
//
//        logger.info("schedule time" + startdate);
//        JobDetail job = JobBuilder.newJob(SendSMS.class)
//                .withIdentity("userid", "campaign").usingJobData("to", sms.getTo()).usingJobData("message", sms.getMessage()).build();
//        Trigger trigger = TriggerBuilder
//                .newTrigger()
//                .withIdentity("userid", "campaign").startAt(startdate)
//                .withSchedule(
//                        SimpleScheduleBuilder.simpleSchedule()
//                                .withIntervalInHours(24).withRepeatCount(0)).endAt(enddate)
//                .build();
//        Scheduler scheduler = null;
//        try {
//            scheduler = new StdSchedulerFactory().getScheduler();
//            scheduler.start();
//            scheduler.scheduleJob(job, trigger);
//        } catch (SchedulerException e) {
//            //e.printStackTrace();
//            logger.error("smsschedule error" + e);
//        }
//
//    }
}
