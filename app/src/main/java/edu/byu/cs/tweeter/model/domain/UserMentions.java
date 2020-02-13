package edu.byu.cs.tweeter.model.domain;


import java.util.HashSet;
import java.util.Set;

public class UserMentions {
    private Set<String> mentions; // Using set so no duplication

    public UserMentions(Set<String> mentions){
        this.mentions = mentions;
    }

    public UserMentions(){
        this.mentions = new HashSet<>();
    }

    public UserMentions getUserMentions(){
        return (UserMentions) this.mentions;
    }

    public boolean checkValidity(){
       //TODO: Check if the people actually exist
        return true;
    }

}
