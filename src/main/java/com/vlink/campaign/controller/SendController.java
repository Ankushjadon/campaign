package com.vlink.campaign.controller;

import com.vlink.campaign.model.Email;
import com.vlink.campaign.model.SMS;
import com.vlink.campaign.scheduler.SchedulerJob;
import com.vlink.campaign.service.SendEmail;
import com.vlink.campaign.service.SendSMS;
import org.joda.time.DateTime;
import org.json.simple.JSONObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by ankush.jadon on 4/17/2017.
 */
@Path("/send")
public class SendController {
    @POST
    @Path("/sms")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sendSMS(SMS sms) throws Exception {
        int statuscode = 200;
        boolean status = true;
        String message1 = "message scheduler call ";
        JSONObject jsonObject = new JSONObject();
        try {
//            SendSMS sendsms = new SendSMS();
//            String to = new String(sms.getTo());
//            String message = new String(sms.getMessage());
//            DateTime dateTime = new DateTime(sms.getDate());
            SchedulerJob schedulerJob = new SchedulerJob();
            schedulerJob.smsSchedule(sms);
//            Class classObj = sendsms.getClass();
//            schedulerJob.smsSchedule(dateTime, classObj);
//            System.out.println(dateTime);
        } catch (Exception e) {
            System.out.println(e);
            status = false;
            message1 = "Unable to process request";
            statuscode = 500;
        }
        jsonObject.put("status", status);
        jsonObject.put("message", message1);
        System.out.println(message1);
        return Response.status(statuscode).entity(jsonObject.toJSONString()).build();

    }

    @POST
    @Path("/email")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sendEMail(Email email) {
        int statuscode = 200;
        boolean status = true;
        String message = "scheduler call successfuly";
        JSONObject jsonObject = new JSONObject();

        try {
//            SendEmail sendEmail = new SendEmail();
//            sendEmail.setEmail(email);
//            DateTime dateTime = new DateTime(email.getDate());
            SchedulerJob schedulerJob = new SchedulerJob();
            schedulerJob.emailSchedule(email);
//            SendEmail.sendMail(data.get("to").toString(), data.get("sub").toString(), data.get("msg").toString(), dateTime);
        } catch (Exception e) {
            status = false;
            message = "unable to send email";
            statuscode = 500;
        }
        jsonObject.put("ststus", status);
        jsonObject.put("message", message);
        Response response = Response.status(statuscode).entity(jsonObject.toJSONString()).build();
        return response;
    }

}
