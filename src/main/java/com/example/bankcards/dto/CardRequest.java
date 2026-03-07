package com.example.bankcards.dto;

import lombok.Data;

@Data
public class CardRequest {

    private String cardNumber;
    private Long userId;
    private String ownerName;
    private String expireDate;

}
