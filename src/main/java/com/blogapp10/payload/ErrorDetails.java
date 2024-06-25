package com.blogapp10.payload;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorDetails {

    private Date date;
    private String message;
    private String details;

    public ErrorDetails(Date date, String message, String details) {
        this.date = date;
        this.message = message;
        this.details = details;
    }
}
