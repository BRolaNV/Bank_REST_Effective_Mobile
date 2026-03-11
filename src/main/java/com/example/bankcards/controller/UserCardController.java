package com.example.bankcards.controller;


import com.example.bankcards.dto.CardResponse;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/user/cards")
@Tag(name = "User Card Controller", description = "Controller for managing cards by user")
public class UserCardController {

    private final CardService cardService;

    public UserCardController(CardService cardService) {
        this.cardService = cardService;
    }


    @PutMapping("/{id}/request-block")
    @Operation(summary = "Block a card by ID", description = "Request to block a card by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card blocked successfully"),
            @ApiResponse(responseCode = "404", description = "Card not found")
    })
    public CardResponse blockCard(@PathVariable Long id) {
        return cardService.requestBlock(id);
    }


    @GetMapping("")
    @Operation(summary = "Get user cards", description = "Returns a list of user cards")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of cards retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public Page<CardResponse> getUserCards(@RequestParam(required = false) CardStatus status,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        return cardService.getUsersCards(status, page, size);
    }


    @GetMapping("/{id}/balance")
    @Operation(summary = "Get card balance", description = "Returns the balance of a card by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Balance retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Card not found")
    })
    public BigDecimal getBalance(@PathVariable Long id) {
        return cardService.getCardBalance(id);
    }

}
