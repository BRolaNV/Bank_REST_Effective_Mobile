package com.example.bankcards.controller;


import com.example.bankcards.dto.CardRequest;
import com.example.bankcards.dto.CardResponse;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.service.CardService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/user/cards")
public class UserCardController {

    private final CardService cardService;

    public UserCardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PutMapping("/{id}/request-block")
    public CardResponse blockCard(@PathVariable Long id) {
        CardResponse card = cardService.requestBlock(id);
        return card;
    }

    @GetMapping("")
    public Page<CardResponse> getUserCards(@RequestParam(required = false) CardStatus status,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        return cardService.getUsersCards(status, page, size);
    }

    @GetMapping("/{id}/balance")
    public BigDecimal getBalance(@PathVariable Long id) {
        return cardService.getCardBalance(id);
    }

}
