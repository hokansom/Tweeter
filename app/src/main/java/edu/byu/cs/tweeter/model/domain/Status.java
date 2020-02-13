package edu.byu.cs.tweeter.model.domain;

import android.net.Uri;

import java.net.URISyntaxException;
import java.util.Date;

public class Status {
    private Date publishDate;
    private String message;
    private User author;
    private URIs uris;
    private UserMentions mentions;

    public Status(User author, String message, URIs uris, UserMentions mentions){
        this.author = author;
        this.message = message;
        this.uris = uris;
        this.mentions = mentions;
        this.publishDate = new Date();
    }

    public Status(User author, String message){
        this.author = author;
        this.message = message;
        this.uris = new URIs();
        this.mentions = new UserMentions();
        this.publishDate = new Date();
    }

    public Status(User author) {
        this.author = author;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public URIs getUris() {
        return uris;
    }

    public void setUris(URIs uris) {
        this.uris = uris;
    }

    public UserMentions getMentions() {
        return mentions;
    }

    public void setMentions(UserMentions mentions) {
        this.mentions = mentions;
    }

    //FIXME: implement a compareTo using the status dates
}
