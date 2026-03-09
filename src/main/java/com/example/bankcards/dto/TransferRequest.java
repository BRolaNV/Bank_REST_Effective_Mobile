package com.example.bankcards.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TransferRequest {
    private Long fromCardId;
    private Long toCardId;
    private BigDecimal amount;
}
