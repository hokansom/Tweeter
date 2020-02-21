package edu.byu.cs.tweeter.model.domain;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Status implements Comparable<Status> {
    private String publishDate;
    private String message;
    private User author;
    private URLs uris;
    private UserMentions mentions;

    public Status(User author, String message, URLs uris, UserMentions mentions, Date date){
        this.author = author;
        this.message = message;
        this.uris = uris;
        this.mentions = mentions;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        this.publishDate =  c.getTime().toString();
        parseMessage(message);
    }

    public Status(User author, String message, Date date){
        this.author = author;
        this.message = message;
        this.uris = new URLs();
        this.mentions = new UserMentions();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        this.publishDate =  c.getTime().toString();
        parseMessage(message);
    }

    public Status(User author, String message){
        this.author = author;
        this.message = message;
        this.uris = new URLs();
        this.mentions = new UserMentions();
        this.publishDate = Calendar.getInstance().getTime().toString();
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
       String date = publishDate;
       String [] parsed = date.split(" ");
       return String.format("%s %s",parsed[1], parsed[2]);
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

    public URLs getUris() {
        return uris;
    }

    public void setUris(URLs uris) {
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
            return true;
        }
    }

    @Override
    public int compareTo(Status o) {
        return (this.getPublishDate().compareTo(o.getPublishDate()) * -1 ) ;
        //FIXME: MAKE THIS SORT THE RIGHT WAY;
    }

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
