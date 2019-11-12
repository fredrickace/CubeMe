package com.cube_me.cubeme_crm;

import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;


/**
 * Created by FredrickCyril on 11/3/16.
 */

public class JSONFunctions{

    public static JSONObject login(String urls, String userName, String password){
        JSONObject resultObject = null;
        HttpURLConnection urlConnection;
        URL url;
        try {

//            url = new URL("http://mindnotix.net/cube-me.org/api/auth");
            url = new URL(urls);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            urlConnection.setRequestMethod("POST");

            JSONObject userObj = new JSONObject();
            userObj.put("username_email",userName);
            userObj.put("password",password);
            //WRITE
            DataOutputStream outputStream = new DataOutputStream(urlConnection.getOutputStream());  //urlConnection.getOutputStream();
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
//            writer.

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(getPostDataString(userObj));
//            outputStream.writeBytes(userObj.toString());
            writer.flush();
            writer.close();
            outputStream.close();


           /*  SAMPLE FOR CONVERTING THE STRING TO A HEADER
           String userCredentials = "username:password";
            String basicAuth = "Basic " + new String(new Base64().encode(userCredentials.getBytes()));
            */

            int responseCode = urlConnection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                String response;
                Reader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

                StringBuilder sb = new StringBuilder();
                for (int c; (c = in.read()) >= 0; )
                    sb.append((char) c);
                response = sb.toString();

                Log.i("Response",response);
                resultObject = new JSONObject(response);

            }else {
                Log.i("Login Response", (String.valueOf(responseCode)));
            }
            urlConnection.connect();


        }catch (JSONException e){
            Log.e("Connection Error:", e.toString());
        }


        catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            Log.e("EXCEPTION",e.toString());
        }

        return resultObject;
    }


    public static JSONObject getUserDetails(String userURL, String token ){

        HttpURLConnection urlConnection;
        JSONObject resultObj = null;
        URL url;
        String result = "";
        byte[] encodedToken = Base64.encode(token.getBytes(), Base64.DEFAULT);
        Log.i("Encoded Token", encodedToken.toString());
//        String encodedToken =  new String(new Base64().encode(token.getBytes()));
        try {

            url = new URL(userURL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("_token", token);
            urlConnection.setRequestProperty("Content - Type", "application/x-www-form-urlencoded");
            urlConnection.connect();

            int responseCode = urlConnection.getResponseCode();
            String responseMsg = urlConnection.getResponseMessage();
            Log.i("Response Code", String.valueOf(responseCode));
            Log.i("Response Msg", responseMsg);
            if(responseCode == HttpURLConnection.HTTP_OK){
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while(data!= -1){
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

                resultObj = new JSONObject(result);
            }



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return resultObj;
    }


    public static String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }

}
