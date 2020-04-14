package edu.byu.cs.tweeter.model.domain;

import java.io.Serializable;
import java.util.List;

public class QueueMessage implements Serializable {
    private Status status;
    private List<String> aliases;

    public QueueMessage() {}

    public QueueMessage(Status status, List<String> aliases) {
        this.status = status;
        this.aliases = aliases;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }
}
