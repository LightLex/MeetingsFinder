package com.fiec.meetingsfinder.service;

import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;

import com.fiec.meetingsfinder.model.MeetingSearch;
import com.fiec.meetingsfinder.model.Result;
import com.fiec.meetingsfinder.ui.OnFindMeeting;
import com.fiec.meetingsfinder.ui.OnFindSections;
import com.fiec.meetingsfinder.util.DataLoader;
import com.fiec.meetingsfinder.util.Tag;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by damato on 03/17/2015.
 */
public class Core {

    private static TrustManager[] trustAllCerts = new TrustManager[] {
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }
            }
    };

    public Core(OnFindSections sections, int region){
        new GetSections(sections).execute(region);
    }

    public Core(OnFindMeeting listener,String ID){
        new GetMeetingInfo(listener).execute(ID);
    }

    public Core(OnFindSections sections, String region, String section){
        Time t = new Time();
        t.setToNow();
        String format = "dd MMM yyyy HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        new GetMeetings(sections).execute(new Search(region,section,sdf.format(new Date(t.toMillis(true)))));
    }

    public Result getXy(String url)throws IOException,NoSuchAlgorithmException,KeyManagementException{
        String When=null;
        String Where=null;
        String Who=null;
        String More=null;
        String Speaker=null;
        String Agenda=null;
        PointF Point=new PointF();
        URL link=null;
        String map_image_url=null;
        String linkregistro=url;
        Tag.i("link de registro 1:"+ linkregistro);


        Tag.i("Executing");

        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (GeneralSecurityException e) {}

        try {
            link = new URL(url);
        } catch (MalformedURLException e) {        }

        BufferedReader in;
        try {
            in = new BufferedReader(new InputStreamReader(link.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null){

                if(inputLine.contains("Click Here to Register")){

                    String lineas[] = inputLine.split("href=");
                    String linea[] = lineas[1].split(">");
                    linkregistro="https://meetings.vtools.ieee.org/"+linea[0].substring(2,linea[0].length());
                    Tag.i("link de registro:"+ linkregistro);
                }

                if(inputLine.contains("https://maps.google.com/maps/api/staticmap"))
                {
                    String var[] = inputLine.split("src");
                    String var2=var[1];
                    map_image_url=var2.substring(2,var2.length()-4);
                    inputLine=var[1].substring(var[1].indexOf("markers"));
                    float x = Float.valueOf(inputLine.substring(inputLine.indexOf("=")+1,inputLine.indexOf("%2C")));
                    float y = Float.valueOf(inputLine.substring(inputLine.indexOf("%2C")+3,inputLine.indexOf('"')));
                    Point.set(x,y);
                }

                if(inputLine.contains("<span class=\"content-block-label shade-blue-hilite\" style=\"\">When</span>"))
                {
                    String Line = "";
                    do{
                        Line=Line+"\n"+inputLine;

                        inputLine=in.readLine();
                    }while(inputLine.contains("<!-- The following images (Outlook_Icon, and iCal_Icon) are copyrighted computer")==false);
                    Line=Line.replaceAll("</p>", "\n").replaceAll("\n", "").replaceAll("\\<.*?>","").replaceAll("\\&([a-z])+;", "");
                    Line=Line.replaceAll("  <span class=\"content-block-label shade-blue-hilite\" style=\"\">", "").replaceAll("   \\s+", "\n");
                    When=Line;
                }

                if(inputLine.contains("<span class=\"content-block-label shade-yellow-hilite\" style=\"\">Where</span>"))
                {
                    String Line = "";
                    do{
                        if(inputLine.contains("www.google.com")==false)
                            Line=Line+"\n"+inputLine;

                        inputLine=in.readLine();
                    }while(inputLine.contains("<div class=\"content-block shade-red\" style=\"width:460px; clear:both;\">")==false);
                    Line=Line.replaceAll("</p>", "\n").replaceAll("\n", "").replaceAll("\\<.*?>","").replaceAll("\\&([a-z])+;", "");
                    Line=Line.replaceAll("  <span class=\"content-block-label shade-yellow-hilite\" style=\"\">", "").replaceAll(" \\s+", "\n");
                    Where=Line;
                }

                if(inputLine.contains("<span class=\"content-block-label shade-red-hilite\" style=\"\">Who</span>"))
                {
                    String Line = "";
                    do{
                        if(inputLine.contains("<script type=\"text/javascript\">")==false)
                            Line=Line+"\n"+inputLine;

                        inputLine=in.readLine();
                    }while(inputLine.contains("</div>")==false);
                    Line=Line.replaceAll("</p>", "\n").replaceAll("\n", "").replaceAll("\\<.*?>","").replaceAll("\\&([a-z])+;", "");
                    Line=Line.replaceAll("  <span class=\"content-block-label shade-red-hilite\" style=\"\">", "").replaceAll(" \\s+", "\n");
                    Who=Line;
                }

                if(inputLine.contains("<span class=\"content-block-label shade-green-hilite\" style=\"\">More</span>"))
                {
                    String Line = "";
                    do{
                        if(inputLine.contains("<script type=\"text/javascript\">")==false)
                            Line=Line+"\n"+inputLine;

                        inputLine=in.readLine();
                    }while(inputLine.contains("</div>")==false);
                    Line=Line.replaceAll("</p>", "\n").replaceAll("\n", "").replaceAll("\\<.*?>","").replaceAll("\\&([a-z])+;", "");
                    Line=Line.replaceAll("  <span class=\"content-block-label shade-green-hilite\" style=\"\">", "").replaceAll(" \\s+", "\n");
                    More=Line;
                }

                if(inputLine.contains("<span class=\"content-block-label shade-grey-hilite\" style=\"\">Speaker"))
                {
                    String Line = "";
                    do{
                        if(inputLine.contains("text/javascript")==false){
                            if(inputLine.contains("<img")==false)
                                Line=Line+"\n"+inputLine;
                        }

                        inputLine=in.readLine();
                    }while(inputLine.contains("<br style=\"clear:both;\" />")==false);
                    Line=Line.replaceAll("</p>", "\n").replaceAll("\n", "").replaceAll("\\<.*?>","").replaceAll("\\&([a-z])+;", "");
                    Line=Line.replaceAll("  <span class=\"content-block-label shade-grey-hilite\" style=\"\">", "").replaceAll(" \\s+", "\n");
                    Speaker=Line;
                }

                if(inputLine.contains("<span class=\"content-block-label shade-yellow-hilite\" style=\"\">Agenda</span>"))
                {
                    String Line = "";
                    do{
                        if(inputLine.contains("text/javascript")==false)
                            Line=Line+"\n"+inputLine;

                        inputLine=in.readLine();
                    }while(inputLine.contains("<br style=\"clear:both;\" />")==false);
                    Line=Line.replaceAll("\n", "").replaceAll("</p>", "\n").replaceAll("\\<.*?>","").replaceAll("\\&([a-z])+;", "");
                    Line=Line.replaceAll("  <span class=\"content-block-label shade-yellow-hilite\" style=\"\">", "").replaceAll("  \\s+", "\n");
                    Agenda=Line;
                }

            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

            Result rest = new Result(When,Where,Who,More,Speaker,Agenda,Point,linkregistro);
            return rest;
    }

    public class GetSections extends AsyncTask<Integer,Void,Bundle>{
        private OnFindSections listener;

        public GetSections(OnFindSections listener){
            this.listener = listener;
        }

        @Override
        protected Bundle doInBackground(Integer... params) {
            String url = "https://meetings.vtools.ieee.org/meeting_view/get_sections";
            SSLContext sc;
            Bundle sec = null;
            try {
                DataLoader dl = new DataLoader();
                /*sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());*/
                List<NameValuePair> par = new ArrayList<>();
                par.add(new BasicNameValuePair("id",params[0]+""));

                /*HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(par));
                DefaultHttpClient sslClient = new DefaultHttpClient();*/
                //HttpResponse httpResponse = sslClient.execute(httpPost);
                HttpResponse httpResponse = dl.securePostData(url,par);
                InputStream is = httpResponse.getEntity().getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line;
                while((line = br.readLine())!=null){
                    sb.append(line);
                }
                br.close();
                httpResponse.getEntity().consumeContent();
                Document document = Jsoup.parse(sb.toString());
                Elements sections = document.select("select > option");
                List<String> opts = new ArrayList<>();
                List<String> values = new ArrayList<>();
                if(sections.size()>0) sec = new Bundle();
                for(Element element:sections){
                    if(!element.text().equals("") && !element.text().contains("region")) {
                        opts.add(element.text());
                        values.add(element.val());
                    }
                }
                sec.putStringArray("captions", opts.toArray(new String[0]));
                sec.putStringArray("values", values.toArray(new String[0]));
                //sec = opts.toArray(new String[0]);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (UnrecoverableKeyException e) {
                e.printStackTrace();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            }

            return sec;
        }

        @Override
        protected void onPostExecute(Bundle bun) {
            if(bun!=null) {
                listener.listregions(bun);
            }
        }
    }

    public class GetMeetings extends AsyncTask<Search,Void,List<MeetingSearch>>{

        private OnFindSections listener;

        public GetMeetings(OnFindSections listener){
            this.listener = listener;
        }

        @Override
        protected List<MeetingSearch> doInBackground(Search... params) {
            String Name=null;
            String ID=null;
            String Date=null;
            String Virtual=null;
            URL link=null;
            List<MeetingSearch> list = new ArrayList<>();

            try {
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            } catch (GeneralSecurityException e) {}

            try {
                link = new URL(params[0].getSearchURL());
                Tag.i(link+"");
            } catch (MalformedURLException e) {}
            BufferedReader in;
            try {
                in = new BufferedReader(new InputStreamReader(link.openStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null){
                    if(inputLine.contains("col_title")==true)
                    {
                        if(inputLine.contains("span title")==true){
                            Name = inputLine.substring(inputLine.indexOf("<span title=\""));
                            Name = Name.substring(Name.indexOf("=\""), Name.indexOf("\">"));
                            Name = Name.replaceAll("\\&([a-z])+;", "").replaceAll("=\"", "");
                        }else{
                            Name=inputLine.replaceAll("</p>", "\n").replaceAll("\n", "").replaceAll("\\<.*?>","").replaceAll("\\&([a-z])+;", "").replaceAll(" \\s+","");
                        }
                        ID = inputLine.substring(inputLine.indexOf("/m/"),inputLine.indexOf("'>"));
                        do{
                            inputLine = in.readLine();
                        }while(inputLine.contains("col_start_time")==false);
                        Date = inputLine.replaceAll("</p>", "\n").replaceAll("\n", "").replaceAll("\\<.*?>","").replaceAll("\\&([a-z])+;", "").replaceAll(" \\s+","");
                        do{
                            inputLine = in.readLine();
                        }while(inputLine.contains("col_virtual")==false);
                        Virtual = inputLine.replaceAll("</p>", "\n").replaceAll("\n", "").replaceAll("\\<.*?>","").replaceAll("\\&([a-z])+;", "").replaceAll(" \\s+","");
                        //
                        //GUARGAR LOS DATOS
                        list.add(new MeetingSearch(ID,Name,Date,Virtual));
                        //System.out.println(ID+"\t"+Name+"\t"+Date+"\t"+Virtual);
                        //
                        //
                    }

                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<MeetingSearch> list) {
            listener.listMeetings(list);
        }
    }

    public class GetMeetingInfo extends AsyncTask<String, Void,Result>{

        private OnFindMeeting listener;

        public GetMeetingInfo(OnFindMeeting listener) {
            this.listener = listener;
        }

        @Override
        protected Result doInBackground(String... params) {
            Result rest=null;
            try {
                rest = getXy("https://meetings.vtools.ieee.org"+params[0]);
            }catch(IOException e){}
            catch(NoSuchAlgorithmException e){}
            catch(KeyManagementException e){}
            return rest;
        }

        @Override
        protected void onPostExecute(Result result) {
            listener.findMeeting(result);
        }
    }

    //GETIMAGE
    public static Drawable LoadImage(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
}
