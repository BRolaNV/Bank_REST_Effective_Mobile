package com.example.bankcards.controller;

import com.example.bankcards.dto.CardRequest;
import com.example.bankcards.dto.CardResponse;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.service.CardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/cards")
public class AdminCardController {

    private final CardService cardService;

    public AdminCardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("")
    public CardResponse createCard(@RequestBody CardRequest request) {
        CardResponse card = cardService.createCard(request);
        return card;
    }

    @PutMapping("/{id}/activate")
    public CardResponse activeCard(@PathVariable Long id) {
        CardResponse card = cardService.updateCard(id, CardStatus.ACTIVE);
        return card;
    }

    @PutMapping("/{id}/block")
    public CardResponse blockCard(@PathVariable Long id) {
        CardResponse card = cardService.updateCard(id, CardStatus.BLOCKED);
        return card;
    }

    @DeleteMapping("/{id}")
    public void deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
    }

    @GetMapping("")
    public List<CardResponse> getAllCards() {
        return cardService.getAllCards();
    }

}
