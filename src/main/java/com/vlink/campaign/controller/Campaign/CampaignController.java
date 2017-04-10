package com.vlink.campaign.controller.Campaign;

import com.mongodb.DBObject;
import com.vlink.campaign.service.MongoDBService;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ankush.jadon on 4/7/2017.
 */

@Path("/campaign")
public class CampaignController {

    static Logger log = Logger.getLogger(CampaignController.class);

    @GET
    @Produces("application/json")
    public Response getCampaign() {
        log.info("getcampaign method called");
        int statuscode = 200;
        boolean status = true;
        String message = "Campaign fetch successfuly";
        JSONObject jsonObject = new JSONObject();
        try {
            MongoDBService mongodbservice = new MongoDBService();
            List<DBObject> dbObjects = mongodbservice.get("campaign");
            jsonObject.put("campaignList", dbObjects);
            System.out.print(dbObjects);
        } catch (Exception e) {
            status = false;
            message = "Unable to process request";
            statuscode = 500;
            log.error("This is the error message" +e);
        }
        jsonObject.put("status", status);
        jsonObject.put("message", message);
        log.debug("response" +jsonObject);
        //	System.out.println(jsonObject.toJSONString());
        Response response = Response.status(statuscode).entity(jsonObject.toJSONString()).build();
        return response;
    }

    @GET
    @Produces("application/json")
    @Path("/{id}")
    public Response getCampaign(@PathParam("id") String id) {
        log.info("get campaign via id method called");
        int statuscode = 200;
        boolean status = true;
        String message = "Campaign fetch successfuly";
        JSONObject jsonObject = new JSONObject();
        try {
            MongoDBService mongodbservice = new MongoDBService();
            DBObject dbObject = mongodbservice.findById("campaign", id);
            jsonObject.put("campaign", dbObject);
        } catch (Exception e) {
            status = false;
            message = "Unable to process request";
            statuscode = 500;
            log.error("this is the error message" +e);
        }
        jsonObject.put("status", status);
        jsonObject.put("message", message);
        log.debug("respone" +jsonObject);
        Response response = Response.status(statuscode).entity(jsonObject.toJSONString()).build();
        return response;
    }

    @POST
    @Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
    public Response postCampaign(MultivaluedMap<String, String> campaignData) {
        log.info("post method called ");
        int statuscode = 200;
        boolean status = true;
        String message = "Campaign created successfuly";
        JSONObject jsonObject = new JSONObject();
        try {
            Map<String, String> data = new HashMap<String, String>();
            for (Map.Entry<String, List<String>> entry : campaignData.entrySet()) {
                data.put(entry.getKey(), entry.getValue().get(0));
            }
            MongoDBService mongodbservice = new MongoDBService();
            mongodbservice.insert("campaign", data);
        } catch (Exception e) {
            status = false;
            message = "Unable to process request";
            statuscode = 500;
            log.error("this is the error message" +e);
        }
        jsonObject.put("status", status);
        jsonObject.put("message", message);
        log.debug("respone" +jsonObject);
        Response response = Response.status(statuscode).entity(jsonObject.toJSONString()).build();
        return response;
    }

	@PUT
	@Produces("application/json")
	@Consumes("application/x-www-form-urlencoded")
	@Path("/{id}")
	public Response putCampaign(MultivaluedMap<String, String> campaignData, @PathParam("id") String id) {
        log.info("put method called");
		int statuscode = 200;
		boolean status = true;
		String message = "Campaign updated successfuly";
		JSONObject jsonObject = new JSONObject();

		try {
            MongoDBService mongoDBService = new MongoDBService();
            DBObject dbObject = mongoDBService.findById("campaign", id);
            Map<String, String> data = new HashMap<String, String>();
			for (Map.Entry<String, List<String>> entry : campaignData.entrySet()) {
				data.put(entry.getKey(), entry.getValue().get(0));
                mongoDBService.update("campaign", data, id);
			}
		} catch (Exception e) {
			// TODO: handle exception
			status = false;
			message = "Unable to process request";
			statuscode = 500;
			log.error("error mesaage" +e);
		}
		jsonObject.put("status", status);
		jsonObject.put("message", message);
		log.debug("response" +jsonObject);
		Response response = Response.status(statuscode).entity(jsonObject.toJSONString()).build();
		return response;
	}

	@DELETE
	@Produces("application/json")
	@Path("/{id}")
    public Response deleteCampaign(@PathParam("id") String id) {
        log.info("delete method called");
        int statuscode = 200;
        boolean status = true;
        String message = "campaign deleted successfuly";
     JSONObject jsonObject = new JSONObject();
     try {
         MongoDBService mongoDBService = new MongoDBService();
         DBObject dbObject = mongoDBService.findById("campaign", id);
         mongoDBService.delete("campaign", id);
     } catch (Exception e){
         status = false;
         statuscode = 500;
         message = "process request failed";
         log.error("error message" +e);
     }
     jsonObject.put("status", status);
     jsonObject.put("message", message);
     log.debug("response" +jsonObject);
     Response response = Response.status(statuscode).entity(jsonObject.toJSONString()).build();
     return response;
    }
    /*
    @DELETE
    @Produces("application/json")
    public Response deleteCampaign() {
        JSONObject jsonObject = new JSONObject();
        try {
            MongoDBService mongoDBService = new MongoDBService();
            mongoDBService.delete();
        } catch (Exception e) {
            System.out.print("unable to delete");
        }
        Response response = Response.status(200).entity(jsonObject.toJSONString()).build();
        return  response;
    }*/
}
