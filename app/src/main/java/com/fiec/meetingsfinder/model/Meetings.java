package com.fiec.meetingsfinder.model;

import android.graphics.PointF;

/**
 * Created by damato on 03/17/2015.
 */
public class Meetings {
//detalles comentados a la derecha del campo, ningun campo es obligatorio excepto el mid
    private int mid; // numerito al final del url
    private String title;
    private int region;
    private String section;
    private String org_unit;
    private boolean virtual;
    private String category;
    private String sub_category;
    private String society;
    private String description;
    private String date;//esto es el when1
    private String time;//when2
    private String timezone;//when3
    private String address; //where1
    private String building; //where2
    private String contact;//who
    private float longitude;//podemos considerar llamarlos lat y lon si es muy largo
    private float latitude;
    private String additional_info;//more
    private String speaker;//speaker
    private String agenda;//agenda


   // private long x, y; //esto porque?, no es un flotante?

    public Meetings(int mid){
        this.mid=mid;
    }

    public Meetings(int mid, String title, int region, String section, String org_unit, boolean virtual, String category, String sub_category, String society, String description, String date, String time, String timezone, String address, String building, String contact, float longitude, float latitude, String additional_info, String speaker, String agenda) {
        this.mid=mid;
        this.title = title;
        this.region = region;
        this.section = section;
        this.org_unit = org_unit;
        this.virtual = virtual;
        this.category = category;
        this.sub_category = sub_category;
        this.society = society;
        this.description = description;
        this.date = date;
        this.time = time;
        this.timezone = timezone;
        this.address = address;
        this.building = building;
        this.contact = contact;
        this.longitude = longitude;
        this.latitude = latitude;
        this.additional_info = additional_info;
        this.speaker = speaker;
        this.agenda = agenda;
        //this.x = x;
        //this.y = y;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getURL(){
        return "meetings.vtools.ieee.org/m/"+mid;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public String getAgenda() {
        return agenda;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getOrg_unit() {
        return org_unit;
    }

    public void setOrg_unit(String org_unit) {
        this.org_unit = org_unit;
    }

    public boolean isVirtual() {
        return virtual;
    }

    public void setVirtual(boolean virtual) {
        this.virtual = virtual;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSub_category() {
        return sub_category;
    }

    public void setSub_category(String sub_category) {
        this.sub_category = sub_category;
    }

    public String getSociety() {
        return society;
    }

    public void setSociety(String society) {
        this.society = society;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getTimezone() {
        return timezone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public String getAdditional_info() {
        return additional_info;
    }

    public void setAdditional_info(String additional_info) {
        this.additional_info = additional_info;
    }

    public PointF getCoordinates() {
        return new PointF(getLatitude(), getLongitude());
    }

//    Esto para que si está latitud y longitud ??? o quieren usar x y ?
//    public long getX() {
//        return x;
//    }
//
//    public void setX(long x) {
//        this.x = x;
//    }
//
//    public long getY() {
//        return y;
//    }
//
//    public void setY(long y) {
//        this.y = y;
//    }
}
