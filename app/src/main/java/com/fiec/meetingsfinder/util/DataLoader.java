package com.fiec.meetingsfinder.util;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;

import android.annotation.SuppressLint;
import android.os.Bundle;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.KeyManagementException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyStoreException;
import java.security.UnrecoverableKeyException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import java.security.SecureRandom;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

public class DataLoader {
	
	public DefaultHttpClient sslClient;
	
	@SuppressLint("TrulyRandom")
	public DataLoader() throws NoSuchAlgorithmException, KeyManagementException,
    KeyStoreException, UnrecoverableKeyException{
		SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(null, new TrustManager[] { new CustomX509TrustManager() },
                new SecureRandom());

        HttpClient client = new DefaultHttpClient();

        SSLSocketFactory ssf = new CustomSSLSocketFactory(ctx);
        ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        ClientConnectionManager ccm = client.getConnectionManager();
        SchemeRegistry sr = ccm.getSchemeRegistry();
        sr.register(new Scheme("https", ssf, 443));
        sslClient = new DefaultHttpClient(ccm,
                client.getParams());
	}

	public HttpResponse secureLoadData(String url) 
			throws URISyntaxException, ClientProtocolException, IOException{

        HttpGet get = new HttpGet(new URI(url));
        HttpResponse response = sslClient.execute(get);
        return response;
    }
	
	public HttpResponse secureGetData(String url, Bundle params) 
			throws URISyntaxException, ClientProtocolException, IOException{
		
		
        HttpGet get = new HttpGet(new URI(url));
        //get.setParams(params);
        HttpResponse response = sslClient.execute(get);
        return response;
    }
    
    public HttpResponse securePostData(String url, List<NameValuePair> params) 
    		throws ClientProtocolException, IOException{
    	 
    	HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        
        HttpResponse httpResponse = sslClient.execute(httpPost);
        
        Tag.i("Response: "+httpResponse.toString());
        //httpResponse.getEntity().consumeContent();
        
        return httpResponse;
    }

}