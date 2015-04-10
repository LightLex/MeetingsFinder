package com.fiec.meetingsfinder.service;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by Mukei on 5/4/15.
 */
public class Register {

    String auth_token;
    String meeting_id;
    String first_name;
    String last_name;
    String member_number;
    String address1;
    String address2;
    String city;
    String postal_code;
    String email;
    String phone;
    String submit_value;


    public Register(String auth_token, String meeting_id, String first_name, String last_name, String member_number, String address1, String address2, String city, String postal_code, String email, String phone, String submit_value) {
        this.auth_token = auth_token;
        this.meeting_id = meeting_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.member_number = member_number;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.postal_code = postal_code;
        this.email = email;
        this.phone = phone;
        this.submit_value = submit_value;
    }

    public void postData() {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("https://meetings.vtools.ieee.org/meeting_registration/create/"+meeting_id);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(14);
            nameValuePairs.add(new BasicNameValuePair("authenticity_token", auth_token));
            nameValuePairs.add(new BasicNameValuePair("meeting_id", meeting_id));
            nameValuePairs.add(new BasicNameValuePair("registrations[1][meeting_id]", meeting_id));
            //este que sigue va vacio, creo que es para cuando estas logeado en el sitio web
            nameValuePairs.add(new BasicNameValuePair("registrations[1][ldap_user_id]", ""));
            nameValuePairs.add(new BasicNameValuePair("registrations[1][first_name]", first_name));
            nameValuePairs.add(new BasicNameValuePair("registrations[1][last_name]", last_name));
            nameValuePairs.add(new BasicNameValuePair("registrations[1][member_number]", member_number));
            nameValuePairs.add(new BasicNameValuePair("registrations[1][address1]", address1));
            nameValuePairs.add(new BasicNameValuePair("registrations[1][address2][]", address2));
            nameValuePairs.add(new BasicNameValuePair("registrations[1][city]", city));
            nameValuePairs.add(new BasicNameValuePair("registrations[1][postal_code]", postal_code));
            nameValuePairs.add(new BasicNameValuePair("registrations[1][email]", email));
            nameValuePairs.add(new BasicNameValuePair("registrations[1][phone]", phone));
            nameValuePairs.add(new BasicNameValuePair("commit", submit_value));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
