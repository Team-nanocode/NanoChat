package com.example.nanochat;


public class Message {
    private int id;
    private String message;
    private String time;
    private String author;
    private Boolean isMine;

    public Message(int id, String message, String time, String author, Boolean isMine) {
        this.id = id;
        this.message = message;
        this.time = time;
        this.author = author;
        this.isMine = isMine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Boolean getMine() {
        return isMine;
    }

    public void setMine(Boolean mine) {
        isMine = mine;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", time='" + time + '\'' +
                ", author='" + author + '\'' +
                ", isMine=" + isMine +
                '}';
    }
}
