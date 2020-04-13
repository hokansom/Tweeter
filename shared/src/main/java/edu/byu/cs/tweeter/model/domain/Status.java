package edu.byu.cs.tweeter.model.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Status implements Comparable<Status> {
    public String message;
    public User author;
    public URLs urls;
    public UserMentions mentions;
    public long date;

    public Status() {}

    public Status(User author, String message, URLs urls, UserMentions mentions, long date){
        this.author = author;
        this.message = message;
        this.urls = urls;
        this.mentions = mentions;
        this.date = date;
    }

    public Status(User author, String message, long date){
        this.author = author;
        this.message = message;
        this.urls = new URLs();
        this.mentions = new UserMentions();
        this.date =  date;
        parseAliases(message);
        parseUrls(message);
    }

    public Status(User author, String message){
        this.author = author;
        this.message = message;
        this.urls = new URLs();
        Date date = new Date();
        this.date = date.getTime();
        parseAliases(message);
        parseUrls(message);
    }

    public Status(User author) {
        this.author = author;
    }

    private void parseAliases(String message){
        List<String> aliases = new ArrayList<>();
        if(message.contains("@")){
            String[] parsed = message.split("@");
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

    public String getDisplayDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        String [] parsed = calendar.getTime().toString().split(" ");
        if(parsed.length == 2){
            return String.format("%s %s",parsed[0], parsed[1]);
        } else {
            return String.format("%s %s", parsed[1], parsed[2]);
        }
    }

    public long getDate() {
      return date;
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

    public UserMentions getMentions() {
        return mentions;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        if (date != status.getDate()){ return false;  }
        else if(!author.equals(status.getAuthor())) { return false; }
        else if(!message.equals(status.getMessage())) { return false; }
        else{
            return true;
        }
    }

    @Override
    public int compareTo(Status o) {
        long date1 = this.getDate();
        long date2 = o.getDate();

        return (int) (date1 - date2);
    }

    @Override
    public String toString(){
        return "Status{" +
                "message='" + message + '\'' +
                ", author ='" + author.getName() + '\'' +
                ", publish date ='" + getDisplayDate() + '\'' +
                '}';
    }
}
