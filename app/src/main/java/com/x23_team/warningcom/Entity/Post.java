package com.x23_team.warningcom.Entity;

/**
 * Created by danam on 07/04/2017.
 */

public class Post {
    private String content, image, type_id, lat, lng, address, account_id, id;
    public Post(){
    }
    public Post( String content, String image, String type_id, String lat, String lng, String address,
                 String account_id, String id){
        this.content = content;
        this.image = image;
        this.type_id = type_id;
        this.lat = lat;
        this.lng = lng;
        this.address = address;
        this.account_id = account_id;
        this.id = id;
    }
    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
