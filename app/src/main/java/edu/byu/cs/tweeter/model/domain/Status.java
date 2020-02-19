package edu.byu.cs.tweeter.model.domain;

import android.net.Uri;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Status implements Comparable<Status> {
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
//        this.publishDate = new Date();
        this.publishDate = Calendar.getInstance().getTime();
//        this.publishDate = LocalDate.now().toString();
    }

    public Status(User author, String message){
        this.author = author;
        this.message = message;
        this.uris = new URIs();
        this.publishDate = new Date();
        parseMessage(message);
    }

    public Status(User author) {
        this.author = author;
    }

    private void parseMessage(String message){
        String copy = message;
        List<String> aliases = new ArrayList<>();
        if(message.contains("@")){
            String[] parsed = copy.split("@");
            for(int i = 1; i < parsed.length; i++){
                String mention = "@" + parsed[i];
                int index = mention.indexOf(' ');
                if(index != -1){
                    mention = mention.substring(0, index);
                }
                aliases.add(mention);
            }

            this.mentions = new UserMentions(aliases);
        }
    }

    public String getPublishDate() {
//        Calendar calendar = publishDate.dateToCalendar(publishDate);
//        System.out.println(calendar.getTime());
//        String date = ;date
        //FIXME:!!!!
//        return publishDate;
        return "Test";
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        if (!publishDate.equals(status.publishDate)){ return false;  }
        else if(!author.equals(status.getAuthor())) { return false; }
        else if(!message.equals(status.getMessage())) { return false; }
        else{
            /*FIXME: potential error. May need more than just checking the date, author, and message*/
            return true;
        }
    }

    @Override
    public int compareTo(Status o) {
        return this.getPublishDate().compareTo(o.getPublishDate());
    }

    @NotNull

    @NonNull
    @Override
    public String toString(){
        return "Status{" +
                "message='" + message + '\'' +
                ", author ='" + author.getName() + '\'' +
                ", publish date ='" + publishDate + '\'' +
                '}';
    }
}
