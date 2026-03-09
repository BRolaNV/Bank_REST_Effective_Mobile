package com.example.bankcards.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TransferRequest {

    @NotNull(message = "Card id cannot be null")
    private Long fromCardId;

    @NotNull(message = "Card id cannot be null")
    private Long toCardId;

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.01", message = "Amount cannot be negative")
    private BigDecimal amount;
}
