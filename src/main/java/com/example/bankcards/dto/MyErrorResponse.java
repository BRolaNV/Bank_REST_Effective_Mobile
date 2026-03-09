package com.example.bankcards.dto;


import lombok.Data;

@Data
public class MyErrorResponse {

    private String message;
    private int status;
}
