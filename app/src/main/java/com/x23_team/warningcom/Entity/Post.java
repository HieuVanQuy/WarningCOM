package com.x23_team.warningcom.Entity;

/**
 * Created by danam on 07/04/2017.
 */

public class Post {
    private String IDPost, content, nameOfUser, address;
    private int accept, image, typeOfWarning,numberOfPost,time,position;
    public Post(){}

    public Post(String IDPost, String content, String nameOfUser, String address, int accept, int image, int typeOfWarning, int numberOfPost, int time, int position) {
        this.IDPost = IDPost;
        this.content = content;
        this.nameOfUser = nameOfUser;
        this.address = address;
        this.accept = accept;
        this.image = image;
        this.typeOfWarning = typeOfWarning;
        this.numberOfPost = numberOfPost;
        this.time = time;
        this.position = position;
    }

    public String getIDPost() {
        return IDPost;
    }

    public void setIDPost(String IDPost) {
        this.IDPost = IDPost;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNameOfUser() {
        return nameOfUser;
    }

    public void setNameOfUser(String nameOfUser) {
        this.nameOfUser = nameOfUser;
    }

    public int getAccept() {
        return accept;
    }

    public void setAccept(int accept) {
        this.accept = accept;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getTypeOfWarning() {
        return typeOfWarning;
    }

    public void setTypeOfWarning(int typeOfWarning) {
        this.typeOfWarning = typeOfWarning;
    }

    public int getNumberOfPost() {
        return numberOfPost;
    }

    public void setNumberOfPost(int numberOfPost) {
        this.numberOfPost = numberOfPost;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
