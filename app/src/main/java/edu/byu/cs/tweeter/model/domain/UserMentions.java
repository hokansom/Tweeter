package edu.byu.cs.tweeter.model.domain;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserMentions {
    private List<String> mentions; // Using set so no duplication

    public UserMentions(List<String> mentions){
        this.mentions = mentions;
    }

    public UserMentions(){
        this.mentions = new ArrayList<>();
    }

    public UserMentions getUserMentions(){
        return (UserMentions) this.mentions;
    }

    public boolean checkValidity(){
       //TODO: Check if the people actually exist
        return true;
    }

}
