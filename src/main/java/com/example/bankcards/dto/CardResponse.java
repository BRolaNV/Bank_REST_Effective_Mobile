package com.example.bankcards.dto;

import com.example.bankcards.entity.CardStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CardResponse {

    private Long id;
    private String maskedCardNumber;
    private String ownerName;
    private String expireDate;
    private CardStatus status;
    private BigDecimal balance;
}
