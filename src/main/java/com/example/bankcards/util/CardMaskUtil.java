package com.example.bankcards.util;

public class CardMaskUtil {

    public static String maskCardNumber(String cardNumber) {

        if (cardNumber == null || cardNumber.length() < 16) return null;

        return "**** **** **** " + cardNumber.substring(12, 16);
    }
}
