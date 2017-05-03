package com.vlink.campaign.com.vlink.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

/**
 * Created by ankush.jadon on 4/28/2017.
 */
public class AppConfig {
    public  static final String uploadFilePath = "E:/test1/";
//     String uploadFolder = null;
//
//        try {
//            JSONParser jsonParser = new JSONParser();
//           Object obj = jsonParser.parse(new FileReader("app.json"));
//            JSONObject jsonObject = (JSONObject) obj;
//            System.out.println("read json file"+jsonObject.toJSONString());
//            uploadFolder = (String) jsonObject.get("uploadFolderPath");
//            JSONObject  jsonObject1 = new JSONObject();
//            jsonObject1.put("uploadFolderPath", uploadFolder);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        return uploadFolder;
//    }
}
