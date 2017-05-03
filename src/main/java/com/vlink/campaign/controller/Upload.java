package com.vlink.campaign.controller;


import com.eaio.uuid.UUID;
import com.mongodb.BasicDBObject;
import com.vlink.campaign.com.vlink.util.AppConfig;
import com.vlink.campaign.scheduler.SchedulerJob;
import com.vlink.campaign.service.MongoDBService;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.*;

/**
 * Created by ankush.jadon on 4/25/2017.
 */
@Path("/file")
public class Upload {

    static Logger logger = LoggerFactory.getLogger(Upload.class);
//    public Long scheduleDateTime;
//    public String campaignId;

    @POST
    @Path("/upload")
    @Consumes({MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadFile(@FormDataParam("data") FormDataBodyPart data,
                               @FormDataParam("file") InputStream uploadedInputStream,
                               @FormDataParam("file") FormDataContentDisposition fileDetail) {
        logger.info("post method called for uploading campaigndata and filedata");
        logger.info(data.getValue());
       String uploadFolderPath = AppConfig.uploadFilePath;
        JSONParser jsonParser = new JSONParser();
        String uploadedFileFolder = System.currentTimeMillis() + "_" + fileDetail.getFileName();

        // save it
        logger.debug("file upload to" +uploadedFileFolder);
        int statuscode = 200;
        boolean status = true;
        String message = "Campaign created successfuly";
        JSONObject jsonObject = new JSONObject();

        try {

          //  JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject1 = (JSONObject) jsonParser.parse(data.getValue());
            JSONObject jsonObject2 = new JSONObject();
//            String name = (String) jsonObject1.get("name");
//            jsonObject2.put("name", name);
           String campaignDescription = (String)jsonObject1.get("campaignDescription");
           String campaignMessage = (String) jsonObject1.get("campaignMessage");
           String campaignType =(String) jsonObject1.get("campaignType");

           Long scheduleDateTime =(Long) jsonObject1.get("scheduleDateTime");
            JSONArray rules = (JSONArray) jsonObject1.get("rules");

            jsonObject2.put("campaignDescription", campaignDescription);
            jsonObject2.put("campaignMessage", campaignMessage);
            jsonObject2.put("campaignType", campaignType);
            jsonObject2.put("scheduleDateTime", scheduleDateTime);
            jsonObject2.put("rules", rules);
            jsonObject2.put("uploadedFileFolder", uploadedFileFolder);
            logger.info(jsonObject2.toJSONString());
            MongoDBService mongodbservice = new MongoDBService();
            String campaignId = mongodbservice.insert("campaign", jsonObject2);
            SchedulerJob schedulerJob = new SchedulerJob();
            String uploadedFileLocation = uploadFolderPath +uploadedFileFolder;
            writeToFile(uploadedInputStream, uploadedFileLocation, campaignId);
            schedulerJob.schedule(campaignId, scheduleDateTime);
        } catch (Exception e) {
            e.printStackTrace();
            status = false;
            message = "Unable to process request";
            statuscode = 500;
            logger.error("this is the error message" + e);
        }
        jsonObject.put("status", status);
        jsonObject.put("message", message);
        logger.debug("respone" + jsonObject);
        logger.info("file uploaded successfully");
        Response response = Response.status(statuscode).entity(jsonObject.toJSONString()).build();
        return response;

    }

    // save uploaded file to new location
    public void writeToFile(InputStream uploadedInputStream,
                             String uploadedFileLocation, String campaignId) {

        MongoDBService mongoDBService = new MongoDBService();
        OutputStream out = null;
        try {
            out = new FileOutputStream(new File(
                    uploadedFileLocation));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int read = 0;
        byte[] bytes = new byte[1024];
        try {
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        String[] data;
        String[] header;
        try {
            br = new BufferedReader(new FileReader(uploadedFileLocation));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            String headerLine;
            headerLine = br.readLine();
            System.out.println(headerLine);
            header=headerLine.split(cvsSplitBy);
            List<BasicDBObject> basicDBObjects = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                BasicDBObject basicDBObject = new BasicDBObject();
                UUID uuid = new UUID();
                basicDBObject.put("_id", uuid.toString());
                basicDBObject.put("campaignId", campaignId);
                 data  = line.split(",", header.length);
                 for(int n=0;n<header.length;n++) {
                     basicDBObject.put(header[n], data[n]);
                 }
                    basicDBObjects.add(basicDBObject);

                if(basicDBObjects.size()==1000) {
                    mongoDBService.insertFileData("filedata", basicDBObjects);
                    basicDBObjects.clear();
                }
                out.flush();
                out.close();
            }
            mongoDBService.insertFileData("filedata", basicDBObjects);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}