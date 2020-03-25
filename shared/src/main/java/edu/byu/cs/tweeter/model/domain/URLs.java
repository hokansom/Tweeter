package edu.byu.cs.tweeter.model.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class URLs {
    public Logger logger;
    public List<String> uris;

    public URLs(){
        this.uris = new ArrayList<>();
    }

    public URLs(List<String> uris){
        this.uris = uris;
    }

    public void addUri(String uri){
        if(!uris.contains(uri)){
            this.uris.add(uri);
        }
        else{
            logger.log(Level.WARNING, "Tried to add a duplicate uri ${uri} ");
        }
    }

    public List<String> getUris(){
        return this.uris;
    }

}
