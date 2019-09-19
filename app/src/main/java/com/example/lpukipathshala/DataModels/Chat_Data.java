package com.example.lpukipathshala.DataModels;

public class Chat_Data  {
    private  String sender_id;
    private  String receiver_id;
    private  String show_message;
    private String Book_id;
    public Chat_Data() {
    }

    public Chat_Data(String sender_id, String book_id) {
        this.sender_id = sender_id;
        Book_id = book_id;
    }

    public Chat_Data(String sender_id, String receiver_id, String show_message, String book_id) {
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.show_message = show_message;
        Book_id = book_id;
    }
    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getShow_message() {
        return show_message;
    }

    public void setShow_message(String show_message) {
        this.show_message = show_message;
    }

    public String getBook_id() {
        return Book_id;
    }

    public void setBook_id(String book_id) {
        Book_id = book_id;
    }
}
