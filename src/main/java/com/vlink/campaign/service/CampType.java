package com.vlink.campaign.service;

import com.mongodb.DBObject;
import jdk.nashorn.internal.parser.JSONParser;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Job;

/**
 * Created by ankush.jadon on 5/3/2017.
 */
public class CampType implements Job {
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("execute method called");
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        String campaignId = jobDataMap.getString("campaignId");
        MongoDBService mongoDBService = new MongoDBService();
     DBObject campaign = mongoDBService.findById("campaign", campaignId);
     String campaignType = (String) campaign.get("campaignType");
//        System.out.println("to string" +campaign.toString());
      System.out.println("type"+campaignType);
        if(campaignType.equals("SMS")){

        }
        if(campaignType.equals("Email")) {

        }


    }
}
