package ua.thinkmobiles.HumanExpert.net;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HandleHTTP {

    private static HandleHTTP instance = null;

    private String url;

    private HandleHTTP(){}

    public static HandleHTTP getInstance(){
        if (null == instance){
            instance = new HandleHTTP();
        }
        return instance;
    }

    public String getRequest(String url) {
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        } catch (IOException e){
            return "";
        }
    }
}
