package com.fiec.meetingsfinder.service;

import android.net.Uri;
import android.os.Bundle;


/**
 * Created by Mukei on 7/4/15.
 */

public class Search {
    String utf8 = "";
    String searchTerm="";
    String category_id="";
    String meeting_region_id="";
    String subcategory_id="";
    String section_id = "";
    String society="";
    String meeting_organization_id="";
    String meeting_after="";
    String meeting_before="";
    String geo_distance="";
    String geo_search="";
    String order="start_time";
    String per_page="20";

    //constructor completo
    public Search(String searchTerm, String category_id, String meeting_region_id, String subcategory_id, String section_id,String society, String meeting_organization_id, String meeting_after, String meeting_before, String geo_distance, String geo_search, String order, String per_page) {
        this.searchTerm = searchTerm;
        this.category_id = category_id;
        this.meeting_region_id = meeting_region_id;
        this.subcategory_id = subcategory_id;
        this.section_id = section_id;
        this.society = society;
        this.meeting_organization_id = meeting_organization_id;
        this.meeting_after = meeting_after;
        this.meeting_before = meeting_before;
        this.geo_distance = geo_distance;
        this.geo_search = geo_search;
        this.order = order;
        this.per_page = per_page;
    }

    public Search(String meeting_region_id, String section_id, String timestamp) {
        this.meeting_region_id = meeting_region_id;
        this.section_id = section_id;
        this.meeting_after = timestamp;
    }

//SE PUEDEN AGREGAR MAS CONSTRUCTORES O UtILIZAR EL PRIMERO Y AGREGAR EL RESTO DE LOS PARAMETROS POR GET/SET

    public String getSearchURL(){

        //ESTO CONSTRUYE EL URL DE SEARCH CON LOS PARAMETROS QUE SE HAYAN METIDO POR CONSTRUCTOR O POR SETTERS, el resto es vacio como se inicializan
        Uri uri = new Uri.Builder()
                .scheme("https")
                .authority("meetings.vtools.ieee.org")
                .appendPath("meeting_view")
                .appendPath("search")
                .appendQueryParameter("utf8",utf8)
                .appendQueryParameter("sub", "true")
                .appendQueryParameter("store_values", "true")
                .appendQueryParameter("search", searchTerm)
                .appendQueryParameter("category_id", category_id)
                .appendQueryParameter("meeting[region_id]", meeting_region_id)
                .appendQueryParameter("subcategory_id", subcategory_id)
                .appendQueryParameter("meeting[section_id]",section_id)
                .appendQueryParameter("society", society)
                .appendQueryParameter("meeting[organization_id]", meeting_organization_id)
                .appendQueryParameter("meeting_after", meeting_after)
                .appendQueryParameter("meeting_before", meeting_before)
                .appendQueryParameter("geo_distance", geo_distance)
                .appendQueryParameter("geo_search", geo_search)
                .appendQueryParameter("order", order)
                .appendQueryParameter("per_page", per_page)
                .build();

        return uri.toString();

    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getMeeting_region_id() {
        return meeting_region_id;
    }

    public void setMeeting_region_id(String meeting_region_id) {
        this.meeting_region_id = meeting_region_id;
    }

    public String getSection_id() {
        return section_id;
    }

    public void setSection_id(String section_id) {
        this.section_id = section_id;
    }

    public String getSubcategory_id() {
        return subcategory_id;
    }

    public void setSubcategory_id(String subcategory_id) {
        this.subcategory_id = subcategory_id;
    }

    public String getSociety() {
        return society;
    }

    public void setSociety(String society) {
        this.society = society;
    }

    public String getMeeting_organization_id() {
        return meeting_organization_id;
    }

    public void setMeeting_organization_id(String meeting_organization_id) {
        this.meeting_organization_id = meeting_organization_id;
    }

    public String getMeeting_after() {
        return meeting_after;
    }

    public void setMeeting_after(String meeting_after) {
        this.meeting_after = meeting_after;
    }

    public String getMeeting_before() {
        return meeting_before;
    }

    public void setMeeting_before(String meeting_before) {
        this.meeting_before = meeting_before;
    }

    public String getGeo_distance() {
        return geo_distance;
    }

    public void setGeo_distance(String geo_distance) {
        this.geo_distance = geo_distance;
    }

    public String getGeo_search() {
        return geo_search;
    }

    public void setGeo_search(String geo_search) {
        this.geo_search = geo_search;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getPer_page() {
        return per_page;
    }

    public void setPer_page(String per_page) {
        this.per_page = per_page;
    }
}
