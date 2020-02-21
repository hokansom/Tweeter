package edu.byu.cs.tweeter.model.domain;

import androidx.annotation.NonNull;

import java.io.CharArrayReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Status implements Comparable<Status> {
    private String publishDate;
    private String message;
    private User author;
    private URLs urls;
    private UserMentions mentions;

    public Status(User author, String message, URLs urls, UserMentions mentions, Date date){
        this.author = author;
        this.message = message;
        this.urls = urls;
        this.mentions = mentions;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        this.publishDate = c.getTime().toString();
    }

    public Status(User author, String message, Date date){
        this.author = author;
        this.message = message;
        this.urls = new URLs();
        this.mentions = new UserMentions();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        this.publishDate =  c.getTime().toString();
        parseAliases(message);
        parseUrls(message);
    }

    public Status(User author, String message){
        this.author = author;
        this.message = message;
        this.urls = new URLs();
        this.publishDate = Calendar.getInstance().getTime().toString();
        parseAliases(message);
        parseUrls(message);
    }

    public Status(User author) {
        this.author = author;
    }

    private void parseAliases(String message){
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
        }
        this.mentions = new UserMentions(aliases);
    }

    private void parseUrls(String message){
        List<String> urls = new ArrayList<>();
        String urlRegex = "\\(?\\b(http://|www[.])[-A-Za-z0-9+&amp;@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&amp;@#/%=~_()|]";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(message);

        while (urlMatcher.find())
        {
            urls.add(message.substring(urlMatcher.start(0),
                    urlMatcher.end(0)));
        }

        this.urls = new URLs(urls);

    }

    public String getPublishDate() {
       String date = publishDate;
       String [] parsed = date.split(" ");
       return String.format("%s %s",parsed[1], parsed[2]);
    }

    public String getFullDate(){
        return publishDate;
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

    public URLs getUrls() {
        return urls;
    }

    public void setUrls(URLs urls) {
        this.urls = urls;
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
        String date1 = this.getFullDate();
        String date2 = o.getFullDate();


        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        try{
            cal1.setTime(sdf.parse(date1));
            cal2.setTime(sdf.parse(date2));
        } catch (ParseException exception){
            System.out.println("Error trying to parse dates");
        }

        return cal1.compareTo(cal2);
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
