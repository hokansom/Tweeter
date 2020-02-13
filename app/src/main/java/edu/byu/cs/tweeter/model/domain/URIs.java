package edu.byu.cs.tweeter.model.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class URIs {
    private Logger logger;
    private Set<String> uris;

    public URIs(){
        this.uris = new HashSet<>();
    }

    public URIs(Set<String> uris){
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

    public URIs getUris(){
        return (URIs) this.uris;
    }

}
