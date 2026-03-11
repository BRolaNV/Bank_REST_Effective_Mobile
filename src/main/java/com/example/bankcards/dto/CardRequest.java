package com.example.bankcards.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CardRequest {

    @NotBlank(message = "Card number cannot be blank")
    @Pattern(regexp = "\\d{16}|" +
            "\\d{4}-\\d{4}-\\d{4}-\\d{4}|" +
            "\\d{4} \\d{4} \\d{4} \\d{4}",
            message = "Invalid card number format")
    private String cardNumber;

    @NotNull(message = "User id cannot be null")
    private Long userId;

    @NotBlank(message = "Owner name cannot be blank")
    private String ownerName;

    @NotBlank(message = "Expire date cannot be blank")
    private String expireDate;
}
