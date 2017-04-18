package com.mydays.journal;

import java.sql.Date;


public class Billet {
    Integer id,catigorie;
    float rating;
    String place,content,title;
    Date day;

    public Billet(Integer id, float rating, Integer catigorie, String place, String content, String title, Date day) {
        this.id = id;
        this.rating = rating;
        this.catigorie = catigorie;
        this.place = place;
        this.content = content;
        this.title = title;
        this.day = day;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Integer getCatigorie() {
        return catigorie;
    }

    public void setCatigorie(Integer catigorie) {
        this.catigorie = catigorie;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

}
