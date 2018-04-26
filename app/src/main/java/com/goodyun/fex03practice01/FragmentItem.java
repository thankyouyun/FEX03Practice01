package com.goodyun.fex03practice01;

/**
 * Created by alfo6-19 on 2018-03-20.
 */


public class FragmentItem {


    String title;
    String link;
    String description;
    String imgUrl;
    String date;


    public FragmentItem() {

    }

    public FragmentItem(String title, String link, String description, String imgUrl, String date) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.imgUrl = imgUrl;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
