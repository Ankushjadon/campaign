//package com.vlink.campaign.controller;
//
//
//import com.eaio.uuid.UUID;
//import com.mongodb.BasicDBList;
//import com.mongodb.BasicDBObject;
//import com.vlink.campaign.com.vlink.util.AppConfig;
//import com.vlink.campaign.service.MongoDBService;
//import org.glassfish.jersey.media.multipart.FormDataBodyPart;
//import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
//import org.glassfish.jersey.media.multipart.FormDataParam;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.ws.rs.Consumes;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import java.io.*;
//import java.lang.reflect.Array;
//import java.util.*;
//
///**
// * Created by ankush.jadon on 4/25/2017.
// */
//@Path("/file")
//public class Upload {
//
//    static Logger logger = LoggerFactory.getLogger(Upload.class);
//    String campaignId;
//    @POST
//    @Path("/upload")
//    @Consumes({MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON})
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response uploadFile(@FormDataParam("data") FormDataBodyPart data,
//                               @FormDataParam("file") InputStream uploadedInputStream,
//                               @FormDataParam("file") FormDataContentDisposition fileDetail) {
//        logger.info("post method called ");
//        logger.info(data.getValue());
//        //  AppConfig appConfig = new AppConfig();
//        String uploadFolderPath = AppConfig.uploadFilePath;
//        JSONParser jsonParser = new JSONParser();
//        //  String uploadFolderPath = (String) appConfig.g
//        String uploadedFileLocation = uploadFolderPath
//                + System.currentTimeMillis() + "_" + fileDetail.getFileName();
//
//        // save it
//        System.out.println("file upload to" +uploadedFileLocation);
//
//        //String output = "File uploaded to : " + uploadedFileLocation;
//        logger.info("file uploaded successfully");
//        // logger.debug(emp.getData());
//        //  return Response.status(200).entity(output).build();
//
//        int statuscode = 200;
//        boolean status = true;
//        String message = "Campaign created successfuly";
//        JSONObject jsonObject = new JSONObject();
//
//        try {
//
//            //  JSONParser jsonParser = new JSONParser();
//            JSONObject jsonObject1 = (JSONObject) jsonParser.parse(data.getValue());
//            JSONObject jsonObject2 = new JSONObject();
//            String name = (String) jsonObject1.get("name");
////           String CampaignDescription = (String)jsonObject1.get("CampaignDescription");
////           String CampaignDomain = (String) jsonObject1.get("CampaignDomain");
////            String CampaignType =(String) jsonObject1.get("CampaignType");
////            Date EndDateTime =(Date) jsonObject1.get("EndDateTime");
////            Date StartDateTime =(Date) jsonObject1.get("StartDateTime");
////            Array rules = (Array) jsonObject1.get("rules");
////
////            jsonObject2.put("rules", rules);
////            jsonObject2.put("CampaignDescription", CampaignDescription);
////            jsonObject2.put("CampaignDomain", CampaignDomain);
////            jsonObject2.put("CampaignType", CampaignType);
////            jsonObject2.put("EndDateTime", EndDateTime);
////            jsonObject2.put("StartDateTime", StartDateTime);
//            jsonObject2.put("name", name);
//            jsonObject2.put("uploadedFileLocation", uploadedFileLocation);
//            logger.info(jsonObject2.toJSONString());
//            MongoDBService mongodbservice = new MongoDBService();
//            campaignId = mongodbservice.insert("campaign", jsonObject2);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            status = false;
//            message = "Unable to process request";
//            statuscode = 500;
//            logger.error("this is the error message" + e);
//        }
//        jsonObject.put("status", status);
//        jsonObject.put("message", message);
//        logger.debug("respone" + jsonObject);
//        writeToFile(uploadedInputStream, uploadedFileLocation);
//        Response response = Response.status(statuscode).entity(jsonObject.toJSONString()).build();
//        return response;
//
//    }
//
//    // save uploaded file to new location
//    public void writeToFile(InputStream uploadedInputStream,
//                            String uploadedFileLocation) {
//
//        MongoDBService mongoDBService = new MongoDBService();
//        OutputStream out = null;
//        try {
//            out = new FileOutputStream(new File(
//                    uploadedFileLocation));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        int read = 0;
//        byte[] bytes = new byte[1024];
//        JSONObject jsonObject = new JSONObject();
//        JSONArray jsonArray = new JSONArray();
//        try {
//            while ((read = uploadedInputStream.read(bytes)) != -1) {
//                out.write(bytes, 0, read);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        BufferedReader br = null;
//        String line = "";
//        String cvsSplitBy = ",";
//        StringTokenizer st = null;
//        String[] data;
//        String[] header;
//        try {
//            br = new BufferedReader(new FileReader(uploadedFileLocation));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        try {
//            String headerLine;
//            headerLine = br.readLine();
//            System.out.println(headerLine);
//            header=headerLine.split(cvsSplitBy);
//            st = new StringTokenizer(line, ",");
//
//            BasicDBObject basicDBObject1 = new BasicDBObject();
//            //   BasicDBList objectsList = new BasicDBList();
//            List<BasicDBObject> basicDBObjects = new ArrayList<>();
//            while ((line = br.readLine()) != null) {
//                // use comma as separator
//                //  JSONObject basicDBObject = new JSONObject();
//                BasicDBObject basicDBObject = new BasicDBObject();
//                UUID uuid = new UUID();
//                basicDBObject.put("_id", uuid.toString());
//                basicDBObject.put("campaignId", campaignId);
//                //  data  = line.split(cvsSplitBy);
//                st = new StringTokenizer(line, ",");
//                //  data=st.toString();
//                //  System.out.println(header.length);
//                //System.out.println(data.length);
//                //  for(int n=0;n<header.length;n++) {
//                //    basicDBObject.put(header[n], data[n]);
//                //}
//                int i=0;
//                while (st.hasMoreElements()){
//                    basicDBObject.put(header[i++],st.nextElement());
//                }
//
//                // jsonObject.put(header[1],data[1]);
//                //    jsonArray.add(jsonObject);
//                // int batch;
//                basicDBObjects.add(basicDBObject);
//
//                if(basicDBObjects.size()==1000) {
//                    //  basicDBObject1.put("objectList", basicDBObjects);
//                    mongoDBService.insertFileData("filedata", basicDBObjects);
//                }
//                out.flush();
//                out.close();
//            }
//            //   basicDBObject1.put("objectList", basicDBObjects);
//            mongoDBService.insertFileData("filedata", basicDBObjects);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}