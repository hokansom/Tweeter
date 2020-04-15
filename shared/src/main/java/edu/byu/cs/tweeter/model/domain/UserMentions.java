package edu.byu.cs.tweeter.model.domain;


import java.util.ArrayList;
import java.util.List;

public class UserMentions {
    public List<String> mentions; // Using set so no duplication

    public UserMentions(List<String> mentions){
        this.mentions = mentions;
    }

    public UserMentions(){
        this.mentions = new ArrayList<>();
    }

    public List<String> getUserMentions(){
        return this.mentions;
    }

}
